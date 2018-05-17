package com.d;

import org.junit.Test;

import java.util.ArrayList;

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
