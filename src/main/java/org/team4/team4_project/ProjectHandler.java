package org.team4.team4_project;

import com.intellij.openapi.project.Project;

public class ProjectHandler {
    public static Project project;
    public static String projectPath;

    /**
     * Set current working project.
     *
     * @param proj project
     */
    public static void setProject(Project proj) {
        project = proj;
        projectPath = proj.getBasePath();
    }

    /**
     * Get current working project.
     *
     * @return current project
     */
    public static Project getProject()
    {
        return project;
    }
}
