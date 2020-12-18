package org.team4.team4_project.metric_calculation;

import java.util.ArrayList;
import java.util.List;

public class FileInfo {
    private String fileName = null;
    private String filePath = null;
    private List<CommitInfo> comInfoList = new ArrayList<CommitInfo> ();

    private Boolean thisIsProject = false;

    /**
     * A method check if this FileInfo is entire project
     * @return if it is project or not
     */
    public Boolean isProject(){
        return thisIsProject;
    }

    /**
     * A method set this FileInfo to entire project
     */
    public void setIsProject(){
        thisIsProject = true;
    }

    //method to set, get
    /**
     * A method returns filename of this FileInfo
     * @return filename of this FileInfo
     */
    public String getFileName(){ return fileName;}

    /**
     * A method sets filename of this fileinfo
     * @param str filename of this fileinfo
     */
    public void setFileName(String str){fileName = str;}

    /**
     * A method returns filepath of this FileInfo
     * @return filepath of this FileInfo
     */
    public String getFilePath(){return filePath;}

    /**
     * A method sets filepath of this fileinfo
     * @param str filepath of this fileinfo
     */
    public void setFilePath(String str){filePath = str;}

    /**
     * A method returns list of commitInfos of this file
     * @return comInfoList commit informations of this file
     */
    public List<CommitInfo> getComInfoList(){return comInfoList;}

    /**
     * A method sets list of commitInfos of this fileinfo
     * @param cList list of commitInfo
     */
    public void setComInfoList(List<CommitInfo> cList){comInfoList = cList;}


}
