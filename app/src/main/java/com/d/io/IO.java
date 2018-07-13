package com.d.io;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;

import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class IO {
    public void read() {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
