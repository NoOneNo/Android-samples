package com.d.webview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.JavascriptInterface
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.collections.ArrayList

interface JsAsyncBridge {
    fun onReceive(key:String, bytes: Any)
    fun onRelease(key:String)
    fun addDataListener(listener:DataListener)

    interface DataListener {
        fun onReceive(objects: Any)
        fun getKey():String
    }
}

class JsAsyncBridgeImpl: JsAsyncBridge {
    var inited = false
    var mDataListeners = ArrayList<JsAsyncBridge.DataListener>()
    var mKlServer:KlServer? = null
    val mHandler = Handler(Looper.getMainLooper())

    override fun addDataListener(listener: JsAsyncBridge.DataListener) {
        mDataListeners.add(listener)
    }

    override fun onReceive(key: String, bytes: Any) {
        for (listener in mDataListeners) {
            if (key == listener.getKey()) {
                mHandler.post {
                    listener.onReceive(bytes)
                }
                mDataListeners.remove(listener)
                return
            }
        }
    }

    override fun onRelease(key: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @JavascriptInterface
    fun getServerUrl():String {
        return mKlServer!!.getServerUrl()
    }

    @JavascriptInterface
    fun init():Boolean {
        if (inited) {
            return true
        }

        mKlServer = object : KlServer() {
            override fun route(request: KlRequest): KlResponse {
                onReceive(request.path, request.body)
                return KlResponse(200, "success".toByteArray())
            }
        };
        mKlServer!!.start()
        return true
    }

    @JavascriptInterface
    fun destory():Boolean {
        return true
    }
}

open class KlServer : Runnable{
    private var serverSocket:ServerSocket
    private var port = 7070

    init {
        while (true) {
            try {
                serverSocket = ServerSocket(port) // TODO un bind port
                break
            } catch (e:java.net.BindException) {
                Log.i("chromium", "Address already in use", e)
                port++
            }
        }
    }

    fun getServerUrl():String {
        return "http://127.0.0.1:$port/"
    }

    override fun run() {
        while (true) {
            val socket = serverSocket.accept()
            performRequest(socket)
        }
    }

    private fun performRequest(socket: Socket) {
        Log.e("chromium", "receive response")
        val inputStream = socket.getInputStream() // mark/reset not support SocketInputStream
        val reader = DataInputStream(inputStream)
        // val bufferedInputStream = BufferedInputStream(inputStream)

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

        val contentLength = Integer.parseInt(headers["Content-Length"])
        Log.e("chromium", "request contentLength: $contentLength")

        var body: Any? = null
        val contentType = headers["Content-Type"]
        if (contentType != null) {
            if (contentType.startsWith("image")) { // TODO image type not support
                val bitmap = BitmapFactory.decodeStream(reader)
                body = bitmap
            } else if (contentType.startsWith("text")) { // TODO char length
                val bodyBuilder = StringBuilder()
                for (i in 0 until contentLength) {
                    bodyBuilder.append(reader.read().toChar()) // notice: POST reader.readLine will block
                }
                body = bodyBuilder.toString().toByteArray()
            }
        } else {
            body = ByteArray(contentLength)
            reader.read(body)
        }

        val request = KlRequest(requestLine, headers, body!!)

        val response = route(request)

        val outputStream = socket.getOutputStream()
        val writer = PrintWriter(outputStream)
        val bufferedOutputStream = BufferedOutputStream(outputStream)

        writer.println("HTTP/1.1 200 OK")
        writer.println("Server: APP HTTP Server")
        writer.println()
        writer.flush()

        bufferedOutputStream.write(response.body)
        bufferedOutputStream.flush()

        writer.close()
        bufferedOutputStream.close() // TODO safe close
        socket.close()

        Log.e("chromium", "send response")
    }

    open fun route(request: KlRequest):KlResponse {
        return KlResponse(200, "success".toByteArray())
    }

    fun start() {
        val thread = Thread(this)
        thread.start()
    }
}

interface KlRouter {
    fun perform(request: KlRequest):KlResponse
}

class KlRequest(requestLine:String, headers:HashMap<String, String>, var body:Any) {
    private var httpMethod:String
    var path:String

    init {
        val token = StringTokenizer(requestLine)
        httpMethod = token.nextToken().toUpperCase() // "GET" or "POST"
        path = token.nextToken()
    }
}

class KlResponse(code:Int, var body:ByteArray) {}
