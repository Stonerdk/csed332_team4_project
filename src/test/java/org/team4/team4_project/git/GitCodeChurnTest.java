package org.team4.team4_project.git;

import static org.junit.Assert.*;
import java.io.File;
import java.util.List;

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
        List<ChurnResult> result = new CodeChurn(repo).addPath("README.md").calc();
        assertEquals(2,result.get(result.size()-1).getLinesAdded());
        assertEquals(3,result.get(0).getLinesAdded());
        //assertEquals(1,result.getLinesDeleted());
    }

}
