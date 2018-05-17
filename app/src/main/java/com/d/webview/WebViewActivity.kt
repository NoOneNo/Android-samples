package com.d.webview

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.SslError
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import com.d.R
import kotlinx.android.synthetic.main.fg_webview.*

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        supportFragmentManager.beginTransaction().add(android.R.id.content, WebViewFragment()).commit()
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
        webview.loadUrl("https://www.account.xiaomi.com")

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

@SuppressLint("SetJavaScriptEnabled")
open class WebView(context:Context?) : android.webkit.WebView(context) {


    init {
        clearCache(true)

        settings.javaScriptEnabled = true

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
        }
        webChromeClient = object : WebChromeClient() {

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
}
