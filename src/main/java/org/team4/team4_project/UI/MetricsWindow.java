package org.team4.team4_project.UI;

import org.team4.team4_project.history.HistoryData;
import org.team4.team4_project.history.HistoryReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            g.drawLine(100,500,100 + 200*historyList.size(),500);
            for(int cnt = 1; cnt < 11; cnt++)
            {
                g.drawString(cnt *10 +"",50,510-40*cnt);
                g.drawLine(100, 500-40*cnt, 100 + 200*historyList.size(),500-40*cnt);
            }
            g.drawLine(100,40,100,500);

            //g.setColor(Color.BLUE);
            int i = 1;
            for(HistoryData h : historyList){
                g.drawString(h.getCommitString(), 200 * i, 540);

                g.fillRect(200*(i++) + 17, 500 -Integer.parseInt(String.valueOf(Math.round(h.getAttribute(Type))))*4,
                        20, Integer.parseInt(String.valueOf(Math.round(h.getAttribute(Type))))*4);
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
        ArrayList<HistoryData> historyList = new HistoryReader().getHistoryList();;

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
