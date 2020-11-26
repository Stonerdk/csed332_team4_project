package org.team4.team4_project.UI;

import com.intellij.ui.JBColor;
import org.team4.team4_project.history.HistoryData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GraphPanel extends JPanel implements MouseMotionListener, MouseListener {
    private int xCount = 10;
    private int nodeCnt = 0;
    private int maxValue = 10;
    private int width = 500;
    private int height = 400;
    private int xOffset = 100;
    private int yOffset = 100;
    private int delay = 20;
    private int mouseX, mouseY;
    private int yPartition = 8;
    private int hoverJ = -1;
    private HistoryData hoverHistory = null;
    MetricsWindow parentFrame;
    ArrayList<HistoryData> historyList;
    String type;

    GraphPanel(ArrayList<HistoryData> historyList, MetricsWindow parentFrame) {
        this.historyList = historyList;
        setPreferredSize(new Dimension(640, 480));
        this.parentFrame = parentFrame;
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0,0,getWidth(),getHeight());
        paintDefaultGraph(g);
//        for(int cnt = 1; cnt < 11; cnt++)
//        {
//            g.drawString(cnt *10 +"",50,510-40*cnt);
//            g.drawLine(100, 500-40*cnt, 100 + 100*historyList.size(),500-40*cnt);
//        }
//        g.drawLine(100,40,100,500);
//
//        int i = 1;
//        int postX = 100;
//        int postY = 500;
//        for(HistoryData h : historyList){
//            g.setColor(JBColor.BLACK);
//            g.drawString(Long.toString(h.getDate().getTime()), 100 * i + 15, 540);
//            g.setColor(JBColor.BLUE);
//            int value = Integer.parseInt(String.valueOf(Math.round(getValue(h, type))));
//            g.drawLine(postX, postY, 100*i + 45,500 -value*4);
//            postX = 100*i + 45;
//            postY = 500 -value*4;
//            i++;
//        }
    }

    private void paintDefaultGraph(Graphics g) {
        nodeCnt = Math.min(historyList.size(), xCount);
        maxValue = 0;
        for (HistoryData h : historyList) {
            maxValue = Math.max(maxValue, (int)getValue(h, type));
        }
        for(int i = 0; i < 8; i++) {
            int y = yOffset + height - height * i / 8;
            g.setColor(JBColor.BLACK);
            g.drawString(Integer.toString(maxValue * i / 8), xOffset - 60, y - 8);
            if (i != 0)
                g.setColor(JBColor.GRAY);
            g.drawLine(xOffset, y, xOffset + width, y);
        }
        g.drawLine(xOffset, yOffset, xOffset, yOffset + height);
        maxValue = Math.max(5, (int)(maxValue * 1.2));
        int j = 0, postX = 0, postY = 0;
        for(int i = historyList.size() - 1; i >= nodeCnt; i--) {
            int value = Integer.parseInt(String.valueOf(Math.round(getValue(historyList.get(i), type))));
            int x = width - j * (width / xCount);
            int y = height - height * value / maxValue;
            int radius = (hoverJ == j) ? 10 : 5;
            g.fillOval(xOffset + x - radius, yOffset + y - radius, radius * 2, radius * 2);
            if (j != 0)
                g.drawLine(xOffset + x, yOffset + y, xOffset + postX, yOffset + postY);
            postX = x;
            postY = y;
            j++;
        }
    }

    private double getValue(HistoryData h, String type) {
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        System.out.println(e.getX() + ", " + e.getY());
        int j = 0;
        hoverJ = -1;
        hoverHistory = null;

        for(int i = historyList.size() - 1; i >= nodeCnt; i--) {
            int value = Integer.parseInt(String.valueOf(Math.round(getValue(historyList.get(i), type))));
            int x = width - j * (width / xCount);
            int y = height - height * value / maxValue;
            Rectangle rect = new Rectangle(xOffset + x - 10, yOffset + y - 10, 20, 20);
            if (rect.contains(mouseX, mouseY)) {
                hoverJ = j;
                hoverHistory = historyList.get(i);
            }
            j++;
        } //TODO : optimize this (without using for loop)
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (hoverHistory != null)
            parentFrame.setStatusHistory(hoverHistory);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

