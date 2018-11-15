package com.d.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.d.R
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.web_browser_layout.*

class WebViewActivity : AppCompatActivity() {

    private var mWebView2Fragment : WebView2Fragment? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        var fragment = supportFragmentManager.findFragmentById(R.id.content_container)
        if (fragment == null) {
            fragment = WebView2Fragment()
            supportFragmentManager.beginTransaction().add(R.id.content_container, fragment).commit()
        }
        mWebView2Fragment = fragment as WebView2Fragment


        web_close.setOnClickListener {
            finish()
        }

        web_back.setOnClickListener{
            mWebView2Fragment?.back()
        }

        web_forward.setOnClickListener{
            mWebView2Fragment?.forword()
        }

        web_url_et.setOnEditorActionListener { v, actionId, event ->
            mWebView2Fragment?.loadurl(web_url_et.text.toString())
            return@setOnEditorActionListener true
        }

        web_url_et.paintFlags =  0

        setSupportActionBar(null)
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
}

class WebView2Fragment : Fragment() {
    var mWebView:MyWebView? = null

    @SuppressLint("InflateParams")
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

            override fun onHideCustomView() {
                (activity as WebViewActivity).onHideCustomView()
            }
        }

//          loadurl("http://www.iqiyi.com")
          loadurl("http://m.youtube.com")
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

        return view
    }


    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    override fun onPause() {
        super.onPause()
        mWebView?.onPause()
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    override fun onResume() {
        mWebView?.onResume()
        super.onResume()
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    override fun onDestroy() {
        mWebView?.destroy()
        mWebView = null
        super.onDestroy()
    }

    fun loadurl(url: String) {
        mWebView?.loadUrl(url)
//        (activity as WebViewActivity).hideKeyBoard()
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
        return super.onConsoleMessage(consoleMessage)
    }
}

class MyWebViewClient(val webView: MyWebView) : android.webkit.WebViewClient() {
    val context = webView.context
    override fun onReceivedError(view: android.webkit.WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onReceivedHttpError(view: android.webkit.WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
        Toast.makeText(context, errorResponse.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onReceivedSslError(view: android.webkit.WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun shouldOverrideUrlLoading(view: android.webkit.WebView?, url: String?): Boolean {
        return webView.shouldOverrideUrlLoading(view, url)
    }

    override fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: Bitmap?) {
        webView.onPageStarted(view, url, favicon)
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
        if(!url.startsWith("www.")&& !url.startsWith("http://") && !url.startsWith("https://")){
            url = "www.$url"
        }
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://$url"
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
}
