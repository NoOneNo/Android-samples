package com.d.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import android.widget.FrameLayout
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
    }

    fun setUrl(url:String?) {
        web_url_et.setText(url)
        content_container.requestFocus()
    }

    fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
    }
}

class WebView2Fragment : Fragment() {
    var mWebView:WebView? = null

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.web_browser_layout, null)

        mWebView?.destroy()

        mWebView = object : WebView(context) {
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

        loadurl("http://www.jandan.net")
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
        (activity as WebViewActivity).hideKeyBoard()
    }

    fun forword() {
        mWebView?.goForward()
    }

    fun back() {
        mWebView?.goBack()
    }

    fun onPageStarted(url: String?) {
        (activity as WebViewActivity).setUrl(url)
    }
}



@SuppressLint("SetJavaScriptEnabled")
open class WebView(context:Context?) : android.webkit.WebView(context) {


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


        webViewClient = object : WebViewClient() {
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
                return this@WebView.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: Bitmap?) {
                this@WebView.onPageStarted(view, url, favicon)
            }
        }
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: android.webkit.WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                this@WebView.onProgressChanged(view, newProgress)
            }
        }


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