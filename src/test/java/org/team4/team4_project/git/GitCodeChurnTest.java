package org.team4.team4_project.git;

import static org.junit.Assert.*;
import java.io.File;
import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;
import org.team4.team4_project.metric_calculation.CommitInfo;
import org.team4.team4_project.metric_calculation.FileInfo;

public class GitCodeChurnTest {
    /*
     * These are tests done in local directory
     * We did not remove, just keep it in comment
     */


    /*
    @Test
    public void Test() throws Exception {
        GitHandler gitHandler = new GitHandler("C:\\Users\\admin\\Documents\\GitHub\\team4_project\\datatest\\homework6");
        List<FileInfo> result = gitHandler.getFileInfo();
    }

    @Test
    public void CodeChurnTest() throws Exception{
        String testFilePath = "datatest/homework0";
        GitHandler gitHandler = new GitHandler(testFilePath);
        Repository repo = gitHandler.getRepository();
        List<CommitInfo> result = new CodeChurn(repo).addPath("README.md").calc();
        assertEquals(1,result.get(result.size()-1).getChurn().getLinesAdded());
        assertEquals(5,result.get(0).getChurn().getLinesAdded());
        //assertEquals(1,result.getLinesDeleted());
    }
    */

}
