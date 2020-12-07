package org.team4.team4_project.swmetric;

import static org.junit.Assert.*;

import org.junit.Test;
import org.team4.team4_project.metric_calculation.HalsteadMetric;

public class HalsteadCalculationTest {
    @Test
    public void testgetVocabulary() {
        HalsteadMetric halsteadMetric = new HalsteadMetric(10, 7, 16, 15);
        assertEquals(17, halsteadMetric.getVocabulary());
    }
    @Test
    public void testgetProglen() {
        HalsteadMetric halsteadMetric = new HalsteadMetric(10, 7, 16, 15);
        assertEquals(31, halsteadMetric.getProglen());
    }
    @Test
    public void testgetVolume() {
        HalsteadMetric halsteadMetric = new HalsteadMetric(10, 7, 16, 15);
        assertEquals(127, Math.round(halsteadMetric.getVolume()));
    }
    @Test
    public void testgetDifficulty() {
        HalsteadMetric halsteadMetric = new HalsteadMetric(10, 7, 16, 15);
        assertEquals(11, Math.round(halsteadMetric.getDifficulty()));
    }
    @Test
    public void testgetEffort() {
        HalsteadMetric halsteadMetric = new HalsteadMetric(10, 7, 16, 15);
        assertEquals(1358, Math.round(halsteadMetric.getEffort()));
    }
    @Test
    public void testgetTimeReqProg() {
        HalsteadMetric halsteadMetric = new HalsteadMetric(10, 7, 16, 15);
        assertEquals(75, Math.round(halsteadMetric.getTimeReqProg()));
    }
    @Test
    public void testgetTimeDelBugs() {
        HalsteadMetric halsteadMetric = new HalsteadMetric(10, 7, 16, 15);
        assertEquals(0, Math.round(halsteadMetric.getTimeDelBugs()));
    }
}