package com.example.multiclone

import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class WebInstanceActivity : ComponentActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        val cloneId = intent.getStringExtra("CLONE_ID") ?: "default_session"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val safeDirName = cloneId.replace("[^a-zA-Z0-9_]".toRegex(), "_")
            try {
                WebView.setDataDirectorySuffix(safeDirName)
            } catch (_: Exception) {}
        }

        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)

        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://auth.tesla.com/oauth2/v3/authorize")
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
