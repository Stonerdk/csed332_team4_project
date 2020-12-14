package org.team4.team4_project.swmetric.testfile;

public class methodnum_testfile {
    int a;

    private void method() {
        System.out.println(1);
    }

    public void seta(int input) {
        a = input;
    }
    public int geta() {
        return a;
    }

    public void main(String[] args) {
        method();

        seta(5);
        System.out.println(geta());
    }
}
