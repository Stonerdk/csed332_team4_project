package org.team4.team4_project.UI;

import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Date;

public class GraphPanel extends JPanel implements MouseMotionListener, MouseListener, ComponentListener {
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
    private int zoom = 80;
    //private CommitInfo hoverHistory = null;
    MetricsWindow parentFrame;
    String type;

    List<Double> valueList;
    int commitSize;
    GUIController guiC;
    int hoverIndex;

    GraphPanel(MetricsWindow parentFrame) {
        guiC = GUIController.getInstance();
        commitSize = guiC.getSize();

        setPreferredSize(new Dimension(commitSize * 80 + 200, 480));
        this.parentFrame = parentFrame;
        addMouseMotionListener(this);
        addMouseListener(this);
        addComponentListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2d);
        g2d.setColor(JBColor.white);
        g2d.fillRect(0,0,getWidth(),getHeight());
        paintDefaultGraph(g2d);
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

    private void paintDefaultGraph(Graphics2D g) {
        //nodeCnt = Math.min(historyList.size(), xCount);
        nodeCnt = commitSize;
        //setSize(Math.min(800, nodeCnt * 80), 480);
        maxValue = 0;


        valueList = guiC.getValueList(type);
        for (double d : valueList) {
            maxValue = Math.max(maxValue, (int)Math.round(d));
        }

        maxValue = Math.max(5, (int)(maxValue * 1.2));
        for(int i = 0; i < 8; i++) {
            int y = yOffset + height - height * i / 8;
            g.setColor(JBColor.BLACK);
            g.drawString(Integer.toString(maxValue * i / 8), xOffset - 60, y);
            g.drawString(Integer.toString(maxValue * i / 8), xOffset + (nodeCnt-1) * zoom + 80 + 30, y);
            if (i != 0)
                g.setColor(JBColor.GRAY);
            g.drawLine(xOffset, y, xOffset + (nodeCnt-1) * zoom + 80, y);
        }
        g.drawLine(xOffset, yOffset, xOffset, yOffset + height);
        g.drawLine(xOffset + (nodeCnt-1) * zoom + 80, yOffset, xOffset + (nodeCnt-1) * zoom + 80, yOffset + height);

        int j = 0, postX = 0, postY = 0;
        for(int i = nodeCnt - 1; i >= 0; i--) {
            int value = Integer.parseInt(String.valueOf(Math.round(valueList.get(i))));
            //int x = width - j * (width / xCount);
            int x = i * zoom + 40;
            int y = height - height * value / maxValue;

            int radius = (hoverJ == j) ? 10 : 5;
            g.setColor(JBColor.ORANGE);
            g.setColor(JBColor.LIGHT_GRAY);
            g.drawLine(xOffset + x, yOffset + height, xOffset + x, yOffset + y);

            g.fillOval(xOffset + x - radius, yOffset + y - radius, radius * 2, radius * 2);
            if (j != 0)
                g.drawLine(xOffset + x, yOffset + y, xOffset + postX, yOffset + postY);

            g.setColor(JBColor.black);
            String dat = guiC.getCommitDate(i);
            g.drawString(dat, xOffset + x - 18, yOffset + height + 30);
            postX = x;
            postY = y;
            j++;
        }
    }

    /*private double getValue(HistoryData h, String type) {
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
    }*/

    private void paintCodeChurn(Graphics g) {

    }

    void setType(String type) {
        this.type = type;
    }

    void Zoom(int k){
        zoom = k;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        //  System.out.println(e.getX() + ", " + e.getY());
        int j = 0;
        hoverJ = -1;
        //hoverHistory = null;
        hoverIndex = -1;

        for(int i = nodeCnt - 1; i >= 0; i--) {
            int value = Integer.parseInt(String.valueOf(Math.round(valueList.get(i))));
            //int x = width - j * (width / xCount);
            int x = i * zoom + 20;
            int y = height - height * value / maxValue;
            Rectangle rect = new Rectangle(xOffset + x - 5, yOffset + y - 20, 40, 40);
            if (rect.contains(mouseX, mouseY)) {
                hoverJ = j;
                //hoverHistory = File.comInfoList.get(i);
                hoverIndex = i;
            }
            j++;
        } //TODO : optimize this (without using for loop)
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Mouse CLicked");
        if (hoverIndex != -1)
            parentFrame.setStatusHistory(hoverIndex);
        parentFrame.repaint();
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

    @Override
    public void componentResized(ComponentEvent e) {
        width = getWidth() - 2 * xOffset;
        //width = xOffset + 200 * nodeCnt;
        height = getHeight() - 2 * yOffset;
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        width = getWidth() - 2 * xOffset;
        //width = xOffset + 200 * nodeCnt;
        height = getHeight() - 2 * yOffset;
        repaint();
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}

