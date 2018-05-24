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
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import android.widget.Toast
import com.d.R
import kotlinx.android.synthetic.main.fg_webview.*
import kotlinx.android.synthetic.main.web_browser_layout.*

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        supportFragmentManager.beginTransaction().add(android.R.id.content, WebView2Fragment()).commit()
    }
}

class WebViewFragment : Fragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fg_webview, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webview = object : WebView(context) {
            override fun shouldOverrideUrlLoading(view: android.webkit.WebView?, url: String?): Boolean {
                floating_search_view.setSearchText(url)
                return false
            }
        }
        web_view_container.addView(webview)
        // https://html5test.com
        webview.loadUrl("https://account.xiaomi.com/oauth2/authorize" +
                "?skip_confirm=false" +
                "&response_type=code" +
                "&redirect_uri=http%3A%2F%2Fpassport.iqiyi.com%2Fapis%2Fthirdparty%2Fncallback.action%3Ffrom%3D30" +
                "&state=d6f72a229f0bae6f16a3228f1ef2dce0" +
                "&client_id=2882303761517310776")

        floating_search_view.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                webview.loadUrl(floating_search_view.query)
            }
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            Toast.makeText(context, it.title.toString(), Toast.LENGTH_SHORT).show()

            when {
                it.itemId == R.id.load -> webview.loadUrl(floating_search_view.query)
                it.itemId == R.id.back -> webview.goBack()
                it.itemId == R.id.forward -> webview.goForward()
            }

            return@setOnNavigationItemSelectedListener true
        }
    }
}

class WebView2Fragment : Fragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.web_browser_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webview = object : WebView(context) {
            override fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: Bitmap?) {
                web_url_et.setText(url)
            }
        }

        web_view_container2.addView(webview)

        // https://html5test.com
        webview.loadUrl("https://account.xiaomi.com/oauth2/authorize" +
                "?skip_confirm=false" +
                "&response_type=code" +
                "&redirect_uri=http%3A%2F%2Fpassport.iqiyi.com%2Fapis%2Fthirdparty%2Fncallback.action%3Ffrom%3D30" +
                "&state=d6f72a229f0bae6f16a3228f1ef2dce0" +
                "&client_id=2882303761517310776")

        web_close.setOnClickListener {
            activity!!.finish()
        }

        web_url_et.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                webview.loadUrl(web_url_et.text.toString())
            }
        }

        web_back.setOnClickListener {
            webview.goBack()
        }

        web_forward.setOnClickListener {
            webview.goForward()
        }
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

        }


    }

    override fun loadUrl(urlIn: String) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(windowToken, 0)

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
}
