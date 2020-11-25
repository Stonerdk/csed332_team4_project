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
            int value = Integer.parseInt(String.valueOf(Math.round(h.getAttribute(type))));
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


    private void paintCodeChurn(Graphics g) {

    }

    void setType(String type) {
        this.type = type;
    }
}

