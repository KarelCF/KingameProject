package com.kingame.web.activity;


import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kingame.mainfunction.activity.R;
import com.kingame.slidingmenu.activity.BaseActivity;

public class WebFantaxideaActivity extends BaseActivity {
	
	private WebView fantaxideaWebView = null;
	private String url = "http://www.fantaxidea.com/";
	
	public WebFantaxideaActivity() {
		super(R.string.webfantaxideaactivity_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_fantaxidea_layout);
		
		fantaxideaWebView = (WebView) findViewById(R.id.fantaxideaWebView);
		
		WebSettings settings = fantaxideaWebView.getSettings(); 
		// 下面两行可以使web自动适配当前webview
		settings.setUseWideViewPort(true); 
		settings.setLoadWithOverviewMode(true); 
		settings.setBuiltInZoomControls(true);
		
		fantaxideaWebView.setWebViewClient(new WebViewClient(){});
		
		fantaxideaWebView.loadUrl(url);
		
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	}
	
}
