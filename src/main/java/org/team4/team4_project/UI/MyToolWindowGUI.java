package org.team4.team4_project.UI;

import com.intellij.openapi.project.Project;
import org.team4.team4_project.ProjectHandler;
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

    /*public void setProject(Project project) {
        MetricsWindow.getInstance().setProject(project);
    }*/
    private void onClick() {
        String path = ProjectHandler.getProject().getBasePath();
        String name = ProjectHandler.getProject().getName();

        GUIController guiC = GUIController.getInstance();

        guiC.refreshController(path, name);
        MetricsWindow.getInstance().Open();

        MetricsWindow.getInstance().setVisible(true);
    }
}
