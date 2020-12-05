package org.team4.team4_project.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    public void test() throws IOException, GitAPIException {
        ObjectId oldHead = repository.resolve("HEAD^^^^{tree}");
        ObjectId head = repository.resolve("HEAD^{tree}");

        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        oldTreeIter.reset(reader, oldHead);
        CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        newTreeIter.reset(reader, head);

        List<DiffEntry> diffs= git.diff()
                .setNewTree(newTreeIter)
                .setOldTree(oldTreeIter)
                .call();

        List<DiffEntry> javaFilesDiffs = diffs.stream()
                .filter(entry -> entry.getNewPath().endsWith(".java"))
                .collect(Collectors.toList());

        for (DiffEntry entry: javaFilesDiffs) {
            List<ChurnResult> result = new CodeChurn(repository).addPath(entry.getNewPath()).calc();
            System.out.println(result.get(0).getcode());
        }
    }
}
