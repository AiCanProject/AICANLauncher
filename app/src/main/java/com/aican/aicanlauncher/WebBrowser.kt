package com.aican.aicanlauncher

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aican.aicanlauncher.databinding.ActivityWebBrowserBinding
import com.aican.aicanlauncher.settingLock.LockScreen
import com.aican.aicanlauncher.util.Source


class WebBrowser : AppCompatActivity() {
    lateinit var webBrowserBinding: ActivityWebBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webBrowserBinding = ActivityWebBrowserBinding.inflate(layoutInflater)
        setContentView(webBrowserBinding.root)

        supportActionBar?.hide()

        window.decorView.apply {
            // Hide the status bar
            systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


        val preference = getSharedPreferences("WEB_URL", MODE_PRIVATE)

        val sharedPreference = getSharedPreferences(Source.ID_PASSWORD, MODE_PRIVATE)

        val id: String = sharedPreference.getString(Source.ID, "").toString()
        val pass: String = sharedPreference.getString(Source.PASSWORD, "").toString()

        val webView = webBrowserBinding.webView

        webView.getSettings().setBuiltInZoomControls(true);

        webBrowserBinding.settings.setOnClickListener {
            if (id == "") {
                startActivity(Intent(this, DashboardSettings::class.java))
            } else {
                val i = Intent(this, LockScreen::class.java)
                i.putExtra("activity", "Settings")
                startActivity(i)
            }
        }

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading....")
        progressDialog.setCancelable(false)

        //webview

        //webview
        val url = preference.getString(
            "WEB_URL",
            "https://aican.co.in/"
        ).toString()

        webBrowserBinding.swipeR.setOnRefreshListener {
            webView.loadUrl(webView.url.toString())
//            webBrowserBinding.swipeR.isRefreshing = false
        }
        webView.clearCache(true)
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
//        webView.getSettings().setDomStorageEnabled(true)
//        webView.getSettings().setSaveFormData(true)
//        webView.getSettings().setAllowContentAccess(true)
//        webView.getSettings().setAllowFileAccess(true)
//        webView.getSettings().setAllowFileAccessFromFileURLs(true)
//        webView.getSettings().setAllowUniversalAccessFromFileURLs(true)
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setMinimumFontSize(1);
        webView.getSettings().setMinimumLogicalFontSize(1);
        webView.isClickable = true

        webView.webViewClient = WebViewClient()

        webView.loadUrl(url)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100) {
                    progressDialog.show()
                }
                if (progress == 100) {
                    progressDialog.dismiss()
                    webBrowserBinding.swipeR.isRefreshing = false
                }
            }
        }

        webBrowserBinding.refresh.setOnClickListener {
            webView.loadUrl(webView.url.toString())
        }

        webBrowserBinding.editLinkLayout.visibility = View.GONE

        webBrowserBinding.editLink.setOnClickListener {
            webBrowserBinding.editLinkLayout.visibility = View.VISIBLE
            webBrowserBinding.pinLayout.visibility = View.VISIBLE
            webBrowserBinding.enterURLLayout.visibility = View.GONE
        }

        webBrowserBinding.btnCheckPin.setOnClickListener {
            val pin = webBrowserBinding.etPinL.text.toString()
            if (pin.isEmpty()) {
                webBrowserBinding.etPinL.error = "PIN is empty"
            } else {
                if (pin.equals("12345678")) {
                    webBrowserBinding.enterURLLayout.visibility = View.VISIBLE
                    webBrowserBinding.pinLayout.visibility = View.GONE
                    webBrowserBinding.etPinL.setText("")
                } else {
                    webBrowserBinding.pinLayout.visibility = View.VISIBLE
                    webBrowserBinding.enterURLLayout.visibility = View.GONE
                    webBrowserBinding.etPinL.error = "Incorrect PIN"
                }
            }
        }

        webBrowserBinding.setUrlBtn.setOnClickListener {
            val url = webBrowserBinding.setUrlEdit.text.toString()
            if (url.isEmpty()) {
                webBrowserBinding.setUrlEdit.error = "Please enter the URL"

            } else {
                val preferences = getSharedPreferences("WEB_URL", MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("WEB_URL", url)
                editor.apply()
                Toast.makeText(
                    this@WebBrowser,
                    "Default URL set to : " + preferences.getString("WEB_URL", "N/A"),
                    Toast.LENGTH_LONG
                ).show()

                if (url.equals(preferences.getString("WEB_URL", "N/A"))) {
                    webBrowserBinding.setUrlEdit.setText("")
                    webBrowserBinding.editLinkLayout.visibility = View.GONE
                    webView.loadUrl(preferences.getString("WEB_URL", "N/A").toString())
                }

            }
        }

        webBrowserBinding.cancelEdit.setOnClickListener {
            webBrowserBinding.editLinkLayout.visibility = View.GONE
        }


    }

    override fun onBackPressed() {
        if (webBrowserBinding.webView.isFocused && webBrowserBinding.webView.canGoBack()) {
            webBrowserBinding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun configureIncognitoTab(myWebView: WebView) {
        myWebView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        myWebView.settings.setAppCacheEnabled(false)
        myWebView.clearHistory()
        myWebView.clearCache(true)
        myWebView.clearFormData()
        myWebView.settings.saveFormData = false
    }
}