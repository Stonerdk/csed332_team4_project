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
    private int num_method;
    private int num_loop;
    private int num_import;

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
    /**
     * enable to get HashMap of operands stored in this class
     * @return operands which is HashMap<String, Integer>
     */
    public HashMap<String, Integer> getOperands() {return operands;}
    public void setOperands(HashMap<String, Integer> oprd){operands = oprd;}

    /**
     * enable to get HashMap of operators stored in this class
     * @return oprt which is HashMap<String, Integer>
     */
    public HashMap<String, Integer> getOperators() {return operators;}
    public void setOperators(HashMap<String, Integer> oprt){operators = oprt;}

    /**
     * enable to get commitHash string stored in this class
     * @return commitHash string stored in this class
     */
    public String getCommitHash(){return commitHash;}
    public void setCommitHash(String str){commitHash = str;};

    /**
     * enable to get branchName string stored in this class
     * @return branchName string stored in this class
     */
    public String getBranchName(){return branchName;}
    public void setBranchName(String str){branchName = str;}

    /**
     * enable to get churn class stored in this class
     * @return oprd which is HashMap<String, Integer>
     */
    public ChurnResult getChurn(){return churn;}
    public void setChurn(ChurnResult ch){churn = ch;};

    /**
     * enable to get date stored in this class
     * @return Date date stored in this class
     */
    public Date getDate(){return date;}
    public void setDate(Date str){date = str;}

    /**
     * enable to get halProgVocab value stored in this class
     * @return halProgVocab value stored in this class
     */
    public double getHalProgVocab(){ return halProgVocab; }
    public void setHalProgVocab(double d){halProgVocab = d;}

    /**
     * enable to get halProgLen value stored in this class
     * @return halProgLen value stored in this class
     */
    public double getHalProgLen(){return halProgLen; }
    public void setHalProgLen(double d){halProgLen = d;}

    /**
     * enable to get halCalProgLen stored in this class
     * @return halCalProgLen value stored in this class
     */
    public double getHalCalProgLen(){return halCalProgLen; }
    public void setHalCalProgLen(double d){halCalProgLen = d;}

    /**
     * enable to get halVolume value stored in this class
     * @return halVolume value stored in this class
     */
    public double getHalVolume(){ return halVolume; }
    public void setHalVolume(double d){halVolume = d;}

    /**
     * enable to get halDifficulty value stored in this class
     * @return halDifficulty value stored in this class
     */
    public double getHalDifficulty(){return halDifficulty; }
    public void setHalDifficulty(double d){halDifficulty = d;}

    /**
     * enable to get halEffort value stored in this class
     * @return halEffort value stored in this class
     */
    public double getHalEffort(){return halEffort; }
    public void setHalEffort(double d){halEffort = d;}

    /**
     * enable to get halTime value stored in this class
     * @return halTime value stored in this class
     */
    public double getHalTime(){return halTime; }
    public void setHalTime(double d){halTime = d;}

    /**
     * enable to get halNumBugs value stored in this class
     * @return halNumBugs value stored in this class
     */
    public double getHalNumBugs(){return halNumBugs;}
    public void setHalNumBugs(double d){halNumBugs = d;}

    /**
     * enable to get cyclomaticComplexity value stored in this class
     * @return cyclomaticComplexity value stored in this class
     */
    public double getCyclomaticComplexity(){return cyclomaticComplexity; }
    public void setCyclomaticComplexity(double d){cyclomaticComplexity = d;}

    /**
     * enable to get maintainability value stored in this class
     * @return maintainability value stored in this class
     */
    public double getMaintainabilityIndex(){return maintainabilityIndex;}
    public void setMaintainabilityIndex(double d){maintainabilityIndex = d;}

    /**
     * enable to get codeLen value stored in this class
     * @return codeLen value stored in this class
     */
    public int getCodeLen(){return codeLen;}
    public void setCodeLen(int cl){codeLen = cl;}

    /**
     * enable to get commentLen value stored in this class
     * @return commentLen value stored in this class
     */
    public int getCommentLen(){return commentLen;}
    public void setCommentLen(int cl){commentLen = cl;}

    /**
     * enable to get num_method value stored in this class
     * @return num_method value stored in this class
     */
    public int getNumMethod(){return num_method;}
    public void setNumMethod(int nm){num_method = nm;}

    /**
     * enable to get num_loop value stored in this class
     * @return num_loop value stored in this class
     */
    public int getNumLoop(){return num_loop;}
    public void setNumLoop(int lp){num_loop = lp;}

    /**
     * enable to get num_import value stored in this class
     * @return num_import value stored in this class
     */
    public int getNumImport(){return num_import;}
    public void setNumImport(int imp){num_import = imp;}

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
