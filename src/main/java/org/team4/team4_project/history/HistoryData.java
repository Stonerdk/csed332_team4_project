package org.team4.team4_project.history;

import org.team4.team4_project.git.ChurnResult;
import org.team4.team4_project.metric_calculation.MetricInfo;

import javax.annotation.Nullable;
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

    private final double halSteadVocabulary;
    private final double halSteadProgLength;
    private final double halSteadCalProgLength;
    private final double halSteadVolume;
    private final double halSteadDifficulty;
    private final double halSteadEffort;
    private final double halSteadTimeRequired;
    private final double halSteadNumDelBugs;
    private final double cyclomaticComplexity;
    private final double maintainabilityIndex;
    private final double codeChurnAdded;
    private final double codeChurnDeleted;

    @Deprecated
    public HistoryData(Date date, String commitString, String branchName, Map<String, Double> attr) {
        this.date = date;
        this.commitString = commitString;
        this.branchName = branchName;
        this.attr = Collections.unmodifiableMap(attr);
        this.halSteadVocabulary = 0;
        this.halSteadProgLength = 0;
        this.halSteadCalProgLength = 0;
        this.halSteadVolume = 0;
        this.halSteadDifficulty = 0;
        this.halSteadEffort = 0;
        this.halSteadTimeRequired = 0;
        this.halSteadNumDelBugs = 0;
        this.cyclomaticComplexity = 0;
        this.maintainabilityIndex = 0;
        this.codeChurnAdded = 0;
        this.codeChurnDeleted = 0;
    }

    public HistoryData(Date date, String commitString, String branchName, MetricInfo metricInfo, @Nullable ChurnResult churnResult) {
        this.date = date;
        this.commitString = commitString;
        this.branchName = branchName;
        this.attr = new HashMap<>();
        this.halSteadVocabulary = metricInfo.getHalsteadVocabulary();
        this.halSteadProgLength = metricInfo.getHalsteadProgLength();
        this.halSteadCalProgLength = metricInfo.getHalsteadCalProgLength();
        this.halSteadVolume = metricInfo.getHalsteadVolume();
        this.halSteadDifficulty = metricInfo.getHalsteadDifficulty();
        this.halSteadEffort = metricInfo.getHalsteadEffort();
        this.halSteadTimeRequired = metricInfo.getHalsteadTimeRequired();
        this.halSteadNumDelBugs = metricInfo.getHalsteadNumDelBugs();
        this.cyclomaticComplexity = metricInfo.getCyclomaticComplexity();
        this.maintainabilityIndex = metricInfo.getMaintainabilityIndex();
        if (churnResult == null) {
            this.codeChurnAdded = 0;
            this.codeChurnDeleted = 0;
        } else {
            this.codeChurnAdded = churnResult.getLinesAdded();
            this.codeChurnDeleted = churnResult.getLinesDeleted();
        }
    }

    @Deprecated
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

    public Date getDate() {
        return date;
    }

    public String getCommitString() {
        return commitString;
    }

    public String getBranchName() {
        return branchName;
    }

    @Deprecated
    public Double getAttribute(String key) {
        return attr.getOrDefault(key, 0.0);
    }

    public double getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public double getHalsteadVolume() {
        return halSteadVolume;
    }

    public double getHalsteadVocabulary() {
        return halSteadVocabulary;
    }

    public double getHalsteadProgLength() {
        return halSteadProgLength;
    }

    public double getHalsteadCalProgLength() {
        return halSteadCalProgLength;
    }

    public double getHalsteadDifficulty() {
        return halSteadDifficulty;
    }
    public double getHalsteadEffort() {
        return halSteadEffort;
    }
    public double getHalsteadTimeRequired() {
        return halSteadTimeRequired;
    }

    public double getHalsteadNumDelBugs() {
        return halSteadNumDelBugs;
    }
    public double getMaintainablityIndex() {
        return maintainabilityIndex;
    }
    public double getCodeChurnPlus() {
        return codeChurnAdded;
    }
    public double getCodeChurnMinus() {
        return codeChurnDeleted;
    }



}
