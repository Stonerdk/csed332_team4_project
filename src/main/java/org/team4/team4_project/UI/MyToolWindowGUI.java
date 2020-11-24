package org.team4.team4_project.UI;

import org.team4.team4_project.UI.MetricsWindow;

import javax.swing.*;

public class MyToolWindowGUI {
    private JPanel mainPanel;
    private JButton showMetricsButton;

    public MyToolWindowGUI() {
        showMetricsButton.addActionListener(event -> onClick());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    private void onClick() {
        MetricsWindow.getInstance().setVisible(true);
    }
}
