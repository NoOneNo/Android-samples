package com.d.webview

import android.annotation.TargetApi
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receiveText
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.collections.HashMap


class KtorService : Service() {
    override fun onBind(intent: Intent?): IBinder {
        Log.e("KtorFetchService", "onBind")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val HTTP_PORT = 7070
    override fun onCreate() {
        Log.e("KtorFetchService", "onCreate")

        KlServer().start()

        super.onCreate()
    }

    fun ktor() {
        embeddedServer(Netty, HTTP_PORT) {
            routing {
                post("/") {
                    val text: String = call.receiveText()
                    Log.e("chromium", "text size: " + utf8Length(text))
                    call.respondText(text, ContentType.Text.Html)
                }
            }
            //}.start(wait = true)
        }.start(wait = false)
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun utf8Length(cs: CharSequence): Int {
        return cs.codePoints()
                .map { cp -> if (cp <= 0x7ff) if (cp <= 0x7f) 1 else 2 else if (cp <= 0xffff) 3 else 4 }
                .sum()
    }
}


/**
 *

POST / HTTP/1.1
Host: 127.0.0.1:7070
Content-Type: text/plain
Cache-Control: no-cache
Postman-Token: 4505ae34-290f-93fc-e149-8745d1922c5c

hello
 *
 * */

class KlServer : Runnable{
    private val serverSocket = ServerSocket(7070) // TODO what if port is in using

    override fun run() {
        while (true) {
            val socket = serverSocket.accept()
            performRequest(socket)
        }
    }

    private fun performRequest(socket: Socket) {
        val inputStream = socket.getInputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))

        val requestLine = reader.readLine()

        val headers = HashMap<String, String>()
        var header = reader.readLine()
        while (header.isNotEmpty()) {
            val idx = header.indexOf(":")
            val key = header.substring(0, idx).trim()
            val value = header.substring(idx + 1, header.length).trim()
            headers[key] = value
            header = reader.readLine()
        }

        val bodyBuilder = StringBuilder()
        val contentLength = Integer.parseInt(headers["Content-Length"])
        for (i in 0 until contentLength) {
            bodyBuilder.append(reader.read().toChar()) // notice: POST reader.readLine will block
        }
        val body = bodyBuilder.toString()

        val request = KlRequest(requestLine, headers, body)

        val response = route(request)

        val outputStream = socket.getOutputStream()
        val writer = PrintWriter(outputStream)
        val bodyBOS = PrintWriter(outputStream)

        writer.println("HTTP/1.1 200 OK")
        writer.println("Server: APP HTTP Server")
        writer.println()
        writer.flush()

        bodyBOS.write(response.body)
        bodyBOS.flush()

        writer.close()
        bodyBOS.close()
        socket.close()

        Log.e("chromium", "send response")
    }

    fun route(request: KlRequest):KlResponse {
        return KlResponse(200, request.body)
    }

    fun start() {
        val thread = Thread(this)
        thread.start()
    }
}

interface KlRouter {
    fun perform(request: KlRequest):KlResponse
}

class KlRequest(requestLine:String, headers:HashMap<String, String>, var body:String) {
    private var httpMethod:String

    init {
        val token = StringTokenizer(requestLine)
        httpMethod = token.nextToken().toUpperCase() // "GET" or "POST"
    }
}

class KlResponse(code:Int, var body:String) {}

