package org.team4.team4_project.swmetric;

import static org.junit.Assert.*;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.*;
import org.junit.Assert;
import org.junit.Test;
import org.team4.team4_project.metric_calculation.ASTVisitorSearch;
import org.team4.team4_project.metric_calculation.MetricInfo;
import org.team4.team4_project.metric_calculation.MetricMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CyclomaticComplexityTest {
    MetricMain metricMain = new MetricMain();

    @Test
    public void testIfElse() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\IfElseExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(7, metricInfo.getCyclomaticComplexity());
    }
    @Test
    public void testForLoop() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\ForLoopExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(3, metricInfo.getCyclomaticComplexity());
    }
    @Test
    public void testWhileLoop() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\WhileLoopExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(3, metricInfo.getCyclomaticComplexity());
    }
    @Test
    public void testAssert() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\AssertExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(3, metricInfo.getCyclomaticComplexity());
    }
    @Test
    public void testSwitchCase() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\SwitchCaseExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(3, metricInfo.getCyclomaticComplexity());
    }
    @Test
    public void testTryCatch() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\TryCatchExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(2, metricInfo.getCyclomaticComplexity());
    }
    @Test
    public void testConditionOperator() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\ConditionOperatorExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(3, metricInfo.getCyclomaticComplexity());
    }
    @Test
    public void testComplex1() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\ComplexExample1.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(5, metricInfo.getCyclomaticComplexity());
    }
    @Test
    public void testComprehension() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\ComprehensionExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(6, metricInfo.getCyclomaticComplexity());
    }


}
