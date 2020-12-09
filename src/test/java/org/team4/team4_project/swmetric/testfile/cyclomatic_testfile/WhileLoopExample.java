package org.team4.team4_project.swmetric.testfile.cyclomatic_testfile;


public class WhileLoopExample {
    public static void main(String[] args) {
        int i = 1;
        while(i<10) {
            i++;
        }

        do {
            i--;
        } while (i > 0);
        System.out.println(i);
    }
}
