package org.team4.team4_project.metric_calculation;

import java.io.*;
import java.text.ParseException;
import java.util.*;

import com.google.common.collect.Iterables;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.team4.team4_project.git.ChurnResult;
import org.team4.team4_project.git.GitHandler;

public class MetricMain {

    public static List<char[]> ParseFilesInDir(List<String> JavaFiles) throws IOException{
        if(JavaFiles.isEmpty())
        {
            System.out.println("There is no java source code in the provided directory");
            System.exit(0);
        }

        List<char[]> FilesRead= new ArrayList<char []>();

        for(int i=0; i<JavaFiles.size(); i++)
        {
            System.out.println("Now, reading: "+ JavaFiles.get(i));
            FilesRead.add(ReadFileToCharArray(JavaFiles.get(i)));
        }

        return FilesRead;
    }

    // parse file in char array
    public static char[] ReadFileToCharArray(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();

        return  fileData.toString().toCharArray();
    }

    // retrieve all .java files in the directory and subdirectories.
    public static List<String> retrieveFiles(String directory) {
        List<String> Files = new ArrayList<String>();
        File dir = new File(directory);


        if(dir.isFile()){
            if (dir.getName().endsWith((".java")))
            {
                Files.add(dir.getAbsolutePath());
                return Files;
            }
        }

        if (!dir.isDirectory())
        {
            System.out.println("The provided path is not a valid directory");
            System.exit(1);
        }



        for (File file : dir.listFiles()) {
            if(file.isDirectory())
            {
                Files.addAll(retrieveFiles(file.getAbsolutePath()));
            }
            if (file.getName().endsWith((".java")))
            {
                Files.add(file.getAbsolutePath());
            }
        }

        return Files;
    }


    // construct AST of the .java files
    /**
     * Given a string of a java file and a parsing option, parse string to a tree and visit all nodes though a visitor
     * @param str a string of a java file contents
     * @param opt 1 or 2 (1: set ASTParser as a parser for compilation unit, 2: set ASTParser as a parser for a number of statements
     * @return An ASTVisitorSearch which was used to visit all nodes in tree made by parsing input string
     */
    public static ASTVisitorSearch parse(char[] str, int opt) {
        /**
         * option 1: using compilationUnit
         * option 2: using a sequence of statements
         * option 3: using a
         */
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(str);
        parser.setResolveBindings(true);
        //parser.setBindingsRecovery(true);
        parser.setStatementsRecovery(true);

        if (opt == 1){
            parser.setKind(ASTParser.K_COMPILATION_UNIT);
            ASTVisitorSearch visitor= new ASTVisitorSearch();

            try {
                final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

                // visit nodes of the constructed AST
                cu.accept(visitor);

                //Calculate the number of source, comments (for Cyclomatic complexity)
                visitor.codeLen += (new String(str)).lines().count();

                for (Comment comment : (List<Comment>) cu.getCommentList()) {
                    int start = comment.getStartPosition();
                    int end = start + comment.getLength();
                    String comment_str = (new String(str)).substring(start, end);

                    visitor.commentLen += comment_str.lines().count();
                }

            } catch (Exception e){
                System.out.println("Some files are invalid(cannot compile)");
                return visitor;
            }

            return visitor;
        }
        else if(opt ==2){
            parser.setKind(ASTParser.K_STATEMENTS);
            final Block cu = (Block) parser.createAST(null);

            // Check for compilationUnits problems in the provided file

            // visit nodes of the constructed AST
            ASTVisitorSearch visitor= new ASTVisitorSearch();
            cu.accept(visitor);

            //Calculate the number of source, comments (for Cyclomatic complexity)
            visitor.codeLen += (new String(str)).lines().count();


            return visitor;

        }
        else{
            System.out.println("WRONG PARSING OPTION INPUT");
            return null;
        }

    }

    /**
     * UI part call a mcMain method to get List<FileInfo> which is overall structure storing all calculated metrics needed to display graph.
     * @param path an absolute path of the directory which includes .git
     * @param name a project name a user open using a plugin
     */
    public List<FileInfo> mcMain(String path, String name) throws IOException, ParseException, GitAPIException {

        // Get from git
        //String temp = ProjectHandler.getProject().getBasePath();
        GitHandler gitHandler = new GitHandler(path);
        List<FileInfo> fileInfoList = gitHandler.getFileInfo();
        //String name = ProjectHandler.getProject().getName();
        for (FileInfo f : fileInfoList) {
            f.setFilePath(name + "/" + f.getFilePath());
        }


        // Calculate for each commit
        FileInfo projectFileInfo = new FileInfo();
        projectFileInfo.setIsProject();
        //CommitInfo projectComInfo = new CommitInfo();
        List<CommitInfo> projectComInfoList = new ArrayList<CommitInfo>();
        //MetricInfo projectMetricInfo = new MetricInfo();

        Map<Date, List<CommitInfo>> dateListMap = new HashMap<Date, List<CommitInfo>>();
        List<Map.Entry<Date, List<CommitInfo>>> dateListList;


        for (FileInfo f : fileInfoList) {
            MetricInfo comMetricInfo = new MetricInfo();

            for (int cIdx = 0; cIdx < f.getComInfoList().size(); cIdx++) {
                CommitInfo c = f.getComInfoList().get(cIdx);
                ASTVisitorSearch comVisitor = parse(c.getChurn().getcode().toCharArray(), 1);
                comMetricInfo.setByCommitInfo(c);
                comMetricInfo.setByVisitor(comVisitor);
                comMetricInfo.setToCommitInfo(c);

                //Put into dateListMap
                dateListMap.put(c.getDate(), new ArrayList<CommitInfo>());


            }
        }

        //Sort dateListMap to List
        dateListList = new ArrayList<Map.Entry<Date, List<CommitInfo>>>(dateListMap.entrySet());
        Collections.sort(
                dateListList
                ,   new Comparator<Map.Entry<Date, List<CommitInfo>>>() {
                    public int compare(Map.Entry<Date, List<CommitInfo>> a, Map.Entry<Date, List<CommitInfo>> b) {
                        return a.getKey().compareTo(b.getKey());
                    }
                }
        );

        //put each CommitInfo to all proper date's list
        for(FileInfo f : fileInfoList){
            int dIdx = 0;
            for (int cIdx = 0; cIdx < f.getComInfoList().size(); cIdx++){

                CommitInfo curCom = f.getComInfoList().get(cIdx);

                //last element
                if(cIdx == f.getComInfoList().size()-1){
                    Date curDate = f.getComInfoList().get(cIdx).getDate();
                    //put until the last date's list
                    for(; dIdx <dateListList.size() ; dIdx++){
                        if(!dateListList.get(dIdx).getKey().before(curDate)){
                                dateListList.get(dIdx).getValue().add(curCom);
                        }
                        else
                            break;
                    }
                }

                else{
                    Date curDate = f.getComInfoList().get(cIdx).getDate();
                    Date nextDate = f.getComInfoList().get(cIdx+1).getDate();

                    for(; dIdx <dateListList.size() ; dIdx++){
                        //for all date which is curDate<= date < nextDate, put curCom
                        if(!dateListList.get(dIdx).getKey().before(curDate)){
                            if(dateListList.get(dIdx).getKey().before(nextDate)){
                                dateListList.get(dIdx).getValue().add(curCom);
                            }
                            else
                                break;
                        }
                    }
                }
            }
        }
        //Combine metrics stored in each date's list
        for(int dIdx = 0 ; dIdx < dateListList.size(); dIdx++){
            MetricInfo mergedMetricInfo = new MetricInfo();
            CommitInfo mergedComInfo = new CommitInfo();
            for(int cIdx = 0 ; cIdx < dateListList.get(dIdx).getValue().size() ; cIdx++){
                CommitInfo curCom = dateListList.get(dIdx).getValue().get(cIdx);
                if(cIdx == 0)
                    mergedMetricInfo.setByCommitInfo(curCom);
                else{
                    mergedMetricInfo.addByCommitInfo(curCom);
                }

                //Set date, commitHash, and combine Churn
                if(curCom.getDate().equals(dateListList.get(dIdx).getKey())){
                    mergedComInfo.setDate(curCom.getDate());
                    mergedComInfo.setCommitHash(curCom.getCommitHash());
                    mergedMetricInfo.addChurn(curCom.getChurn());
                }
            }

            mergedMetricInfo.setToCommitInfo(mergedComInfo);
            projectComInfoList.add(mergedComInfo);
        }

        projectFileInfo.setComInfoList(projectComInfoList);
        fileInfoList.add(0, projectFileInfo);


        return fileInfoList;
    }


}