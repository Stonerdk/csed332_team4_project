package org.team4.team4_project.git;

import java.util.ArrayList;
import java.util.List;

public class ChurnResult {
    private int linesAdded = 0;
    private int linesDeleted = 0;
    private String code = "";
    private int commitDate = 0;
    private String branchName = "";
    private String commmitHash = "";

    private List<String> codelist = new ArrayList<String>();

    public int getTotalChurn() {
        return getLinesAdded() + getLinesDeleted();
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesDeleted() {
        return linesDeleted;
    }

    public void plusLinesAdded(){
        linesAdded++;
    }

    public void plusLinesDeleted(){
        linesDeleted++;
    }

    public String getcode() { return code;}

    public void setcode(String c) {code = c;}

    public List<String> getcodelist() { return codelist;}

    public void setcodelist(List<String> s) { codelist = s;}

    public void setCommmitHash(String h) { commmitHash = h; }

    public String getCommmitHash() { return commmitHash; }

    public void setBranchName(String b) { branchName = b; }

    public String getBranchName() { return branchName; }

    public void setCommitDate(Integer d) { commitDate = d; }

    public Integer getCommitDate() { return commitDate; }
}
