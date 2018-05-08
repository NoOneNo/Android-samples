package com.d.io;

import java.io.File;
import java.io.IOException;

import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class IO {
    public void read() {

    }

    public void readLines(File file) throws IOException {
        try (Source fileSource = Okio.source(file);
             BufferedSource bufferedSource = Okio.buffer(fileSource)) {

            while (true) {
                String line = bufferedSource.readUtf8Line();
                if (line == null) break;

                if (line.contains("square")) {
                    System.out.println(line);
                }
            }

        }
    }
}
