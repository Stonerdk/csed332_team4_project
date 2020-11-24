package org.team4.team4_project.UI;

import org.team4.team4_project.history.HistoryData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MetricsWindow extends JFrame {
    private static MetricsWindow instance = null;

    static class GraphPanel extends JPanel {
        double graphMaxValue;
        double graphMinValue;
        int xCount;
        int nodeCount;
        ArrayList<HistoryData> historyList;

        GraphPanel() {
            setPreferredSize(new Dimension(640, 480));
        }

        private void refresh() {
            nodeCount = Math.min(xCount, historyList.size());
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

        }
    }

    private MetricsWindow () {
        setTitle("Software Metrics Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        GraphPanel mainPanel = new GraphPanel();
        setContentPane(mainPanel);
    }

    public static MetricsWindow getInstance() {
        if (instance == null)
            instance = new MetricsWindow();
        return instance;
    }
}
