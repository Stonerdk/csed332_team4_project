package org.team4.team4_project.git;

import java.util.ArrayList;
import java.util.List;


/**
 * Store code and code churn for specific commit.
 * @author Eojin Kim
 */
public class ChurnResult implements Cloneable{
    private int linesAdded = 0;
    private int linesDeleted = 0;
    private String code = "";
    private List<String> codelist = new ArrayList<String>();

    /**
     * Returns added code length from previous commit.
     * @return int value for added code length.
     */
    public int getLinesAdded() {
        return linesAdded;
    }

    /**
     * Returns deleted code length from previous commit.
     * @return int value for deleted code length.
     */
    public int getLinesDeleted() {
        return linesDeleted;
    }

    /**
     * plus 1 to added code length from previous commit.
     */
    public void plusLinesAdded(){
        linesAdded++;
    }


    /**
     * plus 1 to deleted code length from previous commit.
     */
    public void plusLinesDeleted(){
        linesDeleted++;
    }


    /**
     * Returns code when committing.
     * @return String value for code.
     */
    public String getcode() { return code;}

    /**
     * Set the code by param c.
     * @param c String value for code.
     */
    public void setcode(String c) {code = c;}

    /**
     * Returns codelist when committing.
     * codelist store the code in line.
     * @return List<String> value for codelist.
     */
    public List<String> getcodelist() { return codelist;}

    /**
     * Set the codelist by param s.
     * @param s List<String> value for codelist.
     */
    public void setcodelist(List<String> s) { codelist = s;}

    /**
     * Set the added code length from previous commit by param n.
     * @param n List<String> value for codelist.
     */
    public void setLinesAdded(int n){linesAdded = n;}

    /**
     * Set the deleted code length from previous commit by param n.
     * @param n List<String> value for codelist.
     */
    public void setLinesDeleted(int n){linesDeleted = n;}


    /**
     * A method used to do "deep copy" ChurnResult class
     * @return new ChurnResult object with same contents
     */
    public ChurnResult clone() {
        ChurnResult obj = null;
        try {
            obj = (ChurnResult) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }



}
