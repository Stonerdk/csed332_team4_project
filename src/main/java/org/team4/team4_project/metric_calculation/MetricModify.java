package org.team4.team4_project.metric_calculation;

public class MetricModify {
    MetricStorage metricOrg;

    public void setOrgMetric(MetricStorage m){
        metricOrg = m;
        return;
    }

    public MetricStorage addToMetric(String addStr){
        return metricOrg;


    }

    public MetricStorage deleteFromMetric(String deleteStr){
        return metricOrg;
    }

}
