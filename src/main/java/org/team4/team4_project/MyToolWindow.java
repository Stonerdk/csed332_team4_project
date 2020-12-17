package org.team4.team4_project;

import org.team4.team4_project.UI.MyToolWindowGUI;

import javax.swing.*;

public class MyToolWindow {
    private final JPanel myToolWindowContent;

    public MyToolWindow() {
        MyToolWindowGUI gui = new MyToolWindowGUI();
        myToolWindowContent = gui.getMainPanel();
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
}
