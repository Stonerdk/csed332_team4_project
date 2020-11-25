package org.team4.team4_project.metric_calculation;

import java.io.*;
import java.text.ParseException;
import java.util.*;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.*;
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

    public List<HistoryData> mcMain() throws IOException, ParseException {
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
        List<Map<String, String>> commitInfoList = new ArrayList<Map<String, String>>();

        // Get from git
        for (int i = 0; i< 20; i++){
            HashMap<String, String> commitElement = new HashMap<String, String>();
            commitElement.put("cName", "commit "+ i);
            commitElement.put("bName", "branch " + i);
            commitElement.put("cHash", "0123456x" + i);
            commitElement.put("cPlusStr", "bla bla +");
            commitElement.put("cMinusStr", "bla bla -");

            commitInfoList.add(commitElement);
        }

        // Calculate for each commit
        List<HistoryData> historyDataList= new ArrayList<HistoryData>();

        MetricInfo MetricCommit = new MetricInfo();
        for (int cIdx = 0 ; cIdx<commitInfoList.size() ; cIdx++){

            if (cIdx == 0){ //Calculate for the current directory
                String DirName = System.getProperty("user.dir");

                // retrieve all .java files in the directory and subdirectories.
                List<String> JavaFiles = retrieveFiles(DirName);

                // parse files in a directory to list of char array
                List<char[]> FilesRead = ParseFilesInDir(JavaFiles);

                ASTVisitorSearch ASTVisitorFile;

                MetricInfo MetricFile = new MetricInfo();

                for(int i=0; i<FilesRead.size(); i++)
                {
                    //System.out.println("Now, AST parsing for : "+ JavaFiles.get(i));
                    ASTVisitorFile=parse(FilesRead.get(i), 1);
                    MetricCommit.addByVisitor(ASTVisitorFile); //TODO : addByVisitor
                }
            }
            else {
                MetricCommit.addByString("hi");
            }
            //Arguments needed for History Data
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String sDate = "2020-11-" ;
            Date date = format.parse(sDate + cIdx);
            String commitString = commitInfoList.get(cIdx).get("cName");
            String branchName = commitInfoList.get(cIdx).get("bName");
            Map<String, Double> attr = new HashMap<String, Double>();
            attr.put("Halstead Complexity", MetricCommit.getHalsteadVolume());
            attr.put("Cyclomatic Complexity", (double)MetricCommit.getCyclomaticComplexity());
            attr.put("Maintainability", MetricCommit.getMaintainabilityIndex());

            HistoryData historyDataCommit = new HistoryData(date, commitString, branchName, attr);
            historyDataList.add(historyDataCommit);
        }
    return historyDataList;

    }


}