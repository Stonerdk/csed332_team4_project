package org.team4.team4_project.metric_calculation;

import java.util.ArrayList;
import java.util.List;

public class FileInfo {
    public String fileName = null;
    public String filePath = null;
    public List<CommitInfo> comInfoList = new ArrayList<CommitInfo> ();

    static Boolean thisIsProject = false;

    Boolean isProject(){
        return thisIsProject;
    }

    void setIsProject(){
        thisIsProject = true;
    }


}
