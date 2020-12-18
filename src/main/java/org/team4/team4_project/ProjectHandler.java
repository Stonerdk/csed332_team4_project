package org.team4.team4_project;

import com.intellij.openapi.project.Project;

/**
 * Handler class which provides static information of current project.
 * @author Dookyeong Kang
 */
public class ProjectHandler {
    public static Project project;

    /**
     * Set project handler with current project
     *
     * @param proj current project
     */
    public static void setProject(Project proj) {
        project = proj;
    }

    /**
     * Return information of current project
     *
     * @return Project of current project
     */
    public static Project getProject()
    {
        return project;
    }
}
