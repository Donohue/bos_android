package org.artsinbushwick.bos13;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends Activity {
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.web_layout);
		
		WebView webView = (WebView)findViewById(R.id.webview);
		webView.loadUrl("http://artsinbushwick.com");
	}
}
