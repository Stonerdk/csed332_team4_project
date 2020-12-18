package org.team4.team4_project.metric_calculation;

import org.team4.team4_project.git.ChurnResult;

import java.util.Date;
import java.util.HashMap;

/**
 * A class used to store metrics about each code in each code
 * @author Tahyeong, Chaeyoon
 */
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


    /**
     * enable to get HashMap of operands stored in this class
     * @return operands which is HashMap<String, Integer>
     */
    public HashMap<String, Integer> getOperands() {return operands;}

    /**
     * enable to set HashMap of operands to be stored in this class
     * @param oprd HashMap<String, Integer> which mapps each operand's name to the number of usage of the operand
     */
    public void setOperands(HashMap<String, Integer> oprd){operands = oprd;}

    /**
     * enable to get HashMap of operators stored in this class
     * @return oprt which is HashMap<String, Integer>
     */
    public HashMap<String, Integer> getOperators() {return operators;}

    /**
     * enable to set HashMap of operators to be stored in this class
     * @param oprt HashMap<String, Integer> which mapps each operator's name to the number of usage of the operator
     */
    public void setOperators(HashMap<String, Integer> oprt){operators = oprt;}

    /**
     * enable to get commitHash string stored in this class
     * @return commitHash string stored in this class
     */
    public String getCommitHash(){return commitHash;}

    /**
     * enable to set commitHash string in this class
     * @param str a string to be stored as a commitHash
     */
    public void setCommitHash(String str){commitHash = str;};

    /**
     * enable to get branchName string stored in this class
     * @return branchName string stored in this class
     */
    public String getBranchName(){return branchName;}

    /**
     * enable to set branchName string in this class
     * @param str a string to be stored as a branchName
     */
    public void setBranchName(String str){branchName = str;}

    /**
     * enable to get churn class stored in this class
     * @return oprd which is HashMap<String, Integer>
     */
    public ChurnResult getChurn(){return churn;}

    /**
     * enable to set churn in this class
     * @param ch a ChurnResult class to be stored as a churn
     */
    public void setChurn(ChurnResult ch){churn = ch;};

    /**
     * enable to get date stored in this class
     * @return Date date stored in this class
     */
    public Date getDate(){return date;}

    /**
     * enable to set date in this class
     * @param str a Date class to be stored as a date
     */
    public void setDate(Date str){date = str;}

    /**
     * enable to get halProgVocab value stored in this class
     * @return halProgVocab value stored in this class
     */
    public double getHalProgVocab(){ return halProgVocab; }

    /**
     * enable to set halProgVocab in this class
     * @param d a double value to be stored as a halProgVocab
     */
    public void setHalProgVocab(double d){halProgVocab = d;}

    /**
     * enable to get halProgLen value stored in this class
     * @return halProgLen value stored in this class
     */
    public double getHalProgLen(){return halProgLen; }

    /**
     * enable to set halProgLen in this class
     * @param d a double value to be stored as a halProgLen
     */
    public void setHalProgLen(double d){halProgLen = d;}

    /**
     * enable to get halCalProgLen stored in this class
     * @return halCalProgLen value stored in this class
     */
    public double getHalCalProgLen(){return halCalProgLen; }

    /**
     * enable to set halCalProgLen in this class
     * @param d a double value to be stored as a halCalProgLen
     */
    public void setHalCalProgLen(double d){halCalProgLen = d;}

    /**
     * enable to get halVolume value stored in this class
     * @return halVolume value stored in this class
     */
    public double getHalVolume(){ return halVolume; }

    /**
     * enable to set halVolume in this class
     * @param d a double value to be stored as a halVolume
     */
    public void setHalVolume(double d){halVolume = d;}

    /**
     * enable to get halDifficulty value stored in this class
     * @return halDifficulty value stored in this class
     */
    public double getHalDifficulty(){return halDifficulty; }

    /**
     * enable to set halDifficulty in this class
     * @param d a double value to be stored as a halDifficulty
     */
    public void setHalDifficulty(double d){halDifficulty = d;}

    /**
     * enable to get halEffort value stored in this class
     * @return halEffort value stored in this class
     */
    public double getHalEffort(){return halEffort; }

    /**
     * enable to set halEffort in this class
     * @param d a double value to be stored as a halEffort
     */
    public void setHalEffort(double d){halEffort = d;}

    /**
     * enable to get halTime value stored in this class
     * @return halTime value stored in this class
     */
    public double getHalTime(){return halTime; }

    /**
     * enable to set halTime in this class
     * @param d a double value to be stored as a halTime
     */
    public void setHalTime(double d){halTime = d;}

    /**
     * enable to get halNumBugs value stored in this class
     * @return halNumBugs value stored in this class
     */
    public double getHalNumBugs(){return halNumBugs;}

    /**
     * enable to set halNumBugs in this class
     * @param d a double value to be stored as a halNumBugs
     */
    public void setHalNumBugs(double d){halNumBugs = d;}

    /**
     * enable to get cyclomaticComplexity value stored in this class
     * @return cyclomaticComplexity value stored in this class
     */
    public double getCyclomaticComplexity(){return cyclomaticComplexity; }

    /**
     * enable to set cyclomaticComplexity in this class
     * @param d a double value to be stored as a cyclomaticComplexity
     */
    public void setCyclomaticComplexity(double d){cyclomaticComplexity = d;}

    /**
     * enable to get maintainability value stored in this class
     * @return maintainability value stored in this class
     */
    public double getMaintainabilityIndex(){return maintainabilityIndex;}

    /**
     * enable to set maintainability in this class
     * @param d a double value to be stored as a maintainability
     */
    public void setMaintainabilityIndex(double d){maintainabilityIndex = d;}

    /**
     * enable to get codeLen value stored in this class
     * @return codeLen value stored in this class
     */
    public int getCodeLen(){return codeLen;}

    /**
     * enable to set codeLen in this class
     * @param cl a double value to be stored as a codeLen
     */
    public void setCodeLen(int cl){codeLen = cl;}

    /**
     * enable to get commentLen value stored in this class
     * @return commentLen value stored in this class
     */
    public int getCommentLen(){return commentLen;}

    /**
     * enable to set commnetLen in this class
     * @param cl a double value to be stored as a commentLen
     */
    public void setCommentLen(int cl){commentLen = cl;}

    /**
     * enable to get num_method value stored in this class
     * @return num_method value stored in this class
     */
    public int getNumMethod(){return num_method;}

    /**
     * enable to set num_method in this class
     * @param nm a double value to be stored as a num_method
     */
    public void setNumMethod(int nm){num_method = nm;}

    /**
     * enable to get num_loop value stored in this class
     * @return num_loop value stored in this class
     */
    public int getNumLoop(){return num_loop;}

    /**
     * enable to set num_loop in this class
     * @param lp a double value to be stored as a num_loop
     */
    public void setNumLoop(int lp){num_loop = lp;}

    /**
     * enable to get num_import value stored in this class
     * @return num_import value stored in this class
     */
    public int getNumImport(){return num_import;}

    /**
     * enable to set num_import in this class
     * @param imp a double value to be stored as a num_import
     */
    public void setNumImport(int imp){num_import = imp;}



}
