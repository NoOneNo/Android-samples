package com.d.io

import okio.*
import okio.Okio
import okio.BufferedSource
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


// ByteString & String
val bs = ByteString.of("".toByte())
val s = String()

// Buffers
val bf = Buffer()

// Sources & InputStream & InputStreamReader
//  BufferedSource and BufferedSink
val source = object :Source{
    override fun timeout(): Timeout {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun read(sink: Buffer?, byteCount: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}





@Throws(IOException::class)
fun readLines(file: File) {
    Okio.source(file).use({ fileSource ->
        Okio.buffer(fileSource).use({ bufferedSource ->

            while (true) {
                val line = bufferedSource.readUtf8Line() ?: break

                if (line.contains("square")) {
                    println(line)
                }
            }

        })
    })
}

fun main() {

    var inputstream: InputStream = FileInputStream("c:\\data\\input-text.txt")
    inputstream.read()

}


// Sinks & OutputStream
val sink = object :Sink {
    override fun timeout(): Timeout {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun write(source: Buffer?, byteCount: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun flush() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}