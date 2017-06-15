package test;


import java.util.ArrayList;

public class CloneTest {
    public static void main(String... args) {
        ArrayList<Integer> testlist = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            testlist.add(i);
        }

        long startTime = System.currentTimeMillis();
        ArrayList copyManual = new ArrayList();
        for (Object i:testlist) {
            copyManual.add(i);
        }
        long manualTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        ArrayList<Integer> addAll = new ArrayList<>();
        addAll.addAll(testlist);
        long addAllTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        ArrayList clone = (ArrayList) testlist.clone();
        long cloneTime = System.currentTimeMillis() - startTime;

        System.out.println("manual: "+manualTime);
        System.out.println("addAll: "+addAllTime);
        System.out.println("clone: "+cloneTime);
    }
}
