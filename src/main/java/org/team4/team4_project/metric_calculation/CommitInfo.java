package org.team4.team4_project.metric_calculation;

import org.team4.team4_project.git.ChurnResult;

import java.util.Date;
import java.util.HashMap;

public class CommitInfo implements Cloneable {
    //Basic Information
    private String commitHash = "arb commit hash";
    private String branchName = "master";
    private ChurnResult churn = new ChurnResult();
    private Date date = new Date(20201203);


    //Metric Information
    private HashMap<String, Integer> operands = new HashMap<String, Integer>();
    private HashMap<String, Integer> operators = new HashMap<String, Integer>();
    private double halProgVocab;
    private double halProgLen;
    private double halCalProgLen;
    private double halVolume;
    private double halDifficulty;
    private double halEffort;
    private double halTime;
    private double halNumBugs;
    private double cyclomaticComplexity;
    private double maintainabilityIndex;
    private int codeLen;
    private int commentLen;
    private int num_methods;

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


    //methods to set, get
    //methods to set, get
    public HashMap<String, Integer> getOperands() {return operands;}
    public void setOperands(HashMap<String, Integer> oprd){operands = oprd;}

    public HashMap<String, Integer> getOperators() {return operators;}
    public void setOperators(HashMap<String, Integer> oprt){operators = oprt;}

    public String getCommitHash(){return commitHash;}
    public void setCommitHash(String str){commitHash = str;};

    public String getBranchName(){return branchName;}
    public void setBranchName(String str){branchName = str;}

    public ChurnResult getChurn(){return churn;}
    public void setChurn(ChurnResult ch){churn = ch;};

    public Date getDate(){return date;}
    public void setDate(Date str){date = str;}

    public double getHalProgVocab(){ return halProgVocab; }
    public void setHalProgVocab(double d){halProgVocab = d;}

    public double getHalProgLen(){return halProgLen; }
    public void setHalProgLen(double d){halProgLen = d;}

    public double getHalCalProgLen(){return halCalProgLen; }
    public void setHalCalProgLen(double d){halCalProgLen = d;}

    public double getHalVolume(){ return halVolume; }
    public void setHalVolume(double d){halVolume = d;}

    public double getHalDifficulty(){return halDifficulty; }
    public void setHalDifficulty(double d){halDifficulty = d;}

    public double getHalEffort(){return halEffort; }
    public void setHalEffort(double d){halEffort = d;}

    public double getHalTime(){return halTime; }
    public void setHalTime(double d){halTime = d;}

    public double getHalNumBugs(){return halNumBugs;}
    public void setHalNumBugs(double d){halNumBugs = d;}

    public double getCyclomaticComplexity(){return cyclomaticComplexity; }
    public void setCyclomaticComplexity(double d){cyclomaticComplexity = d;}

    public double getMaintainabilityIndex(){return maintainabilityIndex;}
    public void setMaintainabilityIndex(double d){maintainabilityIndex = d;}

    public int getCodeLen(){return codeLen;}
    public void setCodeLen(int cl){codeLen = cl;}

    public int getCommentLen(){return commentLen;}
    public void setCommentLen(int cl){commentLen = cl;}

    public int getNumMethods(){return num_methods;}
    public void setNumMethods(int nm){num_methods = nm;}

    public CommitInfo clone(){
        CommitInfo vo = null;
        try {
            vo = (CommitInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return vo;
    }

}
