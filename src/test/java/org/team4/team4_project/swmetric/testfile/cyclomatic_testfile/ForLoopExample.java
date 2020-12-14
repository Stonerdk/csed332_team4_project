package org.team4.team4_project.swmetric.testfile.cyclomatic_testfile;


public class ForLoopExample {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j <10; j++) {
                System.out.println(j);
            }
            sum += i;
        }
        for (int i = 0; i < 10; i++) {
            sum += i;
        }
    }
}
