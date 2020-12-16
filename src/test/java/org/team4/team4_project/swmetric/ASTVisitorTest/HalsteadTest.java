package org.team4.team4_project.swmetric.ASTVisitorTest;

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
    public void testPostfix() {
        char[] fileread = (
                "public class Basic {\n" +
                "    public static void main(String args[]) {\n" +
                "        int a = 2;\n" +
                "        int b = 2;\n" +
                "        a++;\n" +
                "        a--;\n" +
                "    }\n" +
                "}\n").toCharArray();

        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(6, metricInfo.getTotalOperands());
        Assert.assertEquals(4, metricInfo.getTotalOperators());
        Assert.assertEquals(3, metricInfo.getDistOperands());
        Assert.assertEquals(3, metricInfo.getDistOperators());
    }
    @Test
    public void testPrefix() {
        char[] fileread = (
                "public class Basic {\n" +
                        "    public static void main(String args[]) {\n" +
                        "        int a = 2;\n" +
                        "        int b = 2;\n" +
                        "        boolean c = true;\n" +
                        "        ++a;\n" +
                        "        --a;\n" +
                        "        a = ~a;\n" +
                        "        c = !c;\n" +
                        "    }\n" +
                        "}\n").toCharArray();

        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(14, metricInfo.getTotalOperands());
        Assert.assertEquals(9, metricInfo.getTotalOperators());
        Assert.assertEquals(7, metricInfo.getDistOperands());
        Assert.assertEquals(5, metricInfo.getDistOperators());
    }
    @Test
    public void testArithmetic() {
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
                        "    }\n" +
                        "}\n").toCharArray();

        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(24, metricInfo.getTotalOperands());
        Assert.assertEquals(12, metricInfo.getTotalOperators());
        Assert.assertEquals(8, metricInfo.getDistOperands());
        Assert.assertEquals(6, metricInfo.getDistOperators());
    }
    @Test
    public void testShift() {
        //Multiplication, Addition, Shift
        char[] fileread = (
                "public class Basic {\n" +
                        "    public static void main(String args[]) {\n" +
                        "        int a = 2;\n" +
                        "        a = a << 2;\n" +
                        "        a = a >> 2;\n" +
                        "        a = a >>> 3;\n" +
                        "    }\n" +
                        "}\n").toCharArray();

        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(14, metricInfo.getTotalOperands());
        Assert.assertEquals(7, metricInfo.getTotalOperators());
        Assert.assertEquals(6, metricInfo.getDistOperands());
        Assert.assertEquals(4, metricInfo.getDistOperators());
    }
    @Test
    public void testRelational_Logical() {
        //Multiplication, Addition, Shift
        char[] fileread = (
                "public class Basic {\n" +
                        "    public static void main(String args[]) {\n" +
                        "        int a = 2;\n" +
                        "        int b = 2;\n" +
                        "        if((a<1) && (a>1))\n" +
                        "            System.out.println(a);\n" +
                        "        if(b<=0 || b>=2)\n" +
                        "            System.out.println(b);\n" +
                        "        if(a==3)\n" +
                        "            System.out.println(a);\n" +
                        "        if(b!=1)\n" +
                        "            System.out.println(b);" +
                        "    }\n" +
                        "}\n").toCharArray();

        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(20, metricInfo.getTotalOperands());
        Assert.assertEquals(10, metricInfo.getTotalOperators());
        Assert.assertEquals(10, metricInfo.getDistOperands());
        Assert.assertEquals(9, metricInfo.getDistOperators());
    }
    @Test
    public void testBitewise() {
        //Multiplication, Addition, Shift
        char[] fileread = (
                "public class Basic {\n" +
                        "    public static void main(String args[]) {\n" +
                        "        int a = 2;\n" +
                        "        int b = 2;\n" +
                        "        a = a & b;\n" +
                        "        a = a ^ 2;\n" +
                        "        a = a | 3;\n" +
                        "    }\n" +
                        "}\n").toCharArray();

        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        Assert.assertEquals(16, metricInfo.getTotalOperands());
        Assert.assertEquals(8, metricInfo.getTotalOperators());
        Assert.assertEquals(7, metricInfo.getDistOperands());
        Assert.assertEquals(4, metricInfo.getDistOperators());
    }
    @Test
    public void testConditional() {
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

        Assert.assertEquals(8, metricInfo.getTotalOperands());
        Assert.assertEquals(4, metricInfo.getTotalOperators());
        Assert.assertEquals(4, metricInfo.getDistOperands());
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

        Assert.assertEquals(26, metricInfo.getTotalOperands());
        Assert.assertEquals(13, metricInfo.getTotalOperators());
        Assert.assertEquals(4, metricInfo.getDistOperands());
        Assert.assertEquals(12, metricInfo.getDistOperators());
    }
}


