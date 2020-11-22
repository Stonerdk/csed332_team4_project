package org.team4.team4_project.git;

import static org.junit.Assert.*;
import java.io.File;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

public class GitCodeChurnTest {

    public static final String testFilePath = "datatest/homework0";

    @Test
    public void CodeChurnTest() throws Exception{
        GitHandler gitHandler = new GitHandler(testFilePath);
        Repository repo = gitHandler.getRepository();
        ChurnResult result = new CodeChurn(repo).addPath(".gitignore").calc();
        assertEquals(1,result.getLinesAdded());
    }

}
