package org.team4.team4_project.history;

import org.team4.team4_project.metric_calculation.MetricInfo;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HistoryData {
    private final Date date;
    private final String commitString;
    private final String branchName;
    private final Map<String, Double> attr;
    //private final MetricInfo metricInfo;
    private static final String[] attrKeys = new String[] {"Halstead Complexity", "Cyclomatic Complexity", "Maintainability"};

    public HistoryData(Date date, String commitString, String branchName, Map<String, Double> attr) {
        this.date = date;
        this.commitString = commitString;
        this.branchName = branchName;
        this.attr = Collections.unmodifiableMap(attr);
    }

    public static Map<String,Double> makeAttrMap(double... value) {
        HashMap<String, Double> ret = new HashMap<>();
        for(int i = 0; i < attrKeys.length; i++) {
            try {
                ret.put(attrKeys[i], value[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                ret.put(attrKeys[i], 0.0);
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static Map<String, Double> makeAttrMap(MetricInfo metricInfo) {
        HashMap<String, Double> ret = new HashMap<>();
        ret.put(attrKeys[0], (double)metricInfo.getCyclomaticComplexity());
        ret.put(attrKeys[1], (double)metricInfo.getHalsteadVolume());
        ret.put(attrKeys[2], metricInfo.getMaintainabilityIndex());
        return ret;
    }

    public Date getDate() {
        return date;
    }

    public String getCommitString() {
        return commitString;
    }

    public String getBranchName() {
        return branchName;
    }

    //TODO : implement an easier getter that client does not need to know whole name of attributes
    public Double getAttribute(String key) {
        return attr.getOrDefault(key, 0.0);
    }

    public double getCyclomaticComplexity() {
        //TODO
        return attr.getOrDefault("Cyclomatic Complexity", 0.0);
    }

    public double getHalsteadVolume() {
        //TODO
        return attr.getOrDefault("Halstead Complexity", 0.0);
    }
    /*
    private int halSteadVocabulary=0;
    private int halSteadProgLength=0;
    private double halSteadCalProgLength=0;
    private double halSteadVolume=0;
    private double halSteadDifficulty=0;
    private double halSteadEffort=0;
    private double halSteadTimeRequired=0;
    private double halSteadNumDelBugs=0;
    */

    public int getHalsteadVocabulary() {
        //TODO
        return 0;
    }

    public int getHalsteadProgLength() {
        //TODO
        return 0;
    }

    public double getHalsteadCalProgLength() {
        //TODO
        return 0;
    }
    public double getHalsteadDifficulty() {
        //TODO
        return 0;
    }



}
