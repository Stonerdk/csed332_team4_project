package org.team4.team4_project.swmetric;

import static org.junit.Assert.*;

import org.junit.Test;
import org.team4.team4_project.metric_calculation.CalHalstead;

public class HalsteadCalculationTest {
    @Test
    public void testgetVocabulary() {
        CalHalstead calHalstead = new CalHalstead();
        calHalstead.setParameters(10, 7, 16, 15);
        assertEquals(17, calHalstead.getVocabulary());
    }

    @Test
    public void testgetProglen() {
        CalHalstead calHalstead = new CalHalstead();
        calHalstead.setParameters(10, 7, 16, 15);
        assertEquals(31, calHalstead.getProglen());
    }

    @Test
    public void testgetVolume() {
        CalHalstead calHalstead = new CalHalstead();
        calHalstead.setParameters(10, 7, 16, 15);
        System.out.println("getVolume= "+ (double)Math.round(calHalstead.getVolume()));
        assertEquals(127, Math.round(calHalstead.getVolume()));
    }


    @Test
    public void testgetDifficulty() {
        CalHalstead calHalstead = new CalHalstead();
        calHalstead.setParameters(10, 7, 16, 15);
        System.out.println("getDifficulty= "+ (double)Math.round(calHalstead.getDifficulty()));
        assertEquals(11, Math.round(calHalstead.getDifficulty()));
    }


    @Test
    public void testgetEffort() {
        CalHalstead calHalstead = new CalHalstead();
        calHalstead.setParameters(10, 7, 16, 15);
        System.out.println("getEffort= "+ (double)Math.round(calHalstead.getEffort()));
        assertEquals(1358, Math.round(calHalstead.getEffort()));
    }



    @Test
    public void testgetTimeReqProg() {
        CalHalstead calHalstead = new CalHalstead();
        calHalstead.setParameters(10, 7, 16, 15);
        System.out.println("getTimeReqProg= "+ (double)Math.round(calHalstead.getTimeReqProg()));
        assertEquals(75, Math.round(calHalstead.getTimeReqProg()));
    }


    @Test
    public void testgetTimeDelBugs() {
        CalHalstead calHalstead = new CalHalstead();
        calHalstead.setParameters(10, 7, 16, 15);
        System.out.println("getTimeDelBugs= "+ (double)Math.round(calHalstead.getTimeDelBugs()));
        assertEquals(0, Math.round(calHalstead.getTimeDelBugs()));
    }
}