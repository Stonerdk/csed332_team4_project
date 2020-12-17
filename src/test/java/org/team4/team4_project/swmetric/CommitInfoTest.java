package org.team4.team4_project.swmetric;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.team4.team4_project.git.ChurnResult;
import org.team4.team4_project.metric_calculation.ASTVisitorSearch;
import org.team4.team4_project.metric_calculation.CommitInfo;
import org.team4.team4_project.metric_calculation.MetricInfo;
import org.team4.team4_project.metric_calculation.MetricMain;

import java.util.Date;

public class CommitInfoTest {
    MetricInfo testMetricInfo = new MetricInfo();

    MetricMain metricMain = new MetricMain();
    ChurnResult tempChurn = new ChurnResult();
    CommitInfo testComInfo = new CommitInfo();

    @Before
    public void initTest() {
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
        testComInfo.setDate(new Date(0));

        testComInfo.setChurn(tempChurn);

        ASTVisitorSearch tempVisitor = metricMain.parse(testComInfo.getChurn().getcode().toCharArray(), 1);

        testMetricInfo.setByCommitInfo(testComInfo);
        testMetricInfo.setByVisitor(tempVisitor);
        testMetricInfo.setToCommitInfo(testComInfo);
    }
    @Test
    public void OperandOperatorTest() {
        Assert.assertEquals("4", testComInfo.getOperands().get("balance").toString());
        Assert.assertEquals("4", testComInfo.getOperators().get("=").toString());
    }
    @Test
    public void GitTest() {
        Assert.assertEquals("arb commit hash", testComInfo.getCommitHash());
        Assert.assertEquals("master", testComInfo.getBranchName());
        Assert.assertEquals(0, testComInfo.getChurn().getLinesAdded());
    }
    @Test
    public void HalsteadTest() {
        Assert.assertEquals(17, Math.round(testComInfo.getHalProgVocab()));
        Assert.assertEquals(26, Math.round(testComInfo.getHalProgLen()));
        Assert.assertEquals(54, Math.round(testComInfo.getHalCalProgLen()));
        Assert.assertEquals(106, Math.round(testComInfo.getHalVolume()));
        Assert.assertEquals(5, Math.round(testComInfo.getHalDifficulty()));
        Assert.assertEquals(493, Math.round(testComInfo.getHalEffort()));
        Assert.assertEquals(27, Math.round(testComInfo.getHalTime()));
        Assert.assertEquals(0, Math.round(testComInfo.getHalNumBugs()));
    }
    @Test
    public void CyclomaticComplexityTest() {
        Assert.assertEquals(2, Math.round(testComInfo.getCyclomaticComplexity()));
    }
    @Test
    public void MaintainabilityTest() {
        Assert.assertEquals(49, Math.round(testComInfo.getMaintainabilityIndex()));
    }
    @Test
    public void Code_Comment_Test() {
        Assert.assertEquals(46, testComInfo.getCodeLen());
        Assert.assertEquals(7, testComInfo.getCommentLen());
    }
    @Test
    public void ExtraMetricTest() {
        Assert.assertEquals(7, testComInfo.getNumMethod());
        Assert.assertEquals(1, testComInfo.getNumLoop());
        Assert.assertEquals(0, testComInfo.getNumImport());
    }
    @Test
    public void getDateTest(){
        Assert.assertEquals("Thu Jan 01 09:00:00 KST 1970", testComInfo.getDate().toString());
    }


}
