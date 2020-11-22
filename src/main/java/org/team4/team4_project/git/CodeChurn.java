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

    public CodeChurn add(AnyObjectId start) throws IncorrectObjectTypeException, MissingObjectException {
        log.add(start);
        return this;
    }

    public List<ChurnResult> calc() throws GitAPIException, IOException {
        List<ChurnResult> results = new ArrayList<ChurnResult>();
        Iterable<RevCommit> commits = log.all().call();
        for (RevCommit commit : commits) {
        //RevCommit commit = commits.iterator().next();
            ChurnResult result = new ChurnResult();
            RevCommit[] parents = commit.getParents();
            for (RevCommit parent : parents) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DiffFormatter formatter = new DiffFormatter(stream);
                formatter.setRepository(repo);
                formatter.setContext(0);
                formatter.setPathFilter(PathFilter.create(path));
                formatter.format(parent, commit);
                String difference = stream.toString();
                Scanner scanner = new Scanner(difference);
                //System.out.println(difference);
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
                ObjectReader reader = repo.newObjectReader();
                RevWalk rw = new RevWalk(reader);
                RevTree rtree = rw.parseTree(commit);
                CanonicalTreeParser parser = new CanonicalTreeParser();
                parser.reset(reader, rtree);
                AbstractTreeIterator iterator = (AbstractTreeIterator)parser;
                AbstractTreeIterator empty = iterator.createEmptyTreeIterator();
                TreeWalk walk = new TreeWalk(reader);
                walk.addTree(empty);
                walk.addTree(iterator);
                List<DiffEntry> diffs = DiffEntry.scan(walk);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DiffFormatter formatter = new DiffFormatter(stream);
                formatter.setRepository(repo);
                formatter.setContext(0);
                formatter.setPathFilter(PathFilter.create(path));
                formatter.format(diffs);
                String difference = stream.toString();
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


        return results;
    }


}
