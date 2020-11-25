package org.team4.team4_project.swmetric.testfile;

public class IfElseExample {
    public static void main(String[] args) {
        int a = 25, b = 15, c = 30, d = 20;
        int min;
        if (a < b && a < c && a < d) {
            min = a;
        } else if (b < c && b < d) {
            min = b;
        } else if (c < d) {
            min = c;
        } else {
            min = d;
        }
        System.out.println(min);
    }
}
