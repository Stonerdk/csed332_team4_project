package org.team4.team4_project.history;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HistoryData {
    private final Date date;
    private final String commitString;
    private final String branchName;
    private final Map<String, Double> attr;
    private static final String[] attrKeys = new String[] {"Halstead Complexity", "Cyclomatic Complexity", "Maintainability"};;

    public HistoryData(Date date, String commitString, String branchName, Map<String, Double> attr) {
        this.date = date;
        this.commitString = commitString;
        this.branchName = branchName;
        this.attr = Collections.unmodifiableMap(attr);
        //TODO : implement an easier constructor that client does not need to know whole name of attributes
        //TODO : Decide the attribute keys.

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
}
