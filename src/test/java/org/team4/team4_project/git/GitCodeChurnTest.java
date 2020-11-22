package org.team4.team4_project.git;

import static org.junit.Assert.*;
import java.io.File;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

public class GitCodeChurnTest {

    public static final File testfile = new File("datatest/homework0/.git");

    @Test
    public void CodeChurnTest() throws Exception{
        Repository repo = new FileRepositoryBuilder().setGitDir(testfile).readEnvironment().findGitDir().build();
        ChurnResult result = new CodeChurn(repo).addPath("README.md").calc();
        assertEquals(1,result.getLinesAdded());
        assertEquals(1,result.getLinesDeleted());
    }

}
