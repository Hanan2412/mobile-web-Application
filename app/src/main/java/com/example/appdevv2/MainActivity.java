package com.example.appdevv2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import Model.Database;
import ModelView.WebAppJSInterface;

/**
 * @author Victoria Avrahamchik
 * @author Hanan Dorfman
 * creates the distinction between view and the database - the mvvm design pattern
 * initiates the webview and the database
 */
public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = new View(new WebView(this));
        Database database = new Database(this);
        WebAppJSInterface webAppJSInterface = new WebAppJSInterface(database,view);
        view.getWebView().addJavascriptInterface(webAppJSInterface,"JSInterface");
        setContentView(view.getWebView());





    }

}