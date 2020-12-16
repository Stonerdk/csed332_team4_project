package org.team4.team4_project.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.team4.team4_project.metric_calculation.CommitInfo;
import org.team4.team4_project.metric_calculation.FileInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<String> getAllFiles(String branchName) throws IOException, GitAPIException {
        List<String> fileList = new ArrayList<String>();
        RevWalk walk = new RevWalk(repository);

        List<Ref> branches = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();

        for (Ref branch : branches) {
            if (branch.getName().equals("refs/heads/" + (branchName.equals("") ? "master" : branchName))) {
                Iterable<RevCommit> commits = git.log().call();

                for (RevCommit commit : commits) {
                    boolean foundInThisBranch = false;

                    RevCommit targetCommit = walk.parseCommit(repository.resolve(
                            commit.getName()));
                    for (Map.Entry<String, Ref> e : repository.getAllRefs().entrySet()) {
                        if (e.getKey().startsWith(Constants.R_HEADS)) {
                            if (walk.isMergedInto(targetCommit, walk.parseCommit(
                                    e.getValue().getObjectId()))) {
                                String foundInBranch = e.getValue().getName();
                                if (branch.getName().equals(foundInBranch)) {
                                    foundInThisBranch = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (foundInThisBranch) {
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

        return fileList;
    }

    public List<FileInfo> getFileInfo() throws IOException, GitAPIException {
        List<String> files = getAllFiles("");
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();

        for (String file: files) {
            FileInfo fileInfo = new FileInfo();

            String fileName = file.substring(file.lastIndexOf('/') + 1);
            fileInfo.setFileName(fileName);
            fileInfo.setFilePath(file);

            List<CommitInfo> commitInfos = new CodeChurn(repository).addPath(file).calc();
            fileInfo.setComInfoList(commitInfos);
            fileInfoList.add(fileInfo);
        }

        System.out.println(fileInfoList);
        return fileInfoList;
    }

    public List<FileInfo> getFileInfo(String branchName) throws IOException, GitAPIException {
        List<String> files = getAllFiles(branchName);
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();

        for (String file: files) {
            FileInfo fileInfo = new FileInfo();

            String fileName = file.substring(file.lastIndexOf('/') + 1);
            fileInfo.setFileName(fileName);
            fileInfo.setFilePath(file);

            List<CommitInfo> commitInfos = new CodeChurn(repository).addPath(file).calc();
            fileInfo.setComInfoList(commitInfos);
            fileInfoList.add(fileInfo);
        }

        System.out.println(fileInfoList);
        return fileInfoList;
    }

    public List<String> getBranchList() throws GitAPIException {
        List<Ref> branches = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        List<String> branchList = new ArrayList<String>();

        for (Ref branch: branches) {
            String branchFullName = branch.getName();
            String branchName = branchFullName.substring(branchFullName.lastIndexOf('/') + 1);
            branchList.add(branchName);
        }

        return branchList;
    }
}
