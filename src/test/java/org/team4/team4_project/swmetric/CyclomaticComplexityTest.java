package org.team4.team4_project.swmetric;

import static org.junit.Assert.*;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.*;
import org.junit.Assert;
import org.junit.Test;
import org.team4.team4_project.metric_calculation.ASTVisitorSearch;
import org.team4.team4_project.metric_calculation.MetricInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CyclomaticComplexityTest {
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

        return fileData.toString().toCharArray();
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

        if (opt == 1) {
            parser.setKind(ASTParser.K_COMPILATION_UNIT);
            final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

            // Check for compilationUnits problems in the provided file
            IProblem[] problems = cu.getProblems();
            for (IProblem problem : problems) {
                // Ignore some error because of the different versions.
                if (problem.getID() == 1610613332)         // 1610613332 = Syntax error, annotations are only available if source level is 5.0
                    continue;
                else if (problem.getID() == 1610613329) // 1610613329 = Syntax error, parameterized types are only available if source level is 5.0
                    continue;
                else if (problem.getID() == 1610613328) // 1610613328 = Syntax error, 'for each' statements are only available if source level is 5.0
                    continue;
                else {
                    // quit compilation if
                    System.out.println("CompilationUnit problem Message " + problem.getMessage() + " \t At line= " + problem.getSourceLineNumber() + "\t Problem ID=" + problem.getID());

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
            ASTVisitorSearch visitor = new ASTVisitorSearch();
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
        } else if (opt == 2) {
            parser.setKind(ASTParser.K_STATEMENTS);
            final Block cu = (Block) parser.createAST(null);

            // Check for compilationUnits problems in the provided file

            // visit nodes of the constructed AST
            ASTVisitorSearch visitor = new ASTVisitorSearch();
            cu.accept(visitor);

            //Calculate the number of source, comments (for Cyclomatic complexity)
            visitor.codeLen += (new String(str)).lines().count();

            return visitor;
        } else {
            System.out.println("WRONG PARSING OPTION INPUT");
            return null;
        }

    }

    @Test
    public void testIfElse() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\IfElseExample.java";
        char[] fileread = ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = parse(fileread, 2);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(7, metricInfo.getCyclomaticComplexity());
    }

    @Test
    public void testForAndWhile() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\ForWhileExample.java";
        char[] fileread = ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = parse(fileread, 2);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(3, metricInfo.getCyclomaticComplexity());
    }

    @Test
    public void testAssert() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\AssertExample.java";
        char[] fileread = ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = parse(fileread, 2);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(1, metricInfo.getCyclomaticComplexity());
    }

    @Test
    public void testSwitchCase() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\SwitchCaseExample.java";
        char[] fileread = ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = parse(fileread, 2);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(3, metricInfo.getCyclomaticComplexity());
    }
    @Test
    public void testTryCatch() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\TryCatchExample.java";
        char[] fileread = ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = parse(fileread, 2);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(2, metricInfo.getCyclomaticComplexity());
    }

    @Test
    public void testConditionOperator() throws IOException {
        String filename = null;
        filename = "src\\test\\java\\org\\team4\\team4_project\\swmetric\\testfile\\ConditionOperatorExample.java";
        char[] fileread = ReadFileToCharArray(filename);
        ASTVisitorSearch ASTVisitorFile;
        ASTVisitorFile = parse(fileread, 2);

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setByVisitor(ASTVisitorFile);

        //System.out.println("Cyclometic complexity in this .java file = " + metricInfo.getCyclomaticComplexity());
        Assert.assertEquals(3, metricInfo.getCyclomaticComplexity());
    }



}
