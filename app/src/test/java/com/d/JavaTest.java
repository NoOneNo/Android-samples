package com.d;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class JavaTest {

    @Test
    public void intToObj() throws IOException {
//        int a = 100;
//        int b = 100;
//        Object c = a;
//        Object d = b;
//        int f = (int)d;
//        Assert.assertTrue(c.equals(f));
//        System.out.println("int:" + (1 << 31));

        File file = new File("/home/harry/tmp/1.log");
        File file_dup = new File("/home/harry/tmp/1.log");

// java.io.FileNotFoundException: /home/harry/tmp/1.log (没有那个文件或目录)
//        System.out.println(new Scanner(file).useDelimiter("#").next());

        OutputStream outputStream = new FileOutputStream(file);
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new FileReader(file);


        outputStream.write("hello file ".getBytes());
        System.out.println(new Scanner(file).useDelimiter("#").next());

        outputStream.write("append ".getBytes());
        System.out.println(new Scanner(file).useDelimiter("#").next());
        Scanner scanner = new Scanner(inputStream);

        file.delete();

        System.out.println(new Scanner(file_dup).useDelimiter("#").next());

        outputStream.write("hello file after delete ".getBytes());
        outputStream.flush();
//        System.out.println(new Scanner(file).useDelimiter("#").next());
    }

    @Test
    public void file2() throws IOException {

        File file3 = new File("/home/harry/tmp/3xxx.log");
        File file4 = new File("/home/harry/tmp/4xxx.log");
//        OutputStream outputStream = new FileOutputStream(file);

//        System.out.println(file3.getPath());

//        File dir = new File("/home/harry/tmp");
//        File filetmp = File.createTempFile("ft1", null, dir);
//        filetmp.mkdir();
//
//        File filetmp2 = File.createTempFile("ft12", null, filetmp);

//        filetmp.deleteOnExit();

//        Files.move(Paths.get(file3.getPath()), Paths.get(file4.getPath()), StandardCopyOption.ATOMIC_MOVE);


//        outputStream.write("hello file\n this is two".getBytes());
//        outputStream.write("hello file".getBytes());

//        file.delete();

//        final InputStream inputStream = new FileInputStream("/home/harry/tmp/3xxx.log");
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                byte[] buffer = new byte[8192];
//                while (true) {
//                    int byteCount = 0;
//                    try {
//                        byteCount = inputStream.read(buffer);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (byteCount == -1) {
//                        System.out.println("EOF");
//                        break;
//                    }
//                    System.out.println("read, " + byteCount + " : \n" + new String(buffer));
//                }
//            }
//        }).start();


    }

    @Test
    public void file3() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override
            public void run() {

                int count = 50;
                while (count>0) {
                    count--;
                    File file3 = new File("/home/harry/tmp/1xxx.log");
                    OutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(file3, true);
                        outputStream.write("hello thread1".getBytes());
                        outputStream.flush();
//                        Thread.sleep(17);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                latch.countDown();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                int count = 50;
                while (count>0) {
                    count--;
                    File file3 = new File("/home/harry/tmp/1xxx.log");
                    OutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(file3, true);
                        outputStream.write("hello thread2".getBytes());
                        outputStream.flush();
//                        Thread.sleep(16);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                latch.countDown();
            }
        }).start();

        latch.await();
    }

    @Test
    public void file4() throws IOException, InterruptedException {


        File file1 = new File("/home/harry/tmp/1.log");

        Assert.assertFalse(file1.exists());

        File file2 = new File("/home/harry/tmp/2.log");
        OutputStream outputStream = new FileOutputStream(file2);

        file2.renameTo(file1);

        Assert.assertTrue(file1.exists());
    }
}
