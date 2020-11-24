import java.util.HashMap;

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
    private double maintainabilityIndex;


    public void setByVisitor(ASTVisitorSearch visitor) {
        operands = visitor.names;
        operators = visitor.oprt;

        dist_operators = operators.size();
        dist_operands = operands.size();
        total_operators = operators.values().stream().reduce(0, Integer::sum);
        total_operands = operands.values().stream().reduce(0, Integer::sum);

        CalHalstead calHalstead = new CalHalstead();
        calHalstead.setParameters(dist_operators, dist_operands, total_operators, total_operands);

        halSteadVocabulary = calHalstead.getVocabulary();
        halSteadProgLength = calHalstead.getProglen();
        halSteadCalProgLength = calHalstead.getCalcProgLen();
        halSteadVolume = calHalstead.getVolume();
        halSteadDifficulty = calHalstead.getDifficulty();
        halSteadEffort = calHalstead.getEffort();
        halSteadTimeRequired = calHalstead.getTimeReqProg();
        halSteadNumDelBugs = calHalstead.getTimeDelBugs();


        cyclomaticComplexity = visitor.cycloComplexity;

        codeLen = visitor.codeLen;
        commentLen = visitor.commentLen;

        maintainabilityIndex = Math.max(0, 100 * (171.0 - 5.2 * Math.log(halSteadVolume) - 0.23 * cyclomaticComplexity - 16.2 * Math.log(codeLen) + 50.0 * Math.sin(Math.pow(2.4 * Math.toRadians(commentLen/codeLen), 0.5))) / 171.0);

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
}
