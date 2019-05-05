package com.d;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArrCurrTest {

    @Test
    public void curr() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                forArry();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                forArry();
            }
        }).start();

        arr.add("hi");
        arr.add("hello");
        arr.add("hi");
        arr.add("hello");

        arr.remove("hello");
        arr.remove("hello");
        arr.remove("hello");

        new Thread(new Runnable() {
            @Override
            public void run() {
                forArry();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                forArry();
            }
        }).start();

        Thread.sleep(1000);
    }

    @Test
    public void sort() {
        List<Integer> styles = new ArrayList<>();
        styles.add(3);
        styles.add(1);
        styles.add(2);


        System.out.print(styles);

        Collections.sort(styles, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return (int) (o1 - o2);
            }
        });

        System.out.print(styles);


        String[] subSelectorPlains = "asdhasd asdasd".trim().split("\\s+");
        System.out.print(subSelectorPlains.length);
        System.out.print("asdhasd asdasd".matches(".*\\s+.*"));
    }

    ArrayList<String> arr = new ArrayList<>(3);

    {
        arr.add("hi");
        arr.add("hello");
        arr.add("hi");
        arr.add("hello");
        arr.add("hi");
        arr.add("hello");
        arr.add("hi");
        arr.add("hello");
        arr.add("hi");
        arr.add("hello");
        arr.add("hi");
        arr.add("hello");
    }

    public void forArry() {
        for (String v:arr) {
            System.out.print(v);
        }
    }
}
