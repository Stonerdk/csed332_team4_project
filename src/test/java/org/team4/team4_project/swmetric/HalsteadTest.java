package org.team4.team4_project.swmetric;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.*;
import org.junit.Assert;
import org.junit.Test;
import org.team4.team4_project.metric_calculation.ASTVisitorSearch;
import org.team4.team4_project.metric_calculation.MetricInfo;
import org.team4.team4_project.metric_calculation.MetricMain;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HalsteadTest {
    MetricMain metricMain = new MetricMain();
    @Test
    public void testAssignment() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\halstead_testfile\\AssignmentExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 2);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(4, metricInfo.getTotalOperands());
        Assert.assertEquals(2, metricInfo.getTotalOperators());
        Assert.assertEquals(3, metricInfo.getDistOperands());
        Assert.assertEquals(1, metricInfo.getDistOperators());

        System.out.println("TotalOperands : " + metricInfo.getTotalOperands());
        System.out.println("TotalOperators : " + metricInfo.getTotalOperators());
        System.out.println("DistinctOperands : " + metricInfo.getDistOperands());
        System.out.println("DistinctOperators : " + metricInfo.getDistOperators());
    }
    /*
    @Test
    public void testForLoop() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\ForLoopExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 2);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //Assert.assertEquals(17, metricInfo.getTotalOperands());
        Assert.assertEquals(7, metricInfo.getTotalOperators());
        //Assert.assertEquals(9, metricInfo.getDistOperands());
        Assert.assertEquals(6, metricInfo.getDistOperators());

        System.out.println("TotalOperands : " + metricInfo.getTotalOperands());
        System.out.println("TotalOperators : " + metricInfo.getTotalOperators());
        System.out.println("DistinctOperands : " + metricInfo.getDistOperands());
        System.out.println("DistinctOperators : " + metricInfo.getDistOperators());
    }
     */
}


