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
    public void testPostfixAndPrefix() throws IOException {
        char[] fileread = (
                "public class Basic {\n" +
                "    public static void main(String args[]) {\n" +
                "        int a = 2;\n" +
                "        int b = 2;\n" +
                "        int b = 2;\n" +
                "        a++;\n" +
                "        a--;\n" +
                "        ++a;\n" +
                "        --a;\n" +
                "        a = ~a;\n" +
                "        boolean c = true;\n" +
                "        c = !c;\n" +
                "    }\n" +
                "}\n").toCharArray();

        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(18, metricInfo.getTotalOperands());
        Assert.assertEquals(12, metricInfo.getTotalOperators());
        Assert.assertEquals(7, metricInfo.getDistOperands());
        Assert.assertEquals(5, metricInfo.getDistOperators());
    }
    @Test
    public void testBasicOperation1() throws IOException {
        //Multiplication, Addition, Shift
        char[] fileread = (
                "public class Basic {\n" +
                        "    public static void main(String args[]) {\n" +
                        "        int a = 2;\n" +
                        "        int b = 2;\n" +
                        "        a = a + 2;\n" +
                        "        a = a - 2;\n" +
                        "        a = a * a;\n" +
                        "        a = a / a;\n" +
                        "        a = a % 2;\n" +
                        "        a = a << 2;\n" +
                        "        a = a >> 2;\n" +
                        "        a = a >>> 2;\n" +
                        "    }\n" +
                        "}\n").toCharArray();

        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(36, metricInfo.getTotalOperands());
        Assert.assertEquals(18, metricInfo.getTotalOperators());
        Assert.assertEquals(11, metricInfo.getDistOperands());
        Assert.assertEquals(9, metricInfo.getDistOperators());
    }
    @Test
    public void testBasicOperation2() throws IOException {
        //Multiplication, Addition, Shift
        char[] fileread = (
                "public class Basic {\n" +
                        "    public static void main(String args[]) {\n" +
                        "        int a = 2;\n" +
                        "        a = (a == 2) ? 3 : 2;\n" +
                        "    }\n" +
                        "}\n").toCharArray();

        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        System.out.println("TotalOperands : " + metricInfo.getTotalOperands());
        System.out.println("TotalOperators : " + metricInfo.getTotalOperators());
        System.out.println("DistinctOperands : " + metricInfo.getDistOperands());
        System.out.println("DistinctOperators : " + metricInfo.getDistOperators());

        Assert.assertEquals(7, metricInfo.getTotalOperands());
        Assert.assertEquals(4, metricInfo.getTotalOperators());
        Assert.assertEquals(3, metricInfo.getDistOperands());
        Assert.assertEquals(3, metricInfo.getDistOperators());
    }
    @Test
    public void testAssignment() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\halstead_testfile\\AssignmentExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //Assert.assertEquals(14, metricInfo.getTotalOperands());
        //Assert.assertEquals(2, metricInfo.getTotalOperators());
        //Assert.assertEquals(13, metricInfo.getDistOperands());
        //Assert.assertEquals(1, metricInfo.getDistOperators());

        System.out.println("TotalOperands : " + metricInfo.getTotalOperands());
        System.out.println("TotalOperators : " + metricInfo.getTotalOperators());
        System.out.println("DistinctOperands : " + metricInfo.getDistOperands());
        System.out.println("DistinctOperators : " + metricInfo.getDistOperators());
    }
}


