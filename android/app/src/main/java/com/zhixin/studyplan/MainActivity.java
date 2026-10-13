package com.zhixin.studyplan;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    // Online copy gets plan updates without reinstalling; the bundled copy is the offline fallback.
    private static final String URL = "https://zhixinli0429.github.io/study-plan/";
    private static final String FALLBACK = "file:///android_asset/index.html";
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError err) {
                if (req.isForMainFrame() && !FALLBACK.equals(view.getUrl())) view.loadUrl(FALLBACK);
            }
        });
        setContentView(web);
        web.loadUrl(URL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Re-render so "today" is correct after the app sat in the background overnight.
        if (web != null) web.evaluateJavascript("typeof render==='function'&&render()", null);
    }
}
