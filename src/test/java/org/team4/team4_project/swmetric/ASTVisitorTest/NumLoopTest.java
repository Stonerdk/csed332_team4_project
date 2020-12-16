package org.team4.team4_project.swmetric.ASTVisitorTest;

import org.junit.Assert;
import org.junit.Test;
import org.team4.team4_project.metric_calculation.ASTVisitorSearch;
import org.team4.team4_project.metric_calculation.MetricInfo;
import org.team4.team4_project.metric_calculation.MetricMain;

import java.io.IOException;

public class NumLoopTest {
    MetricMain metricMain = new MetricMain();

    @Test
    public void testForLoop() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\cyclomatic_testfile\\ForLoopExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(3, metricInfo.getNumLoop());
    }

    @Test
    public void testWhileLoop() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\cyclomatic_testfile\\WhileLoopExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(2, metricInfo.getNumLoop());
    }
}
