package org.team4.team4_project.git;

import static org.junit.Assert.*;
import java.io.File;
import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;
import org.team4.team4_project.metric_calculation.CommitInfo;
import org.team4.team4_project.metric_calculation.FileInfo;

public class GitCodeChurnTest {

    @Test
    public void Test() throws Exception {
        GitHandler gitHandler = new GitHandler("datatest/homework6");
        List<FileInfo> result = gitHandler.getFileInfo();
        System.out.println("Success");
    }

    @Test
    public void CodeChurnTest() throws Exception{
        String testFilePath = "datatest/homework0";
        GitHandler gitHandler = new GitHandler(testFilePath);
        Repository repo = gitHandler.getRepository();
        List<CommitInfo> result = new CodeChurn(repo).addPath("README.md").calc();
        assertEquals(5,result.get(result.size()-1).getChurn().getLinesAdded());
        assertEquals(1,result.get(0).getChurn().getLinesAdded());
        //assertEquals(1,result.getLinesDeleted());
    }

    @Test
    public void CodeCompleteTest() throws Exception{
        String testFilePath = "datatest/homework2";
        GitHandler gitHandler = new GitHandler(testFilePath);
        Repository repo = gitHandler.getRepository();
        List<CommitInfo> result = new CodeChurn(repo).addPath("problem1/src/main/java/edu/postech/csed332/homework2/Book.java").calc();
        String tempResult = "package edu.postech.csed332.homework2;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.util.Arrays;\n" +
                "import java.util.ArrayList;\n" +
                "import org.json.simple.JSONObject;\n" +
                "import org.json.simple.parser.JSONParser;\n" +
                "import org.json.simple.parser.ParseException;\n" +
                "import org.json.simple.JSONArray;\n" +
                "\n" +
                "/**\n" +
                " * A book contains the title and the author(s), each of which is\n" +
                " * represented as a string. A book can be exported to and import\n" +
                " * from a string representation in JSON (https://www.json.org/).\n" +
                " * Using the string, you should be able to construct a book object.\n" +
                " */\n" +
                "public final class Book extends Element {\n" +
                "    private String title;\n" +
                "    private List<String> authors;\n" +
                "\n" +
                "    /**\n" +
                "     * Builds a book with the given title and author(s).\n" +
                "     *\n" +
                "     * @param title   the title of the book\n" +
                "     * @param authors the author(s) of the book\n" +
                "     */\n" +
                "    public Book(String title, List<String> authors) {\n" +
                "        this.title = title;\n" +
                "        this.authors = authors;\n" +
                "        // TODO write more code if necessary\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Builds a book from the string representation in JSON, which\n" +
                "     * contains the title and author(s) of the book.\n" +
                "     *\n" +
                "     * @param stringRepr the JSON string representation\n" +
                "     */\n" +
                "    public Book(String stringRepr) {\n" +
                "        // TODO implement this\n" +
                "        JSONParser parser = new JSONParser();\n" +
                "        JSONObject json = null;\n" +
                "        try {\n" +
                "            json = (JSONObject) parser.parse(stringRepr);\n" +
                "        } catch (ParseException e){\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        this.title = (String)json.get(\"title\");\n" +
                "        JSONArray arr = (JSONArray)json.get(\"authors\");\n" +
                "        List<String> authors = new ArrayList<String>();\n" +
                "        for(int i=0; i<arr.size(); i++){\n" +
                "            authors.add((String)arr.get(i));\n" +
                "        }\n" +
                "        this.authors = authors;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Returns the JSON string representation of this book. The string\n" +
                "     * representation contains the title and author(s) of the book.\n" +
                "     *\n" +
                "     * @return the string representation\n" +
                "     */\n" +
                "    public String getStringRepresentation() {\n" +
                "        // TODO implement this\n" +
                "        JSONObject json = new JSONObject();\n" +
                "        json.put(\"title\",this.title);\n" +
                "        JSONArray arr = new JSONArray();\n" +
                "        for (int i=0; i<this.authors.size();i++) {\n" +
                "            arr.add(this.authors.get(i));\n" +
                "        }\n" +
                "        json.put(\"authors\",arr);\n" +
                "        return json.toJSONString();\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Returns all the collections that this book belongs to directly\n" +
                "     * and indirectly, starting from the bottom-level collection.\n" +
                "     * <p>\n" +
                "     * For example, suppose that \"Computer Science\" is a top collection,\n" +
                "     * \"Operating Systems\" is a collection under \"Computer Science\", and\n" +
                "     * \"Linux Kernel\" is a book under \"Operating System\". Then, this\n" +
                "     * method for \"The Linux Kernel\" returns the list of the collections\n" +
                "     * (Operating System, Computer Science), starting from the bottom.\n" +
                "     *\n" +
                "     * @return the list of collections\n" +
                "     */\n" +
                "    public List<Collection> getContainingCollections() {\n" +
                "        // TODO implement this\n" +
                "        List<Collection> ans = new ArrayList<Collection>();\n" +
                "        Collection temp = getParentCollection();\n" +
                "        if(temp!=null){\n" +
                "            while(temp!=null){\n" +
                "                ans.add(temp);\n" +
                "                temp = temp.getParentCollection();\n" +
                "            }\n" +
                "        }\n" +
                "        return ans;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Returns the title of the book.\n" +
                "     *\n" +
                "     * @return the title\n" +
                "     */\n" +
                "    public String getTitle() {\n" +
                "        return title;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Returns the author(s) of the book.\n" +
                "     *\n" +
                "     * @return the author(s)\n" +
                "     */\n" +
                "    public List<String> getAuthors() {\n" +
                "        return authors;\n" +
                "    }\n" +
                "}";
            assertEquals(tempResult,result.get(0).getChurn().getcode());
    }

}
