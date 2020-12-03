package org.team4.team4_project;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;
import org.team4.team4_project.UI.ProjectHandler;

public class MyToolWindowAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project proj = e.getProject();
        assert proj != null;
        System.out.println(proj.getBasePath());
        ProjectHandler.project = proj;

        ToolWindow win = ToolWindowManager.getInstance(proj).getToolWindow("Software Metrics");
        if (win != null && win.isAvailable())
            win.activate(null);
    }
}
