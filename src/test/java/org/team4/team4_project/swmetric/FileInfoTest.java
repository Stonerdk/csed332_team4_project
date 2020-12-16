package org.team4.team4_project.swmetric;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.team4.team4_project.git.ChurnResult;
import org.team4.team4_project.metric_calculation.CommitInfo;
import org.team4.team4_project.metric_calculation.FileInfo;

import java.util.ArrayList;
import java.util.List;

public class FileInfoTest {
    FileInfo file1;
    CommitInfo commit1_1;
    CommitInfo commit1_2;
    @Before
    public void initTest(){
        file1 = new FileInfo();
        commit1_1 = new CommitInfo();
        commit1_1.setCommitHash("011");
        commit1_1.setBranchName("master");
        ChurnResult tempCR = new ChurnResult();
        tempCR.setcode("interface Account {\n" +
                "    /**\n" +
                "     * Return the account number\n" +
                "     *\n" +
                "     * @return the account number\n" +
                "     */\n" +
                "    int getAccountNumber();\n" +
                "\n" +
                "    /**\n" +
                "     * Return the current balance of the account\n" +
                "     *\n" +
                "     * @return the balance\n" +
                "     */\n" +
                "    double getBalance();\n" +
                "\n" +
                "    /**\n" +
                "     * Return the name of the owner\n" +
                "     *\n" +
                "     * @return the owner name\n" +
                "     */\n" +
                "    String getOwner();\n" +
                "\n" +
                "    /**\n" +
                "     * Update the balance according to the interest rate and elapsed date.\n" +
                "     *\n" +
                "     * @param elapsedDate\n" +
                "     */\n" +
                "    void updateBalance(int elapsedDate);\n" +
                "\n" +
                "    /**\n" +
                "     * Deposit a given amount of money\n" +
                "     *\n" +
                "     * @param amount of money\n" +
                "     */\n" +
                "    void deposit(double amount);\n" +
                "\n" +
                "    /**\n" +
                "     * Withdraw a given amount of money\n" +
                "     *\n" +
                "     * @param amount of money\n" +
                "     * @throws IllegalOperationException if not possible\n" +
                "     */\n" +
                "    void withdraw(double amount) throws IllegalOperationException;\n" +
                "}");
        commit1_1.setChurn(tempCR);

        commit1_2 = new CommitInfo();
        commit1_2.setCommitHash("012");
        commit1_2.setBranchName("master");
        ChurnResult tempCR2 = new ChurnResult();
        tempCR2.setcode("class LowInterestAccount implements Account {\n" +
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
        commit1_2.setChurn(tempCR2);
        List<CommitInfo> tempComList = new ArrayList<CommitInfo>();
        tempComList.add(commit1_1);
        tempComList.add(commit1_2);
        file1.setComInfoList(tempComList);
        file1.setFileName("file1");
        file1.setFilePath("project/main/csed/java/file1");
    }

    @Test
    public void CommitListTest(){
        Assert.assertEquals(2, file1.getComInfoList().size());
        Assert.assertEquals(commit1_1.getCommitHash(), file1.getComInfoList().get(0).getCommitHash());
        Assert.assertEquals(commit1_2.getCommitHash(), file1.getComInfoList().get(1).getCommitHash());
    }

    @Test
    public void FileNameTest(){
        Assert.assertEquals("file1", file1.getFileName());
    }

    @Test
    public void isProjTest(){
        Assert.assertFalse(file1.isProject());
        file1.setIsProject();
        Assert.assertTrue(file1.isProject());
    }

    @Test
    public void FilePathTest(){
        Assert.assertEquals("project/main/csed/java/file1", file1.getFilePath());
    }
}
