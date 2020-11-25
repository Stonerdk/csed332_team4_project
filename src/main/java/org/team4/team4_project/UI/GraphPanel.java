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
            g.drawLine(postX, postY, 100*i + 45,500 -Integer.parseInt(String.valueOf(Math.round(h.getAttribute(type))))*4);
            postX = 100*i + 45;
            postY = 500 -Integer.parseInt(String.valueOf(Math.round(h.getAttribute(type))))*4;
            i++;
        }

    }

    void setType(String variable) {
        this.type = variable;
    }
}

