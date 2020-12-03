package org.team4.team4_project.UI;

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
    private CommitInfo Commit;

    private static GUIController guiC = new GUIController();

    public static GUIController getInstance(){
        return guiC;
    }

    public void refreshController(){
        try {
            FileList = new MetricMain().mcMain();
            File = FileList.get(0);
        } catch (ParseException | NullPointerException | IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public int getSize(){
        return File.comInfoList.size();
    }

    public String getCommitDate(int i){
        return String.valueOf(File.comInfoList.get(i).date.getTime());
    }

    public List<String> getAllValue(int idx){
        List<String> temp = new ArrayList<String>();
        Commit = File.comInfoList.get(idx);

        temp.add(Commit.date.toString());
        temp.add(Commit.commitHash);
        temp.add(Commit.branchName);
        temp.add(String.valueOf(Commit.halProgVocab));
        temp.add(String.valueOf(Commit.halProgLen));
        temp.add(String.valueOf(Commit.halCalProgLen));
        temp.add(String.valueOf(Commit.halVolume));
        temp.add(String.valueOf(Commit.halDifficulty));
        temp.add(String.valueOf(Commit.halEffort));
        temp.add(String.valueOf(Commit.halTime));
        temp.add(String.valueOf(Commit.halNumBugs));
        temp.add(String.valueOf(Commit.cyclomaticComplexity));
        temp.add(String.valueOf(Commit.maintainabilityIndex));

        return temp;
    }

    public void selectFile(String FileName, String Path){
        for(FileInfo f : FileList){
            if(f.fileName.equals(FileName)){
                if(f.filePath.equals(Path)){
                    File = f;
                }
            }
        }
    }

    public List<Double> getValueList(String Type){
        List<Double> temp = new ArrayList<Double>();
        switch(Type) {
            case "Halstead Vocabulary":
                for(CommitInfo c : File.comInfoList){
                    temp.add(c.halProgVocab); }
                break;
            case "Halstead Program Length":
                for(CommitInfo c : File.comInfoList){
                    temp.add(c.halProgLen); }
                break;            case "Halstead Cal Prog Length":
            case "Halstead Volume":
                for(CommitInfo c : File.comInfoList){
                    temp.add(c.halVolume); }
                break;
            case "Halstead Difficulty":
                for(CommitInfo c : File.comInfoList){
                    temp.add(c.halDifficulty); }
                break;
            case "Halstead Effort":
                for(CommitInfo c : File.comInfoList){
                    temp.add(c.halEffort); }
                break;
            case "Halstead Time Required":
                for(CommitInfo c : File.comInfoList){
                    temp.add(c.halTime); }
                break;
            case "Halstead Num Del Bugs":
                for(CommitInfo c : File.comInfoList){
                    temp.add(c.halNumBugs); }
                break;
            case "Cyclomatic Complexity":
                for(CommitInfo c : File.comInfoList){
                    temp.add(c.cyclomaticComplexity); }
                break;
            case "Maintainability":
                for(CommitInfo c : File.comInfoList){
                    temp.add(c.maintainabilityIndex); }
                break;
            case "Code Churn":
                for(CommitInfo c : File.comInfoList){
                    temp.add(0.0); }
                break; // Implement code churn own way without using getValue
            default:
                System.out.println("Wrong type name : " + Type);
        }
        return temp;
    }
}
