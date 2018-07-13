package com.d.io

import okio.*
import okio.Okio
import okio.BufferedSource
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.channels.Selector


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


fun nio() {

    // socket.accept()、socket.read()、socket.write() 是阻塞的

    // NIO的主要事件有几个：读就绪、写就绪、有新连接到来


    // select是阻塞的为了获得nio事件, 底层是操作系统的select, poll, epoll
    // 所以一般还是需要一个循环操作
    // Reactor模式
    Selector.open().select()


}