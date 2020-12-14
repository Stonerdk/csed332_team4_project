package org.team4.team4_project.swmetric;

import org.junit.Assert;
import org.junit.Test;
import org.team4.team4_project.metric_calculation.ASTVisitorSearch;
import org.team4.team4_project.metric_calculation.MetricInfo;
import org.team4.team4_project.metric_calculation.MetricMain;

import java.io.IOException;

public class NumImportTest {
    MetricMain metricMain = new MetricMain();
    @Test
    public void testImport() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\ImportExample.java";
        char[] fileread = metricMain.ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = metricMain.parse(fileread, 1);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(5, metricInfo.getNumImport());
    }
}
