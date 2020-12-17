package org.team4.team4_project.UI;

import org.team4.team4_project.ProjectHandler;
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
        String path = ProjectHandler.getProject().getBasePath();
        String name = ProjectHandler.getProject().getName();
        GUIController guiC = GUIController.getInstance();

        guiC.refreshController(path, name);
        MetricsWindow.getInstance().Open();

        MetricsWindow.getInstance().setVisible(true);
    }
}
