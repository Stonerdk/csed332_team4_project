package org.team4.team4_project.UI;

import javax.swing.*;
import java.awt.*;

public class MetricsWindow extends JFrame {
    private static MetricsWindow instance = null;

    static class GraphPanel extends JPanel {
        GraphPanel() {
            setPreferredSize(new Dimension(640, 480));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("BLAH", 20, 20);
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
