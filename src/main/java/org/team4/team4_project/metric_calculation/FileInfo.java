package org.team4.team4_project.metric_calculation;

import java.util.ArrayList;
import java.util.List;

public class FileInfo {
    private String fileName = null;
    private String filePath = null;
    private List<CommitInfo> comInfoList = new ArrayList<CommitInfo> ();

    private Boolean thisIsProject = false;

    public Boolean isProject(){
        return thisIsProject;
    }

    public void setIsProject(){
        thisIsProject = true;
    }

    //method to set, get
    public String getFileName(){return fileName;}
    public void setFileName(String str){fileName = str;}

    public String getFilePath(){return filePath;}
    public void setFilePath(String str){filePath = str;}

    public List<CommitInfo> getComInfoList(){return comInfoList;}
    public void setComInfoList(List<CommitInfo> cList){comInfoList = cList;}


}
