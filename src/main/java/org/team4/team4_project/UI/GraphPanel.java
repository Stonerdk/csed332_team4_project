package org.team4.team4_project.UI;

import org.team4.team4_project.history.HistoryData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphPanel extends JPanel {
    double graphMaxValue;
    double graphMinValue;
    int xCount;
    int nodeCount;
    int width = 500;
    int height = 400;
    ArrayList<HistoryData> historyList;
    String type;

    GraphPanel(ArrayList<HistoryData> historyList) {
        this.historyList = historyList;
        setPreferredSize(new Dimension(640, 480));
    }

    private void refresh() {
        nodeCount = Math.min(xCount, historyList.size());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0,0,getWidth(),getHeight());
        g.drawLine(100,500,100 + 100*historyList.size(),500);
        for(int cnt = 1; cnt < 11; cnt++)
        {
            g.drawString(cnt *10 +"",50,510-40*cnt);
            g.drawLine(100, 500-40*cnt, 100 + 100*historyList.size(),500-40*cnt);
        }
        g.drawLine(100,40,100,500);

        int i = 1;
        int postX = 100;
        int postY = 500;
        for(HistoryData h : historyList){
            g.setColor(Color.BLACK);
            g.drawString(Long.toString(h.getDate().getTime()), 100 * i + 15, 540);
            g.setColor(Color.BLUE);
            int value = Integer.parseInt(String.valueOf(Math.round(getValue(h, type))));
            g.drawLine(postX, postY, 100*i + 45,500 -value*4);
            postX = 100*i + 45;
            postY = 500 -value*4;
            i++;
        }
    }

    private void paintDefaultGraph(Graphics g) {
        /*int nodeCnt = Math.min(historyList.size(), xCount);
        int maxValue = 0;
        ArrayList<Double> valueList = new ArrayList<>();
        for (HistoryData h : historyList) {
            double value = h.getAttribute(type);
            valueList.add(value);
            maxValue = Math.max(maxValue, (int)value);
        }
        maxValue = (int)(maxValue * 1.2);
        int j = 0;
        for(int i = historyList.size() - 1; i >= nodeCnt; i--) {
            int value = Integer.parseInt(String.valueOf(Math.round(valueList.get(i))));
            int x = width - j * (width / xCount);
        }*/
    }

    private double getValue(HistoryData h, String type) {
        /*
        * this.halSteadVocabulary = metricInfo.getHalsteadVocabulary();
        this.halSteadProgLength = metricInfo.getHalsteadProgLength();
        this.halSteadCalProgLength = metricInfo.getHalsteadCalProgLength();
        this.halSteadVolume = metricInfo.getHalsteadVolume();
        this.halSteadDifficulty = metricInfo.getHalsteadDifficulty();
        this.halSteadEffort = metricInfo.getHalsteadEffort();
        this.halSteadTimeRequired = metricInfo.getHalsteadTimeRequired();
        this.halSteadNumDelBugs = metricInfo.getHalsteadNumDelBugs();
        this.cyclomaticComplexity = metricInfo.getCyclomaticComplexity();
        this.maintainabilityIndex = metricInfo.getMaintainabilityIndex();
        * */
        switch(type) {
            case "Halstead Vocabulary": return h.getHalsteadVocabulary();
            case "Halstead Program Length": return h.getHalsteadProgLength();
            case "Halstead Cal Prog Length": return h.getHalsteadCalProgLength();
            case "Halstead Volume": return h.getHalsteadVolume();
            case "Halstead Difficulty": return h.getHalsteadDifficulty();
            case "Halstead Effort": return h.getHalsteadEffort();
            case "Halstead Time Required": return h.getHalsteadTimeRequired();
            case "Halstead Num Del Bugs": return h.getHalsteadNumDelBugs();
            case "Cyclomatic Complexity": return h.getCyclomaticComplexity();
            case "Maintainability" : return h.getMaintainablityIndex();
            case "Code Churn" : return 0; // Implement code churn own way without using getValue
        }
        System.out.println("Wrong type name : " + type);
        return 0;
    }

    private void paintCodeChurn(Graphics g) {

    }

    void setType(String type) {
        this.type = type;
    }
}

