package org.team4.team4_project.UI;

import org.team4.team4_project.ProjectHandler;
import javax.swing.*;

public class MyToolWindowGUI {
    private JPanel mainPanel;
    private JButton showMetricsButton;

    /**
     * Construct MyToolWindowGUI and add click action to "Show Metrics" button
     *
     */
    public MyToolWindowGUI() {
        showMetricsButton.addActionListener(event -> onClick());
    }

    /**
     * Returns main JPanel
     *
     * @return JPanel mainPanel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * When click "Show Metrics" button, open plugin window using currently opened project
     *
     */
    private void onClick() {
        String path = ProjectHandler.getProject().getBasePath();
        String name = ProjectHandler.getProject().getName();
        GUIController guiC = GUIController.getInstance();

        guiC.refreshController(path, name);
        MetricsWindow.getInstance().Open();

        MetricsWindow.getInstance().setVisible(true);
    }
}
