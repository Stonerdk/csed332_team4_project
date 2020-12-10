package org.team4.team4_project.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeChurn {
    private final Repository repo;
    private final Git git;
    private final LogCommand log;

    private String path = "";

    public CodeChurn(Repository repo) {
        this.repo = repo;
        git = new Git(this.repo);
        log = git.log();
    }

    public Git git() {
        return git;
    }

    public CodeChurn addPath(String path){
        log.addPath(path);
        this.path = path;
        return this;
    }

    public List<ChurnResult> calc() throws GitAPIException, IOException {
        List<ChurnResult> results = new ArrayList<ChurnResult>();
        List<String> differences = new ArrayList<String>();
        Iterable<RevCommit> commits = log.all().call();
        for (RevCommit commit : commits) {
            ChurnResult result = new ChurnResult();
            Integer date = commit.getCommitTime();
            String hash = commit.getName();
            result.setCommmitHash(hash);
            result.setCommitDate(date);
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
                        result.plusLinesAdded();
                    } else if (line.startsWith("-") && !line.startsWith("---")) {
                        result.plusLinesDeleted();
                    }
                }
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
                        result.plusLinesAdded();
                    } else if (line.startsWith("-") && !line.startsWith("---")) {
                        result.plusLinesDeleted();
                    }
                }
            }
            results.add(result);
        }

        results = completeCode(results, differences);
        return results;
    }

    public List<ChurnResult> completeCode(List<ChurnResult> results, List<String> differences) {
        List<String> codes = new ArrayList<String>();
        Integer pre = 1;
        for (int i=0; i<differences.size(); i++){
                if (i>0) {
                    codes = results.get(results.size() - i).getcodelist();
                    String difference = differences.get(differences.size() - 1 - i);
                    Scanner scanner = new Scanner(difference);
                    int deletenumlines = 0;
                    int deletestartline = 1;
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.startsWith("-") && !line.startsWith("---")) {
                            codes.remove(deletestartline - deletenumlines);
                            deletestartline++;
                        } else if (line.startsWith("@@")) {
                            String[] split = line.split(" ");
                            String[] split3 = split[1].split(",");
                            if (split3.length > 1) {
                                deletenumlines = deletenumlines + Integer.valueOf(split3[1]);
                            }
                            else {
                                deletenumlines++;
                            }
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
                            codes.set(addstartLine - 1, code);
                            addstartLine++;
                        }
                        else if (line.startsWith("@@")) {
                            String[] split = line.split(" ");
                            String[] split2 = split[2].split(",");
                            if (split2.length > 1) {
                                addnumLines = Integer.valueOf(split2[1]);
                            }
                            addstartLine = Integer.valueOf(split2[0]);
                            codes = pushString(codes, addnumLines, addstartLine);
                            System.out.println("dsatadt");
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
            results.get(results.size()-1-i).setcode(code);
            results.get(results.size()-1-i).setcodelist(codes);
        }


        return results;
    }

    public List<String> pushString (List<String> codes, int addnumLines, int addstartLine){
        int codessize = codes.size();
        for (int i =0; i<addnumLines; i++){
            codes.add("");
        }

        for (int i = codessize-1; i >= addstartLine - 1 ; i--){
            codes.set(i + addnumLines, codes.get(i));
        }

        return codes;
    }
}