package org.team4.team4_project.swmetric.testfile.cyclomatic_testfile;


import java.util.*;

public class ComprehensionExample {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        Set<Integer> set1 = new HashSet<>();
        Map<Integer, String> map1 = new HashMap<>();
        //System.out.println(arr.size());
        list1.add(1);
        set1.add(1);
        map1.put(1, "string");

        if (1 == 1)
            System.out.println(1);

        assert true;
    }
}