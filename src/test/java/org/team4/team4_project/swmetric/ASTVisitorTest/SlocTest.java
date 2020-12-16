package org.team4.team4_project.swmetric.ASTVisitorTest;

import org.junit.Assert;
import org.junit.Test;
import org.team4.team4_project.metric_calculation.ASTVisitorSearch;
import org.team4.team4_project.metric_calculation.MetricInfo;
import org.team4.team4_project.metric_calculation.MetricMain;

import java.io.IOException;

public class SlocTest {
    MetricMain metricMain = new MetricMain();

    @Test
    public void testcodeline1() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\cyclomatic_testfile\\IfElseExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(19, metricInfo.getCodeLen());
    }

    public void testcodeline2() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\MethodDeclarationExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(24, metricInfo.getCodeLen());
    }

}

