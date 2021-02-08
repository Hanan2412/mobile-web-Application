package com.example.appdevv2;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**@author Victoria Avrahamchik
 * @author Hanan Dorfman
 * creates the webView object
 */
public class View {
    private WebView webView;

    /**
     * initiates the webView object
     * @param webView - the webView object to be initiated and returned to mainActivity
     */
    @SuppressLint("SetJavaScriptEnabled")
    public View(WebView webView){
        this.webView = webView;
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setAllowFileAccess(true);
        this.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        this.webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        this.webView.loadUrl("file:///android_asset/mainWindow.html");
    }

    /**
     * returns the initiated webView
     * @return webView
     */
    public WebView getWebView(){
        return webView;
    }
}
