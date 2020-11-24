import java.util.HashMap;

public class MetricInfo {
    //operands, operators
    private HashMap<String, Integer> operands = new HashMap<String, Integer>();
    private HashMap<String, Integer> operators = new HashMap<String, Integer>();

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
    
}
