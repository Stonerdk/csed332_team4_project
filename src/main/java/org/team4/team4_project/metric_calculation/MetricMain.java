package org.team4.team4_project.metric_calculation;

import java.io.*;
import java.text.ParseException;
import java.util.*;

import com.google.common.collect.Iterables;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.team4.team4_project.ProjectHandler;
import org.team4.team4_project.git.ChurnResult;
import org.team4.team4_project.git.GitHandler;
import org.team4.team4_project.history.HistoryData;


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
            final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

            // Check for compilationUnits problems in the provided file
            IProblem[] problems = cu.getProblems();
            for(IProblem problem : problems) {
                // Ignore some error because of the different versions.
                if (problem.getID() == 1610613332) 		 // 1610613332 = Syntax error, annotations are only available if source level is 5.0
                    continue;
                else if (problem.getID() == 1610613329) // 1610613329 = Syntax error, parameterized types are only available if source level is 5.0
                    continue;
                else if (problem.getID() == 1610613328) // 1610613328 = Syntax error, 'for each' statements are only available if source level is 5.0
                    continue;
                else if (problem.getID() == 1610612969) // Syntax error on tokens, delete these tokens 	    /Problem ID=1610612969
                    continue;
                else if (problem.getID() == 1610612967) // Syntax error on token "assert", AssignmentOperator expected after this token 	 /Problem ID=1610612967
                    continue;
                else if (problem.getID() == 536871352) // 'assert' should not be used as an identifier, since it is a reserved keyword from source level 1.4	 /Problem ID=536871352
                    continue;
                else if (problem.getID() == 1610612940) //Syntax error on token "=", != expected 	 /Problem ID=1610612940
                    continue;
                else
                {
                    // quit compilation if
                    System.out.println("CompilationUnit problem Message " + problem.getMessage() + " \t At line= "+problem.getSourceLineNumber() + "\t Problem ID="+ problem.getID());

                    System.out.println("The program will quit now!");

                    /** We need to modify **/
                    /**
                     * We are not considering trouble making files!!!!!!!!!!!!!!!!!!!!!!!
                     */
                    //System.exit(1);
                    ASTVisitorSearch tempVisitor = new ASTVisitorSearch();
                    return tempVisitor;
                }
            }
            // visit nodes of the constructed AST
            ASTVisitorSearch visitor= new ASTVisitorSearch();
            cu.accept(visitor);

            //Calculate the number of source, comments (for Cyclomatic complexity)
            visitor.codeLen += (new String(str)).lines().count();

            for (Comment comment : (List<Comment>) cu.getCommentList()) {
                int start = comment.getStartPosition();
                int end = start + comment.getLength();
                String comment_str = (new String(str)).substring(start, end);

                visitor.commentLen += comment_str.lines().count();

                //System.out.println(comment_str);
                //System.out.println(comment_str.lines().count());


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

    public List<FileInfo> mcMain() throws IOException, ParseException, GitAPIException {
        /**
         * Option1. get the Directory name from the user
         */
        /*
        String DirName = null;
        Scanner user_input = new Scanner(System.in);
        System.out.print("Enter Directory Name: ");
        DirName = user_input.next();
        user_input.close();
        System.out.println("Directory Name is: " + DirName);
        */

        /**
         * Option 2. get from Git
         */
        // Get from git
        GitHandler gitHandler = new GitHandler(ProjectHandler.getProjectPath());
        List<FileInfo> fileInfoList = gitHandler.getFileInfo();

        /**
         * Example File Info List
         */
        FileInfo file1 = new FileInfo();
        CommitInfo commit1_1 = new CommitInfo();
        commit1_1.setCommitHash("011");
        commit1_1.setBranchName("master");
        ChurnResult tempCR = new ChurnResult();
        tempCR.setcode("interface Account {\n" +
                "    /**\n" +
                "     * Return the account number\n" +
                "     *\n" +
                "     * @return the account number\n" +
                "     */\n" +
                "    int getAccountNumber();\n" +
                "\n" +
                "    /**\n" +
                "     * Return the current balance of the account\n" +
                "     *\n" +
                "     * @return the balance\n" +
                "     */\n" +
                "    double getBalance();\n" +
                "\n" +
                "    /**\n" +
                "     * Return the name of the owner\n" +
                "     *\n" +
                "     * @return the owner name\n" +
                "     */\n" +
                "    String getOwner();\n" +
                "\n" +
                "    /**\n" +
                "     * Update the balance according to the interest rate and elapsed date.\n" +
                "     *\n" +
                "     * @param elapsedDate\n" +
                "     */\n" +
                "    void updateBalance(int elapsedDate);\n" +
                "\n" +
                "    /**\n" +
                "     * Deposit a given amount of money\n" +
                "     *\n" +
                "     * @param amount of money\n" +
                "     */\n" +
                "    void deposit(double amount);\n" +
                "\n" +
                "    /**\n" +
                "     * Withdraw a given amount of money\n" +
                "     *\n" +
                "     * @param amount of money\n" +
                "     * @throws IllegalOperationException if not possible\n" +
                "     */\n" +
                "    void withdraw(double amount) throws IllegalOperationException;\n" +
                "}");
        commit1_1.setChurn(tempCR);

        CommitInfo commit1_2 = new CommitInfo();
        commit1_2.setCommitHash("012");
        commit1_2.setBranchName("master");
        ChurnResult tempCR2 = new ChurnResult();
        tempCR2.setcode("class LowInterestAccount implements Account {\n" +
                "    //TODO implement this\n" +
                "    private int accountNumber;\n" +
                "    private double balance;\n" +
                "    private String owner;\n" +
                "\n" +
                "    public LowInterestAccount(int aN, double bl, String ow){\n" +
                "        accountNumber = aN;\n" +
                "        balance = bl;\n" +
                "        owner = ow;\n" +
                "    }\n" +
                "\n" +
                "    public int getAccountNumber() {\n" +
                "        //TODO implement this\n" +
                "        return accountNumber;\n" +
                "    }\n" +
                "\n" +
                "    public double getBalance() {\n" +
                "        //TODO implement this\n" +
                "        return balance;\n" +
                "    }\n" +
                "\n" +
                "    public String getOwner() {\n" +
                "        //TODO implement this\n" +
                "        return owner;\n" +
                "    }\n" +
                "\n" +
                "    public void updateBalance(int elapsedDate) {\n" +
                "        //TODO implement this\n" +
                "        for (int i=0 ; i<elapsedDate ; i++)\n" +
                "            balance *= 1.005;\n" +
                "        return;\n" +
                "    }\n" +
                "\n" +
                "    public void deposit(double amount) {\n" +
                "        //TODO implement this\n" +
                "        balance += amount;\n" +
                "        return;\n" +
                "    }\n" +
                "\n" +
                "    public void withdraw(double amount) throws IllegalOperationException {\n" +
                "        //TODO implement this\n" +
                "        balance -= amount;\n" +
                "        return;\n" +
                "    }\n" +
                "}");
        commit1_2.setChurn(tempCR2);
        List<CommitInfo> tempComList = new ArrayList<CommitInfo>();
        tempComList.add(commit1_1);
        tempComList.add(commit1_2);
        file1.setComInfoList(tempComList);
        file1.setFileName("file1");
        file1.setFilePath("project/main/csed/java/file1");

        fileInfoList.add(file1);

        // Calculate for each commit
        FileInfo projectFileInfo = new FileInfo();
        projectFileInfo.setIsProject();
        CommitInfo projectComInfo = new CommitInfo();
        List<CommitInfo> projectComInfoList = new ArrayList<CommitInfo>();
        MetricInfo projectMetricInfo = new MetricInfo();

        for (FileInfo f : fileInfoList) {
            MetricInfo comMetricInfo = new MetricInfo();

            for (CommitInfo c : f.getComInfoList()) {
                ASTVisitorSearch comVisitor = parse(c.getChurn().getcode().toCharArray(), 1);
                comMetricInfo.setByVisitor(comVisitor);
                comMetricInfo.setToCommitInfo(c);

                // Find a proper index to put this commit in ProjectFileInfo's CommitInfoList (by Date)
                for(int i=0 ; i < projectComInfoList.size() ; i++){
                    projectComInfo = projectComInfoList.get(i);
                    if(projectComInfo.getDate().after(c.getDate())){
                        projectComInfoList.add(i, c); //
                        break;
                    }
                    else if (projectComInfo.getDate().equals(c.getDate())){
                        projectMetricInfo.setByCommitInfo(projectComInfo);
                        projectMetricInfo.addByVisitor(comVisitor);
                        CommitInfo mergedComInfo = new CommitInfo();
                        projectMetricInfo.setToCommitInfo(mergedComInfo);
                        projectComInfoList.remove(i);
                        projectComInfoList.add(i, mergedComInfo);
                        break;

                    }
                    else{
                        if (i == projectComInfoList.size()-1) {
                            projectComInfoList.add(c);
                            break;
                        }
                    }
                }
                if(projectComInfoList.isEmpty()) {
                    projectComInfoList.add(c);
                }
            }

        }
        projectFileInfo.setComInfoList(projectComInfoList);
        fileInfoList.add(0, projectFileInfo);

        return fileInfoList;
    }


}