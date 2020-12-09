package org.team4.team4_project.metric_calculation;

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

        maintainabilityIndex = Math.max(0, 100 * (171.0 - 5.2 * Math.log(halSteadVolume) - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/(codeLen+1)), 0.5))) / 171.0);

        return;
    }

    public int getDistOperands(){
        return dist_operands;
    }
    public int getDistOperators(){
        return dist_operators;
    }
    public int getTotalOperands(){
        return total_operands;
    }
    public int getTotalOperators(){
        return total_operators;
    }

    public int getHalsteadVocabulary(){
        return halSteadVocabulary;
    }
    public int getHalsteadProgLength(){
        return halSteadProgLength;
    }
    public double getHalsteadCalProgLength(){
        return halSteadCalProgLength;
    }
    public double getHalsteadVolume(){
        return halSteadVolume;
    }
    public double getHalsteadDifficulty(){
        return halSteadDifficulty;
    }
    public double getHalsteadEffort(){
        return halSteadEffort;
    }
    public double getHalsteadTimeRequired(){
        return halSteadTimeRequired;
    }
    public double getHalsteadNumDelBugs(){
        return halSteadNumDelBugs;
    }

    public int getCyclomaticComplexity(){
        return cyclomaticComplexity;
    }

    public int getCodeLen(){
        return codeLen;
    }
    public int getCommentLen(){
        return commentLen;
    }

    public double getMaintainabilityIndex(){
        return maintainabilityIndex;
    }

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

        maintainabilityIndex = Math.max(0, 100 * (171.0 - 5.2 * Math.log(halSteadVolume) - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/(codeLen+1)), 0.5))) / 171.0);

        return;
    }

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

        return;
    }

    public void addByString(String str){
        halSteadVolume +=10;
        cyclomaticComplexity += 20;
        maintainabilityIndex += 30;
        return;
    }

    public void setByCommitInfo(CommitInfo cInfo){
        operands = cInfo.getOperands();
        operators = cInfo.getOperators();

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

        cyclomaticComplexity = (int)cInfo.getCyclomaticComplexity();

        codeLen = cInfo.getCodeLen();
        commentLen = cInfo.getCommentLen();

        maintainabilityIndex = Math.max(0, 100 * (171.0 - 5.2 * Math.log(halSteadVolume) - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/(codeLen+1)), 0.5))) / 171.0);

        return;

    }
}
