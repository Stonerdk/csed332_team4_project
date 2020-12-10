package org.team4.team4_project.git;

import java.util.ArrayList;
import java.util.List;

public class ChurnResult {
    private int linesAdded = 0; // 추가 된 코드 줄 수
    private int linesDeleted = 0; // 삭제 된 코드 줄 수
    private String code = ""; // 해당 commit의 code string
    private int commitDate = 0; // 커밋된 시간 (datetime)
    private String branchName = ""; // 브랜치명
    private String commmitHash = "";

    private List<String> codelist = new ArrayList<String>(); // line 별로 code 저장하는 list

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
