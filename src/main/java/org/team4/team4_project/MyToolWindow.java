package org.team4.team4_project;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import org.team4.team4_project.UI.MyToolWindowGUI;

import javax.swing.*;

public class MyToolWindow {
    private final JPanel myToolWindowContent;
    public MyToolWindow(ToolWindow toolWindow) {
        MyToolWindowGUI gui = new MyToolWindowGUI();
        myToolWindowContent = gui.getMainPanel();
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
}
