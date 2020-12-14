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

public class MethodPerClassTest {
    MetricMain metricMain = new MetricMain();

    @Test
    public void testsample() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\MethodDeclarationExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(4, metricInfo.getNumMethod());
    }
}