package org.team4.team4_project.git;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    public ChurnResult calc() throws GitAPIException, IOException {
        ChurnResult result = new ChurnResult();
        Iterable<RevCommit> commits = log.call();
        //for (RevCommit commit : commits) {
        RevCommit commit = commits.iterator().next();
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
                System.out.println(difference);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("+") && !line.startsWith("+++")) {
                        result.plusLinesAdded();
                    } else if (line.startsWith("-") && !line.startsWith("---")) {
                        result.plusLinesDeleted();
                    }
                }
            }
        //}
        return result;
    }


}
