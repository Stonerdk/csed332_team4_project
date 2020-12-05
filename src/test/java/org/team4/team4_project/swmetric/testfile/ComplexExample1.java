package org.team4.team4_project.swmetric.testfile;
import java.util.*;

public class ComplexExample1 {
    public static void main(String[] args)
    {
        List<Integer> l1 = new ArrayList<Integer>();
        l1.add(1);
        l1.add(2);

        System.out.println(l1);

        // Removes element from index 1
        if(l1.contains(1))
            l1.remove(1);
        else if(l1.contains(2))
            l1.remove(2);
        else
            l1.add(3);

        System.out.println(l1);
        System.out.println(addNumbers(10));
    }

    public static int addNumbers(int num) {
        if (num != 0)
            return num + addNumbers(num - 1);
        else
            return num;
    }

}
