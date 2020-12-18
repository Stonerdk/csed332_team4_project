package org.team4.team4_project;

import org.team4.team4_project.UI.MyToolWindowGUI;

import javax.swing.*;

/**
 * MyToolWindow class.
 * @author Hongi Kim, Dookyeong Kang
 */
public class MyToolWindow {
    private final JPanel myToolWindowContent;

    /**
     * Construct MyToolWindow
     *
     */
    public MyToolWindow() {
        MyToolWindowGUI gui = new MyToolWindowGUI();
        myToolWindowContent = gui.getMainPanel();
    }

    /**
     * Return myToolWindowContent that is main panel of MyToolWindowGUI
     *
     * @return JPanel myToolWindowContent
     */
    public JPanel getContent() {
        return myToolWindowContent;
    }
}
