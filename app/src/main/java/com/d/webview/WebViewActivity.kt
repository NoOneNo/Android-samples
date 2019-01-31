package com.d.webview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.d.R
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.web_browser_layout.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class WebViewActivity : AppCompatActivity() {

    private var mWebView2Fragment : WebView2Fragment? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        setSupportActionBar(null)

        var fragment = supportFragmentManager.findFragmentById(R.id.content_container)
        if (fragment == null) {
            fragment = WebView2Fragment()
            supportFragmentManager.beginTransaction().add(R.id.content_container, fragment).commit()
        }
        mWebView2Fragment = fragment as WebView2Fragment

        web_url_et.paintFlags =  0
        web_url_et.setOnEditorActionListener { v, actionId, event ->
            mWebView2Fragment?.loadurl(web_url_et.text.toString())
            return@setOnEditorActionListener true
        }

        menu.setOnClickListener {

        }


    }

    fun setUrl(url:String?) {
        web_url_et.setText(url)
        web_url_et.clearFocus()
    }

    fun onShowCustomView(view: View?, callback: WebChromeClient.CustomViewCallback?) {
        full_screen.addView(view)
        full_screen.visibility = View.VISIBLE
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    fun onHideCustomView() {
        full_screen.visibility = View.GONE
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onBackPressed() {
        if (mWebView2Fragment?.canback()!!) {
            mWebView2Fragment?.back()
            return
        }
        super.onBackPressed()
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun asyncTest(webView: MyWebView) {
        val jss1 = "var s = \"hello\";"
        val jss = "for(i in [1,2,3,4,5,6,7,8,9]) {console.log(s + i)};"
        // val jss = "new Worker(window.URL.createObjectURL(new Blob([\"onmessage = function(s) {for(i in [1,2,3,4,5,6,7,8,9]) {console.log(s + i)};}\"], { type: 'text/javascript' }))).postMessage(s);"
        webView.evaluateJavascript(jss1, null)
        for (i in arrayOf(1,2,3,4,5)) {
            webView.evaluateJavascript(jss, null)
        }
    }

}

class WebView2Fragment : Fragment() {
    var mWebView:MyWebView? = null
    var mJsAsyncBridge = JsAsyncBridgeImpl()

    @SuppressLint("InflateParams", "JavascriptInterface")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.web_browser_layout, null)

        mWebView?.destroy()

        mWebView = object : MyWebView(context) {
            override fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: Bitmap?) {
                this@WebView2Fragment.onPageStarted(url)
            }

            override fun onProgressChanged(view: android.webkit.WebView?, newProgress: Int) {
                web_progress.progress = progress
                if (progress == 100) {
                    web_progress.visibility = View.GONE
                } else {
                    web_progress.visibility = View.VISIBLE
                }
            }
        }

        view.findViewById<FrameLayout>(R.id.web_view_container2).addView(mWebView)

        (mWebView as MyWebView).webChromeClient = object : MyWebChromeClient(mWebView as MyWebView) {
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                (activity as WebViewActivity).onShowCustomView(view, callback)
            }

            override fun onShowCustomView(view: View?, requestedOrientation: Int, callback: CustomViewCallback?) {
                (activity as WebViewActivity).onShowCustomView(view, callback)
            }

            override fun onHideCustomView() {
                (activity as WebViewActivity).onHideCustomView()
            }
        }

        (mWebView as MyWebView).addJavascriptInterface(object {
            @JavascriptInterface
            fun getAppData(s:String):String {
                return s
            }
        }, "AndroidApp")

        mJsAsyncBridge.init()
        (mWebView as MyWebView).addJavascriptInterface(mJsAsyncBridge, "JsAsyncBridge")

        mJsAsyncBridge.addDataListener(object :JsAsyncBridge.DataListener {
            override fun getKey(): String {
                return "image"
            }

            override fun onReceive(objects: Any) {
                val imageView = ImageView(activity)
                imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                imageView.setImageBitmap(objects as Bitmap)
                val dialog = android.app.AlertDialog.Builder(activity).setView(imageView)
                dialog.show()

//                saveBitmap(bitmap)

                mJsAsyncBridge.addDataListener(this)
            }
        })

        mJsAsyncBridge.addDataListener(object :JsAsyncBridge.DataListener {
            override fun getKey(): String {
                return "base64image"
            }

            override fun onReceive(objects: Any) {
                val s = objects as String
                Log.e("debug", "start: " + s.subSequence(0, 30))
                Log.e("debug", "end: " + s.subSequence(s.length - 30, s.length))

                val newobjects = Base64.decode(objects, Base64.DEFAULT)

                val bitmap = BitmapFactory.decodeByteArray(newobjects, 0, newobjects.size) // TODO decode stream

                val imageView = ImageView(activity)
                imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                imageView.setImageBitmap(bitmap)
                val dialog = android.app.AlertDialog.Builder(activity).setView(imageView)
                dialog.show()

                mJsAsyncBridge.addDataListener(this)
            }
        })

        mJsAsyncBridge.addDataListener(object :JsAsyncBridge.DataListener {
            override fun getKey(): String {
                return "textImage"
            }

            override fun onReceive(objects: Any) {
                val s = objects as String
                Log.e("debug", "start: " + s.subSequence(0, 30))
                Log.e("debug", "end: " + s.subSequence(s.length - 30, s.length))

                val newobjects = Base64.decode(objects, Base64.DEFAULT)

                val bitmap = BitmapFactory.decodeByteArray(newobjects, 0, newobjects.size) // TODO decode stream

                val imageView = ImageView(activity)
                imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                imageView.setImageBitmap(bitmap)
                val dialog = android.app.AlertDialog.Builder(activity).setView(imageView)
                dialog.show()

                mJsAsyncBridge.addDataListener(this)
            }
        })

        mJsAsyncBridge.addDataListener(object :JsAsyncBridge.DataListener {
            override fun getKey(): String {
                return "text"
            }

            override fun onReceive(objects: Any) {
                val str = objects as String
                Log.e("chromium", "mJsAsyncBridge#HTTP#String: " + str.length)
                mJsAsyncBridge.addDataListener(this)
            }
        })

        initUrl()

        return view
    }



    fun saveBitmap(bitmap: Bitmap) {
        val now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".png"

            val imageFile = File(mPath)


            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
            outputStream.flush()
            outputStream.close()

            Log.e("debug", "compress PNG to outputStream")

        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }
    }

    private fun initUrl() {
        loadurl("file:///android_asset/runtime.html")
//        loadurl("http://127.0.0.1:7070/post/")
//          loadurl("http://www.iqiyi.com")
//          loadurl("http://m.youtube.com")
//        loadurl("http://www.bilibili.com")
//        loadurl("http://i.jandan.net/top")
//        loadurl("http://wapv.sogou.com/teleplay/orswyzlqnrqxsxzwgi3denztbhol7t5lwsvq.html")
//        loadurl("https://www.html5test.com")

//                loadUrl("https://account.xiaomi.com/oauth2/authorize" +
//                        "?skip_confirm=false" +
//                        "&response_type=code" +
//                        "&redirect_uri=http%3A%2F%2Fpassport.iqiyi.com%2Fapis%2Fthirdparty%2Fncallback.action%3Ffrom%3D30" +
//                        "&state=d6f72a229f0bae6f16a3228f1ef2dce0" +
//                        "&client_id=2882303761517310776")
    }


    override fun onPause() {
        super.onPause()
        mWebView?.onPause()
    }

    override fun onResume() {
        mWebView?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mWebView?.destroy()
        mWebView = null
        super.onDestroy()
    }

    fun loadurl(url: String) {
        mWebView?.loadUrl(url)
    }

    fun forword() {
        mWebView?.goForward()
    }

    fun back() {
        mWebView?.goBack()
    }

    fun canback():Boolean {
        return mWebView?.canGoBack()!!
    }

    fun onPageStarted(url: String?) {
        (activity as WebViewActivity).setUrl(url)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showBigTh(context: Context, msg: String) {
        val dialog = AlertDialog.Builder(context, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen).setMessage(msg).create()
        dialog.show()
        (dialog.findViewById<View>(android.R.id.message) as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
        (dialog.findViewById<View>(android.R.id.message) as TextView).setTextIsSelectable(true)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showBigTh(context: Context, view:View):AlertDialog {
        val dialog = AlertDialog.Builder(context, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
                .setView(view)
                .create()
        dialog.show()
        return dialog
    }
}


open class MyWebChromeClient(val webView: MyWebView) : android.webkit.WebChromeClient() {
    override fun onProgressChanged(view: android.webkit.WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        webView.onProgressChanged(view, newProgress)
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
//        Toast.makeText(webView.context, "ConsoleMessage: " + consoleMessage?.message() + " -- From line "
//                + consoleMessage?.lineNumber() + " of "
//                + consoleMessage?.sourceId(), Toast.LENGTH_SHORT).show()
//        Log.e("console", "ConsoleMessage: " + consoleMessage?.message())
        return super.onConsoleMessage(consoleMessage)
    }
}

class MyWebViewClient(val webView: MyWebView) : android.webkit.WebViewClient() {
    val context = webView.context
    override fun onReceivedError(view: android.webkit.WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
//        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
        Log.e("MyWebViewClient", "onReceivedError: " + error.toString())
    }

    override fun onReceivedHttpError(view: android.webkit.WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
//        Toast.makeText(context, errorResponse.toString(), Toast.LENGTH_SHORT).show()
        Log.e("MyWebViewClient", "onReceivedError: " + errorResponse.toString())
    }

    override fun onReceivedSslError(view: android.webkit.WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
//        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
        Log.e("MyWebViewClient", "onReceivedError: " + error.toString())
    }

    override fun shouldOverrideUrlLoading(view: android.webkit.WebView?, url: String?): Boolean {
        return webView.shouldOverrideUrlLoading(view, url)
    }

    override fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: Bitmap?) {
        webView.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        initPort()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun initPort() {
        val channelports = webView.createWebMessageChannel()
        Log.e("chromium", "setWebMessageCallback#channelports: " + channelports.size)
        val port = channelports[0]
        port.setWebMessageCallback(object : WebMessagePort.WebMessageCallback() {
            override fun onMessage(port: WebMessagePort?, message: WebMessage?) {
                val str = message!!.data as String
                Log.e("chromium", "setWebMessageCallback#String: " + str.length)

                port!!.postMessage(WebMessage("success"))

//                 val newobjects = Base64.decode(message!!.data, Base64.DEFAULT)
//
//                 val bitmap = BitmapFactory.decodeByteArray(newobjects, 0, newobjects.size) // TODO decode stream
//
//                 val imageView = ImageView(activity)
//                 imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
//                 imageView.setImageBitmap(bitmap)
//                 val dialog = android.app.AlertDialog.Builder(activity).setView(imageView)
//                 dialog.show()
            }
        })

        webView.postWebMessage(WebMessage("", arrayOf(channelports[1])), Uri.EMPTY)
    }

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        val res = super.shouldInterceptRequest(view, request)
        return res
    }
}

@SuppressLint("SetJavaScriptEnabled")
open class MyWebView(context:Context?) : android.webkit.WebView(context) {
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true)
        }

        settings.javaScriptEnabled = true

        // this is useful
        this.clearCache(true)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.setAppCacheEnabled(false)

        settings.setSupportZoom(true)
        settings.builtInZoomControls = true

        // ?
        settings.domStorageEnabled = true
        settings.allowUniversalAccessFromFileURLs = true


        webViewClient = MyWebViewClient(this)
        webChromeClient = MyWebChromeClient(this)

    }

    override fun loadUrl(urlIn: String) {
        var url = urlIn
        if (!url.startsWith("file")) {
            if(!url.startsWith("www.")&& !url.startsWith("http://") && !url.startsWith("https://")){
                url = "www.$url"
            }
            if(!url.startsWith("http://") && !url.startsWith("https://")){
                url = "http://$url"
            }
        }

        super.loadUrl(url)
    }

    open fun shouldOverrideUrlLoading(view: android.webkit.WebView?, url: String?): Boolean {
        return false
    }

    open fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: Bitmap?) {

    }

    open fun onProgressChanged(view: android.webkit.WebView?, newProgress: Int) {

    }

    override fun postUrl(url: String?, postData: ByteArray?) {
        super.postUrl(url, postData)
    }
}
