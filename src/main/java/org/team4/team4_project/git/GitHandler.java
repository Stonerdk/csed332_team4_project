package org.team4.team4_project.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.team4.team4_project.metric_calculation.CommitInfo;
import org.team4.team4_project.metric_calculation.FileInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    public List<String> getAllFiles() throws IOException, GitAPIException {
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

        return fileList;
    }

    public List<FileInfo> getFileInfo() throws IOException, GitAPIException {
        List<String> files = getAllFiles();
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
        List<CommitInfo> commitInfoList = new ArrayList<CommitInfo>();
        CommitInfo commitInfo = new CommitInfo();

        for (String file: files) {
            commitInfoList = new ArrayList<CommitInfo>();
            FileInfo fileInfo = new FileInfo();

            String fileName = file.substring(file.lastIndexOf('/') + 1);
            fileInfo.setFileName(fileName);
            fileInfo.setFilePath(file);

            List<ChurnResult> churnResults = new CodeChurn(repository).addPath(file).calc();
            for (ChurnResult churnResult: churnResults) {
                commitInfo = new CommitInfo();
                commitInfo.setCommitHash(churnResult.getCommmitHash());
                commitInfo.setDate(new Date(churnResult.getCommitDate()));
                commitInfo.setChurn(churnResult);
                commitInfoList.add(commitInfo);
            }
            fileInfo.setComInfoList(commitInfoList);
            fileInfoList.add(fileInfo);
        }

        System.out.println(fileInfoList);
        return fileInfoList;
    }
}
