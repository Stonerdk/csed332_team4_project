package org.team4.team4_project.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.team4.team4_project.metric_calculation.CommitInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;


/**
 * Obtain a code change in the git commit history and
 * use it to calculate the code churn and
 * complete the code at the time of commit.
 * @author Eojin Kim, Honggi Kim
 */

public class CodeChurn {
    private final Repository repo;
    private final Git git;
    private final LogCommand log;

    private String path = "";

    /**
     * Creates Codechurn class pointing to git repository
     * @param repo Repository class for git repository
     */
    public CodeChurn(Repository repo) {
        this.repo = repo;
        git = new Git(this.repo);
        log = git.log();
    }


    /**
     * Returns this CodeChurn class's git
     * @return git class pointing to project's git
     */
    public Git git() {
        return git;
    }


    /**
     * Set the file path for this commit
     * If file path is set, the LogCommand class log's method call() returns all commit relevant to the file.
     * @param path the file path which user want to track git history.
     * @return CodeChurn class which path is set to param path.
     */
    public CodeChurn addPath(String path){
        log.addPath(path);
        this.path = path;
        return this;
    }


    /**
     * Calculate all code churn and code for commit relevant to the file path.
     * After calculating, return List<CommitInfo> containing the code and the information of the commits.
     * All commit is relevant to the file path.
     * @return List<CommitInfo> for all commit relevant to file path.
     */
    public List<CommitInfo> calc() throws GitAPIException, IOException {
        List<CommitInfo> results = new ArrayList<CommitInfo>();
        List<String> differences = new ArrayList<String>();
        Iterable<RevCommit> commits = log.call();
        for (RevCommit commit : commits) {
            CommitInfo result = new CommitInfo();
            int date1 = commit.getCommitTime();
            long date = Long.valueOf(date1);
            String hash = commit.getName();
            result.setCommitHash(hash);
            date = date * 1000;
            result.setDate(new Date(date));
            RevCommit[] parents = commit.getParents();
            for (RevCommit parent : parents) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DiffFormatter formatter = new DiffFormatter(stream);
                formatter.setRepository(repo);
                formatter.setContext(0);
                formatter.setPathFilter(PathFilter.create(path));
                formatter.format(parent, commit);
                String difference = stream.toString();
                differences.add(difference);
                Scanner scanner = new Scanner(difference);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("+") && !line.startsWith("+++")) {
                        result.getChurn().plusLinesAdded();
                    } else if (line.startsWith("-") && !line.startsWith("---")) {
                        result.getChurn().plusLinesDeleted();
                    }
                }
                break;
            }
            if (parents.length == 0){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DiffFormatter formatter = new DiffFormatter(stream);
                formatter.setRepository(repo);
                formatter.setContext(0);
                formatter.setPathFilter(PathFilter.create(path));

                ObjectReader reader = repo.newObjectReader();
                RevWalk rw = new RevWalk(reader);
                RevTree rtree = rw.parseTree(commit);
                CanonicalTreeParser parser = new CanonicalTreeParser();
                parser.reset(reader, rtree);
                AbstractTreeIterator iterator = (AbstractTreeIterator)parser;
                AbstractTreeIterator empty = new EmptyTreeIterator();
                List<DiffEntry> diffs = formatter.scan(empty, iterator);
                formatter.format(diffs);
                String difference = stream.toString();
                differences.add(difference);
                Scanner scanner = new Scanner(difference);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("+") && !line.startsWith("+++")) {
                        result.getChurn().plusLinesAdded();
                    } else if (line.startsWith("-") && !line.startsWith("---")) {
                        result.getChurn().plusLinesDeleted();
                    }
                }
            }

            results.add(result);
        }

        results = completeCode(results, differences);
        List<CommitInfo> temp = new ArrayList<CommitInfo>();
        for (int i=0; i<results.size(); i++){
            temp.add(results.get(results.size()-1-i));
        }
        return temp;
    }


    /**
     * This method complete code for all commits.
     * First, in the root commit, complete the code using difference.
     * After that, using previous CommitInfo and difference, complete the code for all commits.
     * In summary, it is a function that completes the code by adding differences from root commit.
     * @param results List<CommitInfo> containing the information of the commits except code.
     * @param differences List<String> containing all code differences written in commit.
     * @return List<CommitInfo> containing the code and information of the commits.
     */
    public List<CommitInfo> completeCode(List<CommitInfo> results, List<String> differences) {
        List<String> codes = new ArrayList<String>();
        for (int i=0; i<differences.size(); i++){
            if (i>0) {
                codes = results.get(results.size() - i).getChurn().getcodelist();
                String difference = differences.get(differences.size() - 1 - i);
                Scanner scanner = new Scanner(difference);

                int deletenumlines = 0;
                int deletestartline = 1;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("-") && !line.startsWith("---")) {
                        if(codes.size() >= deletestartline - deletenumlines) {
                            codes.remove(deletestartline - 1 - deletenumlines);
                            deletenumlines++;
                            deletestartline++;
                        }
                    } else if (line.startsWith("@@")) {
                        String[] split = line.split(" ");
                        String[] split3 = split[1].split(",");
                        deletestartline = Integer.valueOf(split3[0]) * -1;
                    }
                }

                Scanner scanner2 = new Scanner(difference);
                int addnumLines = 1;
                int addstartLine = 1;
                while (scanner2.hasNextLine()) {
                    String line = scanner2.nextLine();
                    if (line.startsWith("+") && !line.startsWith("+++")) {
                        String code = line.substring(1, line.length());
                        try {
                            codes.set(addstartLine - 1, code);
                        }catch (IndexOutOfBoundsException e){
                        }
                        addstartLine++;
                    }
                    else if (line.startsWith("@@")) {
                        String[] split = line.split(" ");
                        String[] split2 = split[2].split(",");
                        if (split2.length > 1) {
                            addnumLines = Integer.valueOf(split2[1]);
                        }
                        else {
                            addnumLines = 1;
                        }
                        addstartLine = Integer.valueOf(split2[0]);
                        codes = pushString(codes, addnumLines, addstartLine);
                    }
                }
            }

            else {
                String difference = differences.get(differences.size() - 1 - i);
                Scanner scanner = new Scanner(difference);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("+") && !line.startsWith("+++")) {
                        String code = line.substring(1, line.length());
                        codes.add(code);
                    }
                }
            }

            String code = "";
            for (int j=0; j<codes.size(); j++){
                if(j == codes.size()-1)
                    code = code + codes.get(j);
                else
                    code = code + codes.get(j) + "\n";
            }
            results.get(results.size()-1-i).getChurn().setcode(code);
            results.get(results.size()-1-i).getChurn().setcodelist(codes);
        }


        return results;
    }


    /**
     * This method expands codes (List<String)) for adding code difference.
     * addnumLines means code length for added codes and addstartLine means where the code to insert will start.
     * For adding code difference, expand codes by addnumLines and push the list's contents after addstartLine index.
     * @param codes List<String> code for previous commit.
     * @param addnumLines code length for added codes
     * @param addstartLine where the code to insert will start.
     * @return List<String> containing the code and information of the commits.
     */

    public List<String> pushString (List<String> codes, int addnumLines, int addstartLine){
        int codessize = codes.size();
        if (codessize > 0) {
            for (int i = 0; i < addnumLines; i++) {
                codes.add("");
            }
            for (int i = codessize - 1; i >= addstartLine - 1 && i >= 0; i--) {
                try {
                    codes.set(i + addnumLines, codes.get(i));
                }catch (IndexOutOfBoundsException e){
                }
            }
        }

        if (codessize == 0){
            for (int i = 0; i < addnumLines; i++){
                codes.add("");
            }
        }

        return codes;
    }
}