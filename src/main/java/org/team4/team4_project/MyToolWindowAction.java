package org.team4.team4_project;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

/**
 * MyToolWindow Actions class implements the action when the ToolWindow has been invoked.
 * @author Hongi Kim, Dookyeong Kang
 */
public class MyToolWindowAction extends AnAction {
    /**
     * Invoke when MyToolWindow(Tools > Show Graph) action is performed
     * @param e action event.
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project proj = e.getProject();
        assert proj != null;
        ProjectHandler.setProject(proj);

        ToolWindow win = ToolWindowManager.getInstance(proj).getToolWindow("Software Metrics");
        if (win != null && win.isAvailable())
            win.activate(null);
    }
}
