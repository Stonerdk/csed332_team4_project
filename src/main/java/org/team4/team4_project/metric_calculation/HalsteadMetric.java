package org.team4.team4_project.metric_calculation;

public class HalsteadMetric {
    private int DistOperators, DistOperands, TotOperators, TotOperands;
    private int Vocabulary=0;
    private int Proglen=0;
    private double CalcProgLen=0;
    private double Volume=0;
    private double Difficulty=0;
    private double Effort=0;
    private double TimeReqProg=0;
    private double TimeDelBugs=0;

    public HalsteadMetric(int DistOprt, int DistOper, int TotOprt, int TotOper) {
        DistOperators=DistOprt;
        DistOperands=DistOper;
        TotOperators=TotOprt;
        TotOperands=TotOper;

        Vocabulary=DistOperators+DistOperands;
        Proglen=TotOperators+TotOperands;
        CalcProgLen = (DistOperators == 0 || DistOperands == 0) ? 0 : DistOperators*(Math.log(DistOperators) / Math.log(2)) + DistOperands*(Math.log(DistOperands) / Math.log(2));
        Volume= (DistOperators == 0 || DistOperands == 0) ? 0 : (TotOperators+TotOperands)*(Math.log(DistOperators+DistOperands)/Math.log(2));
        Difficulty= (DistOperators == 0 || DistOperands == 0) ? 0 : (DistOperators/2)*(TotOperands/(double)DistOperands);
        Effort= (DistOperators == 0 || DistOperands == 0) ? 0 : ((DistOperators/2)*(TotOperands/(double)DistOperands)) * ((TotOperators+TotOperands)*(Math.log(DistOperators+DistOperands)/Math.log(2)));
        TimeReqProg= (DistOperators == 0 || DistOperands == 0) ? 0 : (((DistOperators/2)*(TotOperands/(double)DistOperands)) * ((TotOperators+TotOperands)*(Math.log(DistOperators+DistOperands)/Math.log(2)))) /18;
        TimeDelBugs = (DistOperators == 0 || DistOperands == 0) ? 0 : ((TotOperators+TotOperands)*(Math.log(DistOperators+DistOperands)/Math.log(2))) / 3000;
    }
    public int getVocabulary(){
        return Vocabulary;
    }
    public int getProglen(){
        return Proglen;
    }
    public double getCalcProgLen(){
        return CalcProgLen;
    }
    public double getVolume(){
        return Volume;
    }
    public double getDifficulty(){
        return Difficulty;
    }
    public double getEffort(){
        return Effort;
    }
    public double getTimeReqProg(){
        return TimeReqProg;
    }
    public double getTimeDelBugs(){
        return TimeDelBugs;
    }
}
