package org.team4.team4_project.UI;

import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Date;

public class GraphPanel extends JPanel implements MouseMotionListener, MouseListener, ComponentListener {
    //private int xCount = 10;
    //private int nodeCnt = 0;
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

    GUIController guiC;
    int hoverIndex;

    GraphPanel(MetricsWindow parentFrame) {
        guiC = GUIController.getInstance();

        setPreferredSize(new Dimension(guiC.getSize() * 80 + 200, 480));
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
        if (!type.equals("Code Churn"))
            paintDefaultGraph(g2d);
        else
            paintChurnGraph(g2d);

    }

    private void paintDefaultGraph(Graphics2D g) {
        maxValue = 0;
        setPreferredSize(new Dimension(guiC.getSize() * zoom + 200, 480));

        for (double d : guiC.getValueList(type)) {
            maxValue = Math.max(maxValue, (int)Math.round(d));
        }

        maxValue = Math.max(5, (int)(maxValue * 1.2));
        int xRightOffset = xOffset + (guiC.getSize() - 1) * zoom + 80;
        for(int i = 0; i < 8; i++) {
            g.setColor(JBColor.BLACK);
            int y = yOffset + height - height * i / 8;
            g.drawString(Integer.toString(maxValue * i / 8), xOffset - 60, y);
            g.drawString(Integer.toString(maxValue * i / 8), xRightOffset + 30, y);
            if (i != 0)
                g.setColor(JBColor.GRAY);
            g.drawLine(xOffset, y, xRightOffset, y);
        }
        g.drawLine(xOffset, yOffset, xOffset, yOffset + height);
        g.drawLine(xRightOffset, yOffset, xRightOffset, yOffset + height);

        int postX = 0, postY = 0;
        for(int i = guiC.getSize() - 1; i >= 0; i--) {
            int value = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i))));

            int x = i * zoom + 40;
            int y = height - height * value / maxValue;

            int radius = (hoverJ == guiC.getSize() - 1 - i) ? 10 : 5;
            g.setColor(JBColor.ORANGE);
            g.setColor(JBColor.LIGHT_GRAY);
            g.drawLine(xOffset + x, yOffset + height, xOffset + x, yOffset + y);

            g.fillOval(xOffset + x - radius, yOffset + y - radius, radius * 2, radius * 2);
            if (i != guiC.getSize() - 1)
                g.drawLine(xOffset + x, yOffset + y, xOffset + postX, yOffset + postY);

            g.setColor(JBColor.black);
            String dat = guiC.getCommitDate(i);
            g.drawString(dat, xOffset + x - 18, yOffset + height + 30);
            postX = x;
            postY = y;

        }
    }

    private void paintChurnGraph(Graphics2D g) {
        maxValue = 0;
        setPreferredSize(new Dimension(guiC.getSize() * zoom + 200, 480));
        for (double d : guiC.getValueList(type)) {
            maxValue = Math.max(maxValue, (int)Math.round(d));
        }

        maxValue = Math.max(5, (int)(maxValue * 1.2));

        int xRightOffset = xOffset + (guiC.getSize() - 1) * zoom + 80;
        int y1, y2;
        for (int i = 0; i < 4; i++) {
            g.setColor(JBColor.BLACK);
            y1 = yOffset + height / 2 - height * i / 8;
            y2 = yOffset + height / 2 + height * i / 8;
            g.drawString(Integer.toString(maxValue * i / 4), xOffset - 60, y1);
            g.drawString(Integer.toString(maxValue * i / 4), xRightOffset + 30, y1);
            g.drawString( Integer.toString(-maxValue * i / 4), xOffset - 60, y2);
            g.drawString(Integer.toString(-maxValue * i / 4), xRightOffset + 30, y2);
            if (i != 0)
                g.setColor(JBColor.GRAY);
            g.drawLine(xOffset, y1, xRightOffset, y1);
            g.drawLine(xOffset, y2, xRightOffset, y2);
        }

        g.drawLine(xOffset, yOffset, xOffset, yOffset + height);
        g.drawLine(xRightOffset, yOffset, xRightOffset, yOffset + height);
        int x;

        int postX = 0, postY1 = 0, postY2 = 0;
        for(int i = guiC.getSize() - 1; i >= 0; i--) {
            int added = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i * 2))));
            int deleted = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i * 2 + 1))));

            x = xOffset + i * zoom + 40;
            y1 = yOffset + height / 2 - (height / 2) * added / maxValue;
            y2 = yOffset + height / 2 + (height / 2) * deleted / maxValue;

            int radius = (hoverJ == guiC.getSize() - 1 - i) ? 10 : 5;
            g.setColor(JBColor.BLUE);
            g.drawLine(x, yOffset + height / 2, x, y1);
            g.fillOval(x - radius, y1 - radius, radius * 2, radius * 2);
            if (i != guiC.getSize() - 1)
                g.drawLine(x, y1, postX, postY1);

            g.setColor(JBColor.ORANGE);
            g.drawLine(x, yOffset + height / 2, x, y2);
            g.fillOval(x - radius, y2 - radius, radius * 2, radius * 2);
            if (i != guiC.getSize() - 1)
                g.drawLine(x, y2, postX, postY2);

            g.setColor(JBColor.black);
            String dat = guiC.getCommitDate(i);
            g.drawString(dat, x - 18, yOffset + height + 30);

            postX = x;
            postY1 = y1;
            postY2 = y2;
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
        if (!type.equals("Code Churn")) {
            for (int i = guiC.getSize() - 1; i >= 0; i--) {
                int value = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i))));
                int x = i * zoom + 20;
                int y = height - height * value / maxValue;
                Rectangle rect = new Rectangle(xOffset + x - 5, yOffset + y - 20, 40, 40);
                if (rect.contains(mouseX, mouseY)) {
                    hoverJ = j;
                    hoverIndex = i;
                }
                j++;
            }
        } else {
            for (int i = guiC.getSize() - 1; i >= 0; i--) {
                int added = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i * 2))));
                int deleted = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i * 2 + 1))));
                int x = i * zoom + 20;
                int y1 = height / 2 - (height / 2) * added / maxValue;
                int y2 = height / 2 + (height / 2) * deleted / maxValue;
                Rectangle rect1 = new Rectangle(xOffset + x - 5, yOffset + y1 - 20, 40, 40);
                Rectangle rect2 = new Rectangle(xOffset + x - 5, yOffset + y2 - 20, 40, 40);
                if (rect1.contains(mouseX, mouseY) || rect2.contains(mouseX, mouseY)) {
                    hoverJ = j;
                    hoverIndex = i;
                }
                j++;
            }
        }
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

