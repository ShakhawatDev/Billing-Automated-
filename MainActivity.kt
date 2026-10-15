package com.ucchash.billing

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.View
import android.webkit.CookieManager
import android.webkit.DownloadListener
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView
    private lateinit var root: LinearLayout
    private val website = "https://ictisc-ivoice.netlify.app/"
    private var pageFailed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(18, 52, 90)
        window.navigationBarColor = Color.rgb(18, 52, 90)

        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
        }
        webView = WebView(this)
        root.addView(webView, LinearLayout.LayoutParams(-1, -1))
        setContentView(root)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            loadsImagesAutomatically = true
            javaScriptCanOpenWindowsAutomatically = false
            mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
            setSupportZoom(true)
            builtInZoomControls = false
            displayZoomControls = false
            userAgentString = userAgentString + " UcchashBillingAndroid/1.0"
        }
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, false)
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val uri = request?.url ?: return false
                val scheme = uri.scheme ?: ""
                return if (scheme == "https" || scheme == "http") {
                    if (uri.host == Uri.parse(website).host || uri.host?.endsWith("netlify.app") == true) false
                    else { openExternal(uri); true }
                } else {
                    openExternal(uri); true
                }
            }
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                if (request?.isForMainFrame == true) showOfflineIfNeeded()
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                pageFailed = false
            }
        }
        webView.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimeType, _ ->
            try {
                val request = DownloadManager.Request(Uri.parse(url))
                    .setMimeType(mimeType)
                    .addRequestHeader("User-Agent", userAgent)
                    .addRequestHeader("Cookie", CookieManager.getInstance().getCookie(url) ?: "")
                    .setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
                    .setDescription("Downloading billing document")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType))
                (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
                Toast.makeText(this, "Download started. Check Downloads.", Toast.LENGTH_LONG).show()
            } catch (_: Exception) {
                Toast.makeText(this, "Download failed. Please try again.", Toast.LENGTH_LONG).show()
            }
        })
        if (savedInstanceState != null) webView.restoreState(savedInstanceState) else webView.loadUrl(website)
    }

    private fun showOfflineIfNeeded() {
        if (pageFailed) return
        pageFailed = true
        if (!isOnline()) {
            val panel = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(32, 32, 32, 32)
                setBackgroundColor(Color.WHITE)
            }
            panel.addView(TextView(this).apply {
                text = "No Internet Connection\n\nইন্টারনেট সংযোগ পরীক্ষা করে আবার চেষ্টা করুন।"
                textSize = 19f
                gravity = Gravity.CENTER
                setTextColor(Color.rgb(18, 52, 90))
            })
            panel.addView(Button(this).apply {
                text = "Retry / আবার চেষ্টা করুন"
                setOnClickListener { pageFailed = false; root.removeView(panel); webView.loadUrl(website) }
            })
            root.addView(panel, LinearLayout.LayoutParams(-1, -1))
        }
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun openExternal(uri: Uri) {
        try { startActivity(Intent(Intent.ACTION_VIEW, uri)) }
        catch (_: Exception) { Toast.makeText(this, "Cannot open this link.", Toast.LENGTH_SHORT).show() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }

    override fun onDestroy() {
        if (::webView.isInitialized) { webView.stopLoading(); webView.destroy() }
        super.onDestroy()
    }
}
