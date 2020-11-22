package org.team4.team4_project.git;

public class ChurnResult {

    private int linesAdded = 0;
    private int linesDeleted = 0;

    public int getTotalChurn() {
        return getLinesAdded() + getLinesDeleted();
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesDeleted() {
        return linesDeleted;
    }

    public void plusLinesAdded(){
        linesAdded++;
    }

    public void plusLinesDeleted(){
        linesDeleted++;
    }

}
