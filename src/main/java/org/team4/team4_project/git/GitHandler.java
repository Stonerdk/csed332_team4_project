package org.team4.team4_project.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitHandler {
    private Git git;
    private Repository repository;

    public GitHandler() throws IOException {
        String root = System.getProperty("user.dir");
        git = Git.open(new File(root + "/.git"));
        git.checkout();
        repository = git.getRepository();
    }

    public GitHandler(String path) throws IOException {
        git = Git.open(new File(path + "/.git"));
        git.checkout();
        repository = git.getRepository();
    }

    public Git getGit() {
        return git;
    }

    public Repository getRepository() {
        return repository;
    }

    public void getAllFiles() throws IOException, GitAPIException {
        List<String> fileList = new ArrayList<String>();

        List<Ref> branches = git.branchList().call();

        for (Ref branch : branches) {
            if (branch.getName().equals("refs/heads/master")) {
                Iterable<RevCommit> commits = git.log().call();

                for (RevCommit commit : commits) {
                    ObjectId treeId = commit.getTree();
                    TreeWalk treeWalk = new TreeWalk(repository);
                    treeWalk.reset(treeId);
                    while (treeWalk.next()) {
                        if (treeWalk.isSubtree()) {
                            treeWalk.enterSubtree();
                        } else {
                            String file = treeWalk.getPathString();
                            if (file.endsWith(".java") && !fileList.contains(file)) {
                                fileList.add(file);
                            }
                        }
                    }
                }
            }
        }
    }
}
