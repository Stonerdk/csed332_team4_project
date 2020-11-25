package org.team4.team4_project.UI;

import org.team4.team4_project.history.HistoryData;
import org.team4.team4_project.history.HistoryReader;
import org.team4.team4_project.metric_calculation.MetricMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class MetricsWindow extends JFrame {
    private static MetricsWindow instance = null;


    static class GraphPanel extends JPanel {
        double graphMaxValue;
        double graphMinValue;
        int xCount;
        int nodeCount;
        ArrayList<HistoryData> historyList;
        String Type;

        GraphPanel() {
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
                g.drawLine(postX, postY, 100*i + 45,500 -Integer.parseInt(String.valueOf(Math.round(h.getAttribute(Type))))*4);
                postX = 100*i + 45;
                postY = 500 -Integer.parseInt(String.valueOf(Math.round(h.getAttribute(Type))))*4;
                i++;
            }

        }
        void setScores(ArrayList<HistoryData> HistoryList, int type)
        {
            historyList = HistoryList;
            switch (type){
                case 1:
                    Type = "Halstead Complexity";
                    break;
                case 2:
                    Type = "Cyclomatic Complexity";
                    break;
                case 3:
                    Type = "Maintainability";
                    break;
            }
        }
    }

    private MetricsWindow () {
        MetricMain metricMain = new MetricMain();
        ArrayList<HistoryData> historyList;
        try {
            historyList = (ArrayList<HistoryData>) metricMain.mcMain();
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //ArrayList<HistoryData> historyList =

        setTitle("Software Metrics Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);

        Container contentPane = getContentPane();

        JPanel p = new JPanel();
        JButton Metric1 = new JButton("Halstead");
        JButton Metric2 = new JButton("Cyclomatic");
        JButton Metric3 = new JButton("Maintainability");

        p.add(Metric1);
        p.add(Metric2);
        p.add(Metric3);

        contentPane.add(p, BorderLayout.NORTH);

        GraphPanel graphPanel = new GraphPanel();
        contentPane.add(graphPanel, BorderLayout.CENTER);

        Metric1.addActionListener(new DrawActionListener(historyList, graphPanel, 1));
        Metric2.addActionListener(new DrawActionListener(historyList, graphPanel, 2));
        Metric3.addActionListener(new DrawActionListener(historyList, graphPanel, 3));

    }

    public static MetricsWindow getInstance() {
        if (instance == null)
            instance = new MetricsWindow();
        return instance;
    }


    class DrawActionListener implements ActionListener
    {
        ArrayList<HistoryData> HistoryList;
        GraphPanel drawingPanel;
        int type;
        DrawActionListener(ArrayList<HistoryData> historylist, GraphPanel drawingPanel, int type)
        {
            HistoryList = historylist;
            this.type = type;
            this.drawingPanel = drawingPanel;
        }
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                drawingPanel.setScores(HistoryList, type);
                drawingPanel.repaint();
            }
            catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(drawingPanel,"Invalid Number","Error Message",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
