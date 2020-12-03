package org.team4.team4_project.metric_calculation;

import org.team4.team4_project.git.ChurnResult;

import java.util.Date;

public class CommitInfo {
    //Basic Information
    public String commitHash = "arb commit hash";
    public String branchName = "arb branch name";
    public ChurnResult churn = new ChurnResult();
    public Date date = new Date(20201203);


    //Metric Information
    public double halProgVocab;
    public double halProgLen;
    public double halCalProgLen;
    public double halVolume;
    public double halDifficulty;
    public double halEffort;
    public double halTime;
    public double halNumBugs;
    public double cyclomaticComplexity;
    public double maintainabilityIndex;

    public Boolean isBasicInfoFilled(){
        if(commitHash == null )
            return false;
        if(branchName == null)
            return false;
        if(branchName == null)
            return false;
        if(date == null )
            return false;

        return true;
    }

}
