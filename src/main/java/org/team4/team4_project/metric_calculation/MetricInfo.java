package org.team4.team4_project.metric_calculation;

import org.team4.team4_project.git.ChurnResult;
import org.team4.team4_project.git.CodeChurn;

import java.util.HashMap;
import java.util.Map;

public class MetricInfo {
    //operands, operators
    private HashMap<String, Integer> operands = new HashMap<String, Integer>();
    private HashMap<String, Integer> operators = new HashMap<String, Integer>();

    private int dist_operands;
    private int dist_operators;
    private int total_operands;
    private int total_operators;

    //Halstead Metric
    private int halSteadVocabulary=0;
    private int halSteadProgLength=0;
    private double halSteadCalProgLength=0;
    private double halSteadVolume=0;
    private double halSteadDifficulty=0;
    private double halSteadEffort=0;
    private double halSteadTimeRequired=0;
    private double halSteadNumDelBugs=0;

    //Cyclomatic Complexity
    private int cyclomaticComplexity = 0;

    //Code Line Length & Comment Line Length
    private int codeLen = 0;
    private int commentLen = 0;

    //Maintainability Index
    private double maintainabilityIndex = 0;

    // Code churn
    private ChurnResult churn = new ChurnResult();

    //Number of Methods per Class
    private int num_method = 0;

    private int num_loop = 0;
    private int num_import = 0;


    /**
     * Fill variables in this class by using operand, operator lists in visitor
     * @param visitor a visitor which was used to visit all nodes of tree made by parsing code
     */
    public void setByVisitor(ASTVisitorSearch visitor) {
        operands = visitor.names;
        operators = visitor.oprt;

        dist_operators = operators.size();
        dist_operands = operands.size();
        total_operators = operators.values().stream().reduce(0, Integer::sum);
        total_operands = operands.values().stream().reduce(0, Integer::sum);

        HalsteadMetric halsteadMetric = new HalsteadMetric(dist_operators, dist_operands, total_operators, total_operands);

        halSteadVocabulary = halsteadMetric.getVocabulary();
        halSteadProgLength = halsteadMetric.getProglen();
        halSteadCalProgLength = halsteadMetric.getCalcProgLen();
        halSteadVolume = halsteadMetric.getVolume();
        halSteadDifficulty = halsteadMetric.getDifficulty();
        halSteadEffort = halsteadMetric.getEffort();
        halSteadTimeRequired = halsteadMetric.getTimeReqProg();
        halSteadNumDelBugs = halsteadMetric.getTimeDelBugs();

        cyclomaticComplexity = visitor.cycloComplexity;

        codeLen = visitor.codeLen;
        commentLen = visitor.commentLen;

        maintainabilityIndex = (codeLen == 0) ? 100 : (halSteadVolume == 0) ? Math.max(0, 100 * (171.0 - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/(codeLen+1)), 0.5))) / 171.0) :
                Math.max(0, 100 * (171.0 - 5.2 * Math.log(halSteadVolume) - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/(codeLen+1)), 0.5))) / 171.0);

        num_method = visitor.num_method;
        num_loop = visitor.num_loop;
        num_import = visitor.num_import;

        return;
    }

    /**
     * enable to get dist_operands value in ths class
     * @return the number of dist operands
     */
    public int getDistOperands(){
        return dist_operands;
    }

    /**
     * enable to get dist_operators value in ths class
     * @return the number of dist operands
     */
    public int getDistOperators(){
        return dist_operators;
    }

    /**
     * enable to get total_operands value in ths class
     * @return the number of total operands
     */
    public int getTotalOperands(){
        return total_operands;
    }

    /**
     * enable to get total_operators value in ths class
     * @return the number of total operators
     */
    public int getTotalOperators(){
        return total_operators;
    }


    /**
     * enable to get halsteadVocabulary value in ths class
     * @return halsteadVocabulary stored in this class
     */
    public int getHalsteadVocabulary(){
        return halSteadVocabulary;
    }

    /**
     * enable to get halsteadProgLength value in ths class
     * @return halsteadProgLength stored in this class
     */
    public int getHalsteadProgLength(){
        return halSteadProgLength;
    }

    /**
     * enable to get halsteadCalProgLength value in ths class
     * @return halsteadCalProgLength stored in this class
     */
    public double getHalsteadCalProgLength(){
        return halSteadCalProgLength;
    }

    /**
     * enable to get halsteadVolume value in ths class
     * @return halsteadVolume stored in this class
     */
    public double getHalsteadVolume(){
        return halSteadVolume;
    }

    /**
     * enable to get halsteadDifficulty value in ths class
     * @return halsteadDifficulty stored in this class
     */
    public double getHalsteadDifficulty(){
        return halSteadDifficulty;
    }

    /**
     * enable to get halsteadEffort value in ths class
     * @return halsteadEffort stored in this class
     */
    public double getHalsteadEffort(){
        return halSteadEffort;
    }

    /**
     * enable to get halsteadTimeRequired value in ths class
     * @return halsteadTimeRequired stored in this class
     */
    public double getHalsteadTimeRequired(){
        return halSteadTimeRequired;
    }

    /**
     * enable to get halsteadNumDelBugs value in ths class
     * @return halsteadNumDelBugs stored in this class
     */
    public double getHalsteadNumDelBugs(){
        return halSteadNumDelBugs;
    }


    /**
     * enable to get cyclomaticComplexity value in ths class
     * @return cyclomaticComplexity stored in this class
     */
    public int getCyclomaticComplexity(){
        return cyclomaticComplexity;
    }


    /**
     * enable to get codeLen value in ths class
     * @return codeLen stored in this class
     */
    public int getCodeLen(){
        return codeLen;
    }

    /**
     * enable to get commentLen value in ths class
     * @return commentLen stored in this class
     */
    public int getCommentLen(){
        return commentLen;
    }

    /**
     * enable to get maintainability value in ths class
     * @return maintainability stored in this class
     */
    public double getMaintainabilityIndex(){
        return maintainabilityIndex;
    }


    /**
     * enable to get num_method value in ths class
     * @return num_method stored in this class
     */
    public int getNumMethod() { return num_method; }

    /**
     * enable to get num_loop value in ths class
     * @return num_loop stored in this class
     */
    public int getNumLoop() { return num_loop; }

    /**
     * enable to get num_import value in ths class
     * @return num_import stored in this class
     */
    public int getNumImport() { return num_import; }

    /**
     * Combine metric values using operator and operand lists in a new visitor
     * @param visitor a visitor containing calculation results by parsing another codes.
     */
    public void addByVisitor(ASTVisitorSearch visitor){
        HashMap<String, Integer> mergedOperators = new HashMap<String,Integer>();
        HashMap<String, Integer> mergedOperands = new HashMap<String,Integer>();

        for(Map.Entry<String, Integer> entry: operators.entrySet())
            mergedOperators.put(entry.getKey(), entry.getValue());
        for(Map.Entry<String, Integer> entry: operands.entrySet())
            mergedOperands.put(entry.getKey(), entry.getValue());

        visitor.oprt.forEach((k, v) -> mergedOperators.merge(k, v, Integer::sum));
        visitor.names.forEach((k, v) -> mergedOperands.merge(k, v, Integer::sum));

        operators = mergedOperators;
        operands = mergedOperands;

        codeLen += visitor.codeLen;
        commentLen += visitor.commentLen;
        cyclomaticComplexity += visitor.cycloComplexity;

        //Do the same things in setByVisitor with updated operators, operand, codeLen, commentLen
        dist_operators = operators.size();
        dist_operands = operands.size();
        total_operators = operators.values().stream().reduce(0, Integer::sum);
        total_operands = operands.values().stream().reduce(0, Integer::sum);

        HalsteadMetric halsteadMetric = new HalsteadMetric(dist_operators, dist_operands, total_operators, total_operands);

        halSteadVocabulary = halsteadMetric.getVocabulary();
        halSteadProgLength = halsteadMetric.getProglen();
        halSteadCalProgLength = halsteadMetric.getCalcProgLen();
        halSteadVolume = halsteadMetric.getVolume();
        halSteadDifficulty = halsteadMetric.getDifficulty();
        halSteadEffort = halsteadMetric.getEffort();
        halSteadTimeRequired = halsteadMetric.getTimeReqProg();
        halSteadNumDelBugs = halsteadMetric.getTimeDelBugs();

        maintainabilityIndex = (codeLen == 0) ? 100 : (halSteadVolume == 0) ? Math.max(0, 100 * (171.0 - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/(codeLen+1)), 0.5))) / 171.0) :
                Math.max(0, 100 * (171.0 - 5.2 * Math.log(halSteadVolume) - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/(codeLen+1)), 0.5))) / 171.0);

        num_method += visitor.num_method;
        num_loop += visitor.num_loop;
        num_import += visitor.num_import;

        return;
    }

    /**
     * update variables in this class by combining two metric calculation results
     * @param cInfo CommitInfo class which we want to combine with original values in this class
     */
    public void addByCommitInfo(CommitInfo cInfo){
        HashMap<String, Integer> mergedOperators = new HashMap<String,Integer>();
        HashMap<String, Integer> mergedOperands = new HashMap<String,Integer>();

        for(Map.Entry<String, Integer> entry: operators.entrySet())
            mergedOperators.put(entry.getKey(), entry.getValue());
        for(Map.Entry<String, Integer> entry: operands.entrySet())
            mergedOperands.put(entry.getKey(), entry.getValue());

        cInfo.getOperators().forEach((k, v) -> mergedOperators.merge(k, v, Integer::sum));
        cInfo.getOperands().forEach((k, v) -> mergedOperands.merge(k, v, Integer::sum));

        operators = mergedOperators;
        operands = mergedOperands;

        dist_operators = operators.size();
        dist_operands = operands.size();
        total_operators = operators.values().stream().reduce(0, Integer::sum);
        total_operands = operands.values().stream().reduce(0, Integer::sum);

        HalsteadMetric halsteadMetric = new HalsteadMetric(dist_operators, dist_operands, total_operators, total_operands);

        halSteadVocabulary = halsteadMetric.getVocabulary();
        halSteadProgLength = halsteadMetric.getProglen();
        halSteadCalProgLength = halsteadMetric.getCalcProgLen();
        halSteadVolume = halsteadMetric.getVolume();
        halSteadDifficulty = halsteadMetric.getDifficulty();
        halSteadEffort = halsteadMetric.getEffort();
        halSteadTimeRequired = halsteadMetric.getTimeReqProg();
        halSteadNumDelBugs = halsteadMetric.getTimeDelBugs();

        codeLen += cInfo.getCodeLen();
        commentLen += cInfo.getCommentLen();
        cyclomaticComplexity += cInfo.getCyclomaticComplexity();

        maintainabilityIndex = (codeLen == 0) ? 100 : (halSteadVolume == 0) ? Math.max(0, 100 * (171.0 - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/(codeLen+1)), 0.5))) / 171.0) :
                Math.max(0, 100 * (171.0 - 5.2 * Math.log(halSteadVolume) - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/(codeLen+1)), 0.5))) / 171.0);



        num_method += cInfo.getNumMethod();
        num_loop += cInfo.getNumLoop();
        num_import += cInfo.getNumImport();

        return;

    }

    /**
     * update ChurnResult's attributes using a new ChurnResult
     * @param c a ChurnResult we want to add to this class's churn
     */
    public void addChurn(ChurnResult c){
        churn = churn.clone();
        churn.setLinesAdded(churn.getLinesAdded() + c.getLinesAdded());
        churn.setLinesDeleted(churn.getLinesDeleted() + c.getLinesDeleted());

    }

    /**
     * Fill attributes in CommitInfo using values in this class
     * @param cInfo a CommitInfo class which we want to fill with this class's values
     */
    public void setToCommitInfo(CommitInfo cInfo){
        cInfo.setHalProgVocab(halSteadVocabulary);
        cInfo.setHalProgLen(halSteadProgLength);
        cInfo.setHalCalProgLen(halSteadCalProgLength);
        cInfo.setHalVolume(halSteadVolume);
        cInfo.setHalDifficulty(halSteadDifficulty);
        cInfo.setHalEffort(halSteadEffort);
        cInfo.setHalTime(halSteadTimeRequired);
        cInfo.setHalNumBugs(halSteadNumDelBugs);
        cInfo.setCyclomaticComplexity(cyclomaticComplexity);
        cInfo.setMaintainabilityIndex(maintainabilityIndex);
        cInfo.setOperands(operands);
        cInfo.setOperators(operators);
        cInfo.setCodeLen(codeLen);
        cInfo.setCommentLen(commentLen);
        cInfo.setChurn(churn);
        cInfo.setNumMethod(num_method);
        cInfo.setNumLoop(num_loop);
        cInfo.setNumImport(num_import);

        return;
    }

    /**
     * Fill variables in this class by using attributes in CommitInfo
     * @param cInfo a CommitInfo class which we bring values from to fill this class's attribute
     */
    public void setByCommitInfo(CommitInfo cInfo){
        operands = cInfo.getOperands();
        operators = cInfo.getOperators();

        dist_operators = operators.size();
        dist_operands = operands.size();
        total_operators = operators.values().stream().reduce(0, Integer::sum);
        total_operands = operands.values().stream().reduce(0, Integer::sum);

        halSteadVocabulary = (int)cInfo.getHalProgVocab();
        halSteadProgLength = (int)cInfo.getHalProgLen();
        halSteadCalProgLength = cInfo.getHalCalProgLen();
        halSteadVolume = cInfo.getHalVolume();
        halSteadDifficulty = cInfo.getHalDifficulty();
        halSteadEffort = cInfo.getHalEffort();
        halSteadTimeRequired = cInfo.getHalTime();
        halSteadNumDelBugs = cInfo.getHalTime();

        cyclomaticComplexity = (int)cInfo.getCyclomaticComplexity();

        codeLen = cInfo.getCodeLen();
        commentLen = cInfo.getCommentLen();

        maintainabilityIndex = cInfo.getMaintainabilityIndex();

        churn = cInfo.getChurn();
        num_method = cInfo.getNumMethod();
        num_loop = cInfo.getNumLoop();
        num_import = cInfo.getNumImport();

        return;
    }
}
