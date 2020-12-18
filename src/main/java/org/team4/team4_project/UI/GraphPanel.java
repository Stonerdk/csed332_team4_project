package org.team4.team4_project.UI;

import com.intellij.ui.JBColor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphPanel extends JPanel implements MouseMotionListener, MouseListener, ComponentListener {
    private int maxValue = 10;
    private int height = 400;
    private int xOffset = 100;
    private int yOffset = 100;
    private int checkIndex = -1;
    private int zoom = 80;
    private int hoverIndex;
    private String type;

    private MetricsWindow parentFrame;
    private GUIController guiC;

    Color colorTransparent = new JBColor(new Color(30, 80, 200, 128), new Color(30, 80, 200, 128));
    JBColor colorNormal = new JBColor(new Color(30, 80, 200), new Color(30, 80, 200));
    JBColor colorSelected = new JBColor(new Color(40, 50, 160), new Color(40, 50, 160));
    Color colorChurnAddedTransparent = new JBColor(new Color(25, 110, 53, 128),new Color(25, 110, 53, 128));
    JBColor colorChurnAdded = new JBColor(new Color(25, 110, 53), new Color(25, 110, 53));
    JBColor colorChurnAddedSelected = new JBColor(new Color(25, 70, 30), new Color(25, 70, 30));
    Color colorChurnDeletedTransparent = new JBColor(new Color(237, 79, 55, 128), new Color(237, 79, 55, 128));
    JBColor colorChurnDeleted = new JBColor(new Color(237, 79, 55), new Color(237, 79, 55));
    JBColor colorChurnDeletedSelected = new JBColor(new Color(160, 49, 35), new Color(160, 49, 35));

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
        int xRightOffset = xOffset + (guiC.getSize() - 1) * zoom + 80;
        int postX = 0, postY = 0;

        maxValue = 0;
        setPreferredSize(new Dimension(guiC.getSize() * zoom + 200, 480));

        for (double d : guiC.getValueList(type))
            maxValue = Math.max(maxValue, (int)Math.round(d));

        maxValue = Math.max(5, (int)(maxValue * 1.2));

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

        for(int i = guiC.getSize() - 1; i >= 0; i--) {
            int value = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i))));
            int x = xOffset + i * zoom + 40;
            int y = yOffset + height - height * value / maxValue;

            if (i != guiC.getSize() - 1) {
                g.setColor(colorTransparent);
                g.fillPolygon(new int[]{x, postX, postX, x}, new int[]{y, postY, yOffset + height, yOffset + height}, 4);
            }

            g.setColor(colorNormal);
            g.drawLine(x, yOffset + height, x, y);
            if (i != guiC.getSize() - 1) {
                g.drawLine(x, y, postX, postY);
            }
            g.setColor(JBColor.black);
            String dat = guiC.getCommitDate(i);
            g.drawString(dat, x - 18, yOffset + height + 30);
            postX = x;
            postY = y;
        }

        for(int i = guiC.getSize() - 1; i >= 0; i--) {
            int value = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i))));
            int x = xOffset + i * zoom + 40;
            int y = yOffset + height - height * value / maxValue;
            int radius = (hoverIndex == i || checkIndex == i) ? 10 : 5;

            g.setColor(checkIndex == i ? colorSelected : colorNormal);
            g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }

    private void paintChurnGraph(Graphics2D g) {
        maxValue = 0;
        int xRightOffset = xOffset + (guiC.getSize() - 1) * zoom + 80;
        int x, y1, y2;
        int postX = 0, postY1 = 0, postY2 = 0;

        setPreferredSize(new Dimension(guiC.getSize() * zoom + 200, 480));
        for (double d : guiC.getValueList(type))
            maxValue = Math.max(maxValue, (int)Math.round(d));

        maxValue = Math.max(5, (int)(maxValue * 1.2));

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

        for(int i = guiC.getSize() - 1; i >= 0; i--) {
            int added = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i * 2))));
            int deleted = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i * 2 + 1))));

            x = xOffset + i * zoom + 40;
            y1 = yOffset + height / 2 - (height / 2) * added / maxValue;
            y2 = yOffset + height / 2 + (height / 2) * deleted / maxValue;

            if (i != guiC.getSize() - 1) {
                g.setColor(colorChurnAddedTransparent);
                g.fillPolygon(new int[]{x, postX, postX, x}, new int[]{y1, postY1, yOffset + height / 2, yOffset + height / 2}, 4);
                g.setColor(colorChurnDeletedTransparent);
                g.fillPolygon(new int[]{x, postX, postX, x}, new int[]{y2, postY2, yOffset + height / 2, yOffset + height / 2}, 4);
            }

            g.setColor(colorChurnAdded);
            g.drawLine(x, yOffset + height / 2, x, y1);

            if (i != guiC.getSize() - 1)
                g.drawLine(x, y1, postX, postY1);

            g.setColor(colorChurnDeleted);
            g.drawLine(x, yOffset + height / 2, x, y2);

            if (i != guiC.getSize() - 1)
                g.drawLine(x, y2, postX, postY2);

            g.setColor(JBColor.black);
            String dat = guiC.getCommitDate(i);
            g.drawString(dat, x - 18, yOffset + height + 30);

            postX = x;
            postY1 = y1;
            postY2 = y2;
        }

        for(int i = guiC.getSize() - 1; i >= 0; i--) {
            int added = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i * 2))));
            int deleted = Integer.parseInt(String.valueOf(Math.round(guiC.getValueList(type).get(i * 2 + 1))));
            int radius = (hoverIndex == i || checkIndex == i) ? 10 : 5;

            x = xOffset + i * zoom + 40;
            y1 = yOffset + height / 2 - (height / 2) * added / maxValue;
            y2 = yOffset + height / 2 + (height / 2) * deleted / maxValue;

            g.setColor(checkIndex == i ? colorChurnAddedSelected : colorChurnAdded);
            g.fillOval(x - radius, y1 - radius, radius * 2, radius * 2);
            g.setColor(checkIndex == i ? colorChurnDeletedSelected : colorChurnDeleted);
            g.fillOval(x - radius, y2 - radius, radius * 2, radius * 2);
        }
    }

    /**
     * Change type of metric value displayed on the graph
     *
     * @param type the type of metric value
     */
    void setType(String type) {
        this.type = type;
    }

    /**
     * Change distance between adjacent points in the graph
     *
     * @param k degree of distance that you want
     */
    void Zoom(int k){
        zoom = k;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hoverIndex = (e.getX() + zoom / 2 - xOffset - 40) / zoom;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (hoverIndex != -1) {
            parentFrame.setStatusHistory(hoverIndex);
            checkIndex = hoverIndex;
        }
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
        height = getHeight() - 2 * yOffset;
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
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

