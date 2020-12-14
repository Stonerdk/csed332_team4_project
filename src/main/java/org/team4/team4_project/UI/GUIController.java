package org.team4.team4_project.UI;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.DepthWalk;
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

    private static GUIController guiC = new GUIController();

    public static GUIController getInstance(){
        return guiC;
    }

    public void refreshController(){
        try {
            FileList = new MetricMain().mcMain();
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

    public boolean isProj(){
        return File.isProject();
    }

    public int getSize(){
        return File.getComInfoList().size();
    }

    public String getCommitDate(int i){
        String aa = File.getComInfoList().get(i).getDate().toString();
        String rtn = aa.substring(4,7) + " " + aa.substring(8,10);
        return rtn;
    }

    public List<String> getAllValue(int idx){
        List<String> temp = new ArrayList<String>();
        Commit = File.getComInfoList().get(idx);

        temp.add(Commit.getDate().toString());
        temp.add(Commit.getCommitHash());
        temp.add(Commit.getBranchName());
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

    public List<String> getAllPath(){
        List<String> temp = new ArrayList<String>();
        for(FileInfo f : FileList){
            temp.add(f.getFilePath());
        }
        return temp;
    }

    public String getName(){
        return File.getFileName();
    }

    public void selectFile(String FileName, String Path){
        for(FileInfo f : FileList){
            if(f.getFileName().equals(FileName)){
                if(f.getFilePath().equals(Path)){
                    File = f;
                }
            }
        }
    }

    public void selectProj(){
        File = Proj;
    }

    public String getFileName(int idx){
        return FileList.get(idx).getFileName();
    }

    public String getFilePath(int idx){
        return FileList.get(idx).getFilePath();
    }

    public List<Double> getValueList(String Type){
        List<Double> temp = new ArrayList<Double>();
        switch(Type) {
            case "Code Length":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add((double)c.getCodeLen()); }
                break;
            case "Halstead Vocabulary":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getHalProgVocab()); }
                break;
            case "Halstead Program Length":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getHalProgLen()); }
                break;
            case "Halstead Cal Prog Length":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getHalCalProgLen()); }
                break;
            case "Halstead Volume":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getHalVolume()); }
                break;
            case "Halstead Difficulty":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getHalDifficulty()); }
                break;
            case "Halstead Effort":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getHalEffort()); }
                break;
            case "Halstead Time Required":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getHalTime()); }
                break;
            case "Halstead Num Del Bugs":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getHalNumBugs()); }
                break;
            case "Cyclomatic Complexity":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getCyclomaticComplexity()); }
                break;
            case "Maintainability":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add(c.getMaintainabilityIndex()); }
                break;
            case "Code Churn":
                for(CommitInfo c : File.getComInfoList()){
                    temp.add((double)c.getChurn().getLinesAdded());
                    temp.add((double)c.getChurn().getLinesDeleted());
                }

                break; // Implement code churn own way without using getValue
            case "Number of Methods":
                for (CommitInfo c : File.getComInfoList()) {
                    temp.add((double)c.getNumMethod());
                }
                break;
            case "Number of Loops":
                for (CommitInfo c : File.getComInfoList()) {
                    temp.add((double)c.getNumLoop());
                }
                break;
            case "Number of Imports":
                for (CommitInfo c : File.getComInfoList()) {
                    temp.add((double)c.getNumImport());
                }
                break;
            default:
                System.out.println("Wrong type name : " + Type);
                break;
        }
        return temp;
    }
}
