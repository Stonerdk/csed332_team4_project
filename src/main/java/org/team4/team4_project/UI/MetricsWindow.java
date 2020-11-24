package org.team4.team4_project.UI;

import org.team4.team4_project.history.HistoryData;

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
        int score_java, score_python, score_cs;

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
            g.drawLine(50,250,350,250);
            for(int cnt = 1 ;cnt<11;cnt++)
            {
                g.drawString(cnt *10 +"",25,255-20*cnt);
                g.drawLine(50, 250-20*cnt, 350,250-20*cnt);
            }
            g.drawLine(50,20,50,250);
            g.drawString("Java",100,270);
            g.drawString("Python",200,270);
            g.drawString("C#",300,270);
            g.setColor(Color.BLUE);
            if (score_java>0)
                g.fillRect(110,250-score_java*2,10,score_java*2);
            if(score_python>0)
                g.fillRect(210,250-score_python*2,10,score_python*2);
            if(score_cs>0)
                g.fillRect(310,250-score_cs*2,10,score_cs*2);
        }
        void setScores(int score_java, int score_python, int score_cs)
        {
            this.score_java=score_java;
            this.score_python=score_python;
            this.score_cs=score_cs;
        }
    }

    private MetricsWindow () {
        setTitle("Software Metrics Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);

        Container contentPane = getContentPane();

        JPanel p = new JPanel();
        JButton Metric1 = new JButton("Metric1");
        JButton Metric2 = new JButton("Metric2");
        JButton Metric3 = new JButton("Metric3");

        p.add(Metric1);
        p.add(Metric2);
        p.add(Metric3);

        contentPane.add(p, BorderLayout.NORTH);

        GraphPanel graphPanel = new GraphPanel();
        contentPane.add(graphPanel, BorderLayout.CENTER);

        ArrayList<Integer> Metric1_ = new ArrayList<Integer>();
        ArrayList<Integer> Metric2_ = new ArrayList<Integer>();
        ArrayList<Integer> Metric3_ = new ArrayList<Integer>();

        Metric1_.add(50);
        Metric1_.add(34);
        Metric1_.add(73);
        Metric1_.add(92);
        Metric1_.add(13);

        Metric2_.add(25);
        Metric2_.add(46);
        Metric2_.add(14);
        Metric2_.add(86);
        Metric2_.add(94);

        Metric3_.add(78);
        Metric3_.add(36);
        Metric3_.add(95);
        Metric3_.add(67);
        Metric3_.add(32);

        Metric1.addActionListener(new DrawActionListener(50, 34, 73, graphPanel));
        Metric2.addActionListener(new DrawActionListener(25,46, 14, graphPanel));
        Metric3.addActionListener(new DrawActionListener(78,36,95, graphPanel));

    }

    public static MetricsWindow getInstance() {
        if (instance == null)
            instance = new MetricsWindow();
        return instance;
    }


    class DrawActionListener implements ActionListener
    {
        int text1,text2,text3;
        GraphPanel drawingPanel;
        DrawActionListener(int text1, int text2, int text3, GraphPanel drawingPanel)
        {
            this.text1=text1;
            this.text2=text2;
            this.text3=text3;
            this.drawingPanel = drawingPanel;
        }
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                int korean = text1;
                int english = text2;
                int math = text3;
                drawingPanel.setScores(korean, english, math);
                drawingPanel.repaint();
            }
            catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(drawingPanel,"Invalid Number","Error Message",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
