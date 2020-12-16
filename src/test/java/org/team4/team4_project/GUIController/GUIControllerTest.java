package org.team4.team4_project.GUIController;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.team4.team4_project.UI.GUIController;

public class GUIControllerTest {
    /*GUIController guiC = GUIController.getInstance();

    @Before
    public void init(){
        String proj = System.getProperty("user.dir");
        proj = proj.replace("\\", "/");
        String path = proj + "/src/test/java/org/team4/team4_project/GUIController/testProj/homework3";
        guiC.refreshController(path, "homework3");
    }
    @Test
    public void ProjectFileTest(){
        assertEquals(null, guiC.getName());
    }

    @Test
    public void ProjectPathTest(){
        assertEquals((System.getProperty("user.dir")).replace("\\", "/") +
                "/src/test/java/org/team4/team4_project/GUIController/testProj/homework3", guiC.getPath());
        assertEquals("homework3", guiC.getProj());
    }

    @Test
    public void FileSelectTest(){
        guiC.selectFile("AdjacencyListGraph.java", "homework3/homework3/homework3/src/main/java/edu/postech/csed332/homework3/AdjacencyListGraph.java");
        assertEquals("AdjacencyListGraph.java", guiC.getName());
    }

    @Test
    public void getCommitListSize(){
        assertEquals(7, guiC.getSize());
    }

    @Test
    public void getDateTest(){
        assertEquals("Sep 29", guiC.getCommitDate(0));
        assertEquals("Oct 04", guiC.getCommitDate(1));
        assertEquals("Oct 04", guiC.getCommitDate(2));
        assertEquals("Oct 05", guiC.getCommitDate(3));
        assertEquals("Oct 05", guiC.getCommitDate(4));
        assertEquals("Oct 06", guiC.getCommitDate(5));
        assertEquals("Oct 07", guiC.getCommitDate(6));
    }

    @Test
    public void NumOfInfo(){
        assertEquals(18, guiC.getAllValue(0).size());
        assertEquals(18, guiC.getAllValue(1).size());
    }

    @Test
    public void AllFilePathTest(){
        assertEquals(13, guiC.getAllPath().size());
        assertEquals("homework3/homework3/homework3/src/main/java/edu/postech/csed332/homework3/AdjacencyListGraph.java", guiC.getAllPath().get(0));
    }

    @Test
    public void getFilePathTest(){
        assertEquals("homework3/homework3/homework3/src/main/java/edu/postech/csed332/homework3/AdjacencyListGraph.java", guiC.getFilePath(0));
    }*/

}
