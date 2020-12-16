package org.team4.team4_project.swmetric;

import org.junit.Assert;
import org.junit.Test;
import org.team4.team4_project.git.ChurnResult;
import org.team4.team4_project.metric_calculation.ASTVisitorSearch;
import org.team4.team4_project.metric_calculation.CommitInfo;
import org.team4.team4_project.metric_calculation.MetricInfo;
import org.team4.team4_project.metric_calculation.MetricMain;

import java.util.ArrayList;

public class MetricInfoTest {
    MetricInfo testMetricInfo = new MetricInfo();

    MetricMain metricMain = new MetricMain();
    ChurnResult tempChurn = new ChurnResult();
    CommitInfo tempComInfo = new CommitInfo();

    public void test_setting() {
        tempChurn.setcode("class LowInterestAccount implements Account {\n" +
                "    //TODO implement this\n" +
                "    private int accountNumber;\n" +
                "    private double balance;\n" +
                "    private String owner;\n" +
                "\n" +
                "    public LowInterestAccount(int aN, double bl, String ow){\n" +
                "        accountNumber = aN;\n" +
                "        balance = bl;\n" +
                "        owner = ow;\n" +
                "    }\n" +
                "\n" +
                "    public int getAccountNumber() {\n" +
                "        //TODO implement this\n" +
                "        return accountNumber;\n" +
                "    }\n" +
                "\n" +
                "    public double getBalance() {\n" +
                "        //TODO implement this\n" +
                "        return balance;\n" +
                "    }\n" +
                "\n" +
                "    public String getOwner() {\n" +
                "        //TODO implement this\n" +
                "        return owner;\n" +
                "    }\n" +
                "\n" +
                "    public void updateBalance(int elapsedDate) {\n" +
                "        //TODO implement this\n" +
                "        for (int i=0 ; i<elapsedDate ; i++)\n" +
                "            balance *= 1.005;\n" +
                "        return;\n" +
                "    }\n" +
                "\n" +
                "    public void deposit(double amount) {\n" +
                "        //TODO implement this\n" +
                "        balance += amount;\n" +
                "        return;\n" +
                "    }\n" +
                "\n" +
                "    public void withdraw(double amount) throws IllegalOperationException {\n" +
                "        //TODO implement this\n" +
                "        balance -= amount;\n" +
                "        return;\n" +
                "    }\n" +
                "}");

        tempComInfo.setChurn(tempChurn);

        ASTVisitorSearch tempVisitor = metricMain.parse(tempComInfo.getChurn().getcode().toCharArray(), 1);

        testMetricInfo.setByCommitInfo(tempComInfo);
        testMetricInfo.setByVisitor(tempVisitor);
        testMetricInfo.setToCommitInfo(tempComInfo);
    }
    @Test
    public void OperandTest() {
        test_setting();
        Assert.assertEquals(11, testMetricInfo.getDistOperands());
        Assert.assertEquals(6, testMetricInfo.getDistOperators());
        Assert.assertEquals(17, testMetricInfo.getTotalOperands());
        Assert.assertEquals(9, testMetricInfo.getTotalOperators());
    }
    @Test
    public void HalsteadTest() {
        test_setting();
        Assert.assertEquals(17, Math.round(testMetricInfo.getHalsteadVocabulary()));
        Assert.assertEquals(26, Math.round(testMetricInfo.getHalsteadProgLength()));
        Assert.assertEquals(54, Math.round(testMetricInfo.getHalsteadCalProgLength()));
        Assert.assertEquals(106, Math.round(testMetricInfo.getHalsteadVolume()));
        Assert.assertEquals(5, Math.round(testMetricInfo.getHalsteadDifficulty()));
        Assert.assertEquals(493, Math.round(testMetricInfo.getHalsteadEffort()));
        Assert.assertEquals(27, Math.round(testMetricInfo.getHalsteadTimeRequired()));
        Assert.assertEquals(0, Math.round(testMetricInfo.getHalsteadNumDelBugs()));
    }
    @Test
    public void CyclomaticTest() {
        test_setting();
        Assert.assertEquals(2, testMetricInfo.getCyclomaticComplexity());
    }
    @Test
    public void Code_Comment_Test() {
        test_setting();
        Assert.assertEquals(46, testMetricInfo.getCodeLen());
        Assert.assertEquals(7, testMetricInfo.getCommentLen());
    }
    @Test
    public void ExtraMetricTest() {
        test_setting();
        Assert.assertEquals(7, testMetricInfo.getNumMethod());
        Assert.assertEquals(1, testMetricInfo.getNumLoop());
        Assert.assertEquals(0, testMetricInfo.getNumImport());
    }


}
