package org.team4.team4_project.metric_calculation;


/**
 * A Class used to calculate halstead metric using operator, operands information
 * @author Tahyeong, Chaeyoon
 */
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

    /**
     * A constructor calculate and set Halstead Metrics based on number of distinct operators, distinct operands, total operators, total operands
     * @param DistOprt vocabulary of the HalsteadMetric
     * @param DistOper vocabulary of the HalsteadMetric
     * @param TotOprt vocabulary of the HalsteadMetric
     * @param TotOper vocabulary of the HalsteadMetric\
     */
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
    /**
     * A method returns vocabulary of this HalsteadMetric
     * @return vocabulary of the HalsteadMetric
     */
    public int getVocabulary(){
        return Vocabulary;
    }

    /**
     * A method returns program length of this HalsteadMetric
     * @return program length of the HalsteadMetric
     */
    public int getProglen(){
        return Proglen;
    }

    /**
     * A method returns calculated program length of this HalsteadMetric
     * @return calculated program length of this HalsteadMetric
     */
    public double getCalcProgLen(){
        return CalcProgLen;
    }

    /**
     * A method returns volume of this HalsteadMetric
     * @return volume of the HalsteadMetric
     */
    public double getVolume(){
        return Volume;
    }

    /**
     * A method returns difficulty of this HalsteadMetric
     * @return difficulty of the HalsteadMetric
     */
    public double getDifficulty(){
        return Difficulty;
    }

    /**
     * A method returns effort of this HalsteadMetric
     * @return effort of the HalsteadMetric
     */
    public double getEffort(){
        return Effort;
    }

    /**
     * A method returns time required to program of this HalsteadMetric
     * @return time required to program of the HalsteadMetric
     */
    public double getTimeReqProg(){
        return TimeReqProg;
    }

    /**
     * A method returns number of delivered bugs of this HalsteadMetric
     * @return number of delivered bugs of the HalsteadMetric
     */
    public double getTimeDelBugs(){
        return TimeDelBugs;
    }
}
