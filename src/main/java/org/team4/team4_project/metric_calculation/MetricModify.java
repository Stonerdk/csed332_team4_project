package org.team4.team4_project.metric_calculation;

public class MetricModify {
    MetricInfo metricOrg;

    public void setOrgMetric(MetricInfo m){
        metricOrg = m;
        return;
    }

    public MetricInfo addToMetric(String addStr){
        return metricOrg;


    }

    public MetricInfo deleteFromMetric(String deleteStr){
        return metricOrg;
    }

}
