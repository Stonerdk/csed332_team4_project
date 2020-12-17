package org.team4.team4_project.UI;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.team4.team4_project.metric_calculation.CommitInfo;
import org.team4.team4_project.metric_calculation.FileInfo;
import org.team4.team4_project.metric_calculation.MetricMain;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GUIController {
    private List<FileInfo> FileList;
    private FileInfo File;
    private FileInfo Proj;
    private CommitInfo Commit;
    private String path;
    private String proj;

    private static GUIController guiC = new GUIController();

    public static GUIController getInstance(){
        return guiC;
    }

    /**
     * Refresh metric window to current project
     *
     * @param _path    path of current project
     * @param projName name of current project
     */
    public void refreshController(String _path, String projName){
        try {
            path = _path;
            proj = projName;

            FileList = new MetricMain().mcMain(path, proj);

            for(FileInfo f : FileList){
                if(f.isProject()) {
                    Proj = f;
                    FileList.remove(f);
                    break;
                }
            }
            File = Proj;
        } catch (ParseException | NullPointerException | IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns path of current project as String
     *
     * @return String path of current project
     */
    public String getPath(){
        return path;
    }

    /**
     * Returns name of the current project ad String
     *
     * @return String name of current project
     */
    public String getProj(){
        return proj;
    }

    /**
     *  Returns the size of commit list of the file chosen.
     *
     * @return int size of commit list of the file currently opened
     */
    public int getSize(){
        return File.getComInfoList().size();
    }

    /**
     * Returns the date of commit that you chose
     *
     * @param index int index of commit list that you want to see date
     * @return String date of index-th commit of chose file as a form of "Jan 01"
     */
    public String getCommitDate(int index){
        String aa = File.getComInfoList().get(index).getDate().toString();
        String date = aa.substring(4,7) + " " + aa.substring(8,10);
        return date;
    }

    /**
     * Collect all metric values of commit that you want to see and return
     *
     * @param index int index of commit list that you want to see metric values
     * @return List of String that has date, commit hash, and metric values of chosen commit of chosen file
     */
    public List<String> getAllValue(int index){
        List<String> temp = new ArrayList<String>();
        Commit = File.getComInfoList().get(index);

        temp.add(Commit.getDate().toString());
        temp.add(Commit.getCommitHash());
        temp.add(String.valueOf(Commit.getCodeLen()));
        temp.add(String.valueOf(Commit.getHalProgVocab()));
        temp.add(String.valueOf(Commit.getHalProgLen()));
        temp.add(String.valueOf(Commit.getHalCalProgLen()));
        temp.add(String.valueOf(Commit.getHalVolume()));
        temp.add(String.valueOf(Commit.getHalDifficulty()));
        temp.add(String.valueOf(Commit.getHalEffort()));
        temp.add(String.valueOf(Commit.getHalTime()));
        temp.add(String.valueOf(Commit.getHalNumBugs()));
        temp.add(String.valueOf(Commit.getCyclomaticComplexity()));
        temp.add(String.valueOf(Commit.getMaintainabilityIndex()));
        temp.add(String.valueOf(Commit.getChurn().getLinesAdded()));
        temp.add(String.valueOf(Commit.getChurn().getLinesDeleted()));
        temp.add(String.valueOf(Commit.getNumMethod()));
        temp.add(String.valueOf(Commit.getNumLoop()));
        temp.add(String.valueOf(Commit.getNumImport()));

        return temp;
    }

    /**
     * Collect path of all files in the file list
     *
     * @return List of String which contains path of each file
     */
    public List<String> getAllPath(){
        List<String> temp = new ArrayList<String>();
        for(FileInfo f : FileList)
            temp.add(f.getFilePath());

        return temp;
    }

    /**
     * Return name of currently chosen file
     *
     * @return String name of currently chosen file
     */
    public String getName(){
        return File.getFileName();
    }

    /**
     * Select certain file using file name and its path
     *
     * @param FileName name of file chosen in the struct tree
     * @param Path string that contains path of file chosen in the struct tree
     */
    public void selectFile(String FileName, String Path){
        for(FileInfo f : FileList){
            if(f.getFileName().equals(FileName)){
                if(f.getFilePath().equals(Path)){
                    File = f;
                }
            }
        }
    }

    /**
     * Select Entire project as current file
     *
     */
    public void selectProj(){
        File = Proj;
    }

    /**
     * Return file path of currently chosen file
     *
     * @param index index of file that you want to see
     * @return String path of currently chosen file
     */
    public String getFilePath(int index){
        return FileList.get(index).getFilePath();
    }

    /**
     * Return list of double, containing certain metric values to make a graph
     *
     * @param Type type of metric value that you want to see
     * @return List of Double which contains metric value you chose
     */
    public List<Double> getValueList(String Type){
        List<Double> temp = new ArrayList<Double>();
        switch(Type) {
            case "Code Length":
                for(CommitInfo c : File.getComInfoList())
                    temp.add((double)c.getCodeLen());
                break;

            case "Halstead Vocabulary":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getHalProgVocab());
                break;

            case "Halstead Program Length":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getHalProgLen());
                break;

            case "Halstead Cal Prog Length":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getHalCalProgLen());
                break;

            case "Halstead Volume":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getHalVolume());
                break;

            case "Halstead Difficulty":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getHalDifficulty());
                break;

            case "Halstead Effort":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getHalEffort());
                break;

            case "Halstead Time Required":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getHalTime());
                break;

            case "Halstead Num Del Bugs":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getHalNumBugs());
                break;

            case "Cyclomatic Complexity":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getCyclomaticComplexity());
                break;

            case "Maintainability":
                for(CommitInfo c : File.getComInfoList())
                    temp.add(c.getMaintainabilityIndex());
                break;

            case "Code Churn":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add((double)c.getChurn().getLinesAdded());
                    temp.add((double)c.getChurn().getLinesDeleted());
                }
                break; // Implement code churn own way without using getValue

            case "Number of Methods":
                for (CommitInfo c : File.getComInfoList())
                    temp.add((double)c.getNumMethod());
                break;

            case "Number of Loops":
                for (CommitInfo c : File.getComInfoList())
                    temp.add((double)c.getNumLoop());
                break;

            case "Number of Imports":
                for (CommitInfo c : File.getComInfoList())
                    temp.add((double)c.getNumImport());
                break;
            default:
                System.out.println("Wrong type name : " + Type);
                break;
        }

        return temp;
    }
}
