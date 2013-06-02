package org.artsinbushwick.bos13;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MessageActivity extends Activity {
	private String message = "PCFkb2N0eXBlIGh0bWw+CjxodG1sIGxhbmc9ImVuIj4KPGhlYWQ+CiAgPG1ldGEgY2hhcnNldD0idXRmLTgiPgogIDx0aXRsZT5Db21tdW5pdHkgTWVzc2FnZTwvdGl0bGU+CiAgPHN0eWxlPgogIGJvZHkge2ZvbnQtZmFtaWx5OiAiSGVsdmV0aWNhIE5ldWUiLCBIZWx2ZXRpY2EsIEFyaWFsLCBzYW5zLXNlcmlmO30gaDEge21hcmdpbi10b3A6IDB9CiAgPC9zdHlsZT4KPC9oZWFkPgo8Ym9keT4KPGgxPkEgTWVzc2FnZSB0byBPdXIgQ29tbXVuaXR5PC9oMT4KPHA+T3ZlciB0aGUgcGFzdCB0d28geWVhcnMsIEFydHMgaW4gQnVzaHdpY2sgaGFzIGV4cGVyaWVuY2VkIHVucHJlY2VkZW50ZWQgZ3Jvd3RoLCBleHBhbmRpbmcgb3VyIGNvbW11bml0eSBvdXRyZWFjaCBhbmQgcHJvZ3JhbW1pbmcsIGZlc3RpdmFsIHBhcnRpY2lwYXRpb24gYW5kIHZvbHVudGVlciBuZXR3b3JrLiBPdXIgY3VycmVudCBncm91cCBvZiBsZWFkIG9yZ2FuaXplcnMgaGFzIGNvbWUgdG9nZXRoZXIgdGhpcyB5ZWFyIHRvIHByb3ZpZGUgYW4gb3BlbiBzdHVkaW9zIGFuZCBhcnRzIGZlc3RpdmFsIHdoaWNoLCByZWdhcmRsZXNzIG9mIHRoZSBzdXJnZSBpbiBzaXplIGFuZCBzY29wZSwgZnVuY3Rpb25zIGFzIGFuIG9wZW4gZm9ydW0gZm9yIHRoZSBjcmVhdGlvbiwgZXhoaWJpdGlvbiBhbmQgY2VsZWJyYXRpb24gb2YgYXJ0IGluIHRoZSBCdXNod2ljayBjb21tdW5pdHkuPC9wPgo8cD5UaHJvdWdob3V0IDIwMTItMjAxMywgd2l0aCB0aGUgY29tYmluZWQgZWZmb3J0cyBvZiBpdHMgdm9sdW50ZWVycywgQWlCIGluY3JlYXNlZCB0aGUgcmVhY2ggb2Ygb3VyIGNvbW11bml0eSBwcm9ncmFtbWluZyBieSBwYXJ0bmVyaW5nIHdpdGggbG9jYWwgb3JnYW5pemF0aW9ucywgaW5kaXZpZHVhbHMgYW5kIHNjaG9vbHMgdG8gb2ZmZXIgZnJlZSBldmVudHMgYW5kIHdvcmtzaG9wcyBmb3IgcmVzaWRlbnRzLCB3aXRoIGEgc3BlY2lhbCBmb2N1cyBvbiB0aGUgeW91dGggcG9wdWxhdGlvbi4gSW4gdGhpcyB2ZWluLCB3ZSBhcmUgdGhyaWxsZWQgdG8gZGVidXQgPHN0cm9uZz5CT1MgJzEzIENvbW11bml0eSBEYXk8L3N0cm9uZz4gaW4gTWFyaWEgSGVybmFuZGV6IFBhcmssIGEgbmV3IEJPUyBldmVudCBmZWF0dXJpbmcgbXVzaWMgYW5kIGZhbWlseS1mcmllbmRseSBhY3Rpdml0aWVzLiBXZSBhcmUgZXF1YWxseSBleGNpdGVkIHRvIHVudmVpbCBhIG5ldyBwdWJsaWMgbXVyYWwgaW4gdGhlIG91dGRvb3IgYXF1YXBvbmljIGZhcm0gc3BhY2UKYXQgTGEgTWFycXVldGEgZGUgV2lsbGlhbXNidXJnLCBkZXZlbG9wZWQgYnkgYXJ0aXN0IGFuZCBCdXNod2ljayByZXNpZGVudCBNaXJpYW0gQ2FzdGlsbG8gaW4gY29sbGFib3JhdGlvbiB3aXRoIHN0dWRlbnRzIGZyb20gdGhlIEJlYWNvbiBDZW50ZXIgZm9yIEFydHMgYW5kIExlYWRlcnNoaXAgYW5kIG1hZGUgcG9zc2libGUgYnkgYSBwYXJ0bmVyc2hpcCBiZXR3ZWVuIFRoZSBNb29yZSBTdHJlZXQgUmV0YWlsIE1hcmtldCwgT0tPIEZhcm1zLCB0aGUgQmVhY29uIENlbnRlciwgYW5kIHRoZSBBaUIgQ29tbXVuaXR5IFRlYW0uPC9wPgo8cD5PdGhlciBuZXcgaGlnaGxpZ2h0cyB0byB0aGlzIHllYXLigJlzIGZlc3RpdmFsIGluY2x1ZGUgPHN0cm9uZz5DaW5lbWFTdW5kYXk8L3N0cm9uZz4sIHdoaWNoIHByb3ZpZGVzIGZpbG0gYW5kIHZpZGVvIGFydGlzdHMgYSBjaGFuY2UgdG8gcmVhbGl6ZSB0aGVpciB3b3JrIG9uIHRoZSBiaWcgc2NyZWVuIGR1cmluZyBCT1MgJzEzOyA8c3Ryb25nPkFpQiBCbG9nPC9zdHJvbmc+LCBuZXdseSByZS1sYXVuY2hlZCBpbiAyMDEzIHRvIHByb3ZpZGUgdW5pcXVlIGNvbnRlbnQgaW4gYW4gZXhwYW5kZWQgc2NvcGUsIGluY2x1ZGluZyA8c3Ryb25nPkFpQiBSYWRpbzwvc3Ryb25nPiwgYSBzZXJpZXMgb2YgcG9kY2FzdHMgaGlnaGxpZ2h0aW5nIG11c2ljaWFucyBhbmQgb3JpZ2luYWwgYXVkaW8gaW50ZXJ2aWV3czsgYW5kIGFuIDxzdHJvbmc+RWxlY3Ryb25pYyBNdXNpYyBTaG93Y2FzZTwvc3Ryb25nPiwgd2hpY2ggb2ZmZXJzIGV4cG9zdXJlIHRvIG11c2ljaWFucyBhbmQgc291bmQgYXJ0aXN0cyBleHBlcmltZW50aW5nIGluIHRoaXMgaHlicmlkIG1lZGl1bS4gVGhlc2UgZXhjaXRpbmcgYWRkaXRpb25zIHRvIG91ciBvZmZpY2lhbCBwcm9ncmFtbWluZyByb3N0ZXIgd291bGQgbm90IGJlIG1hZGUgcG9zc2libGUgd2l0aG91dCB0aGUgZXh0cmFvcmRpbmFyeSB0YWxlbnRzIGFuZCBoYXJkIHdvcmsgb2Ygb3VyIHBhc3Npb25hdGUgdGVhbSBvZiB2b2x1bnRlZXJzLiBXZSB3b3VsZCBzcGVjaWZpY2FsbHkgbGlrZSB0byB0aGFuayBvdXIgY29yZSBncm91cCBvZiBsZWFkIG9yZ2FuaXplcnMgYW5kIGNvb3JkaW5hdG9ycyBvZiBCT1MgJzEzIHdobyBoYXZlIHdvcmtlZCB0aXJlbGVzc2x5IHllYXItcm91bmQgdG8gbWFrZSBBaUIgYW5kIEJPUyAnMTMgYSBzdWNjZXNzOiA8c3Ryb25nPkx1Y2lhIFJvbGxvdywgSnVsaWEgU2luZWxuaWtvdmEsIEhvbGx5IFNoZW4gQ2hhdmVzLCBTYW1hbnRoYSBLYXR6LCBIYW5sZXkgTWEsIEppbGxpYW4gU2FsaWssIExhdXJlbiBELiBTbWl0aCwgTWVnYW4gVHJldmlubywgVGFyYSBGZXJyaSwgQW5keSBHaWxsZXR0ZSwgTWlrYWVsIEhlbmFmZiwgSm9leSBDYXN0aWxsbywgQnJpYW4gRG9ub2h1ZSwgQWxleGFuZHJhIFNwaW5rcywgTWFyeSBHb3JkYW5pZXIsIE1hbmR5IE1hbmRlbHN0ZWluLCBUcmFjeSBGcmFuY2lzLCBhbmQgSWFuIENvbGxldHRpPC9zdHJvbmc+LjwvcD4KPHA+QWlCIHdvdWxkIGFsc28gbGlrZSB0byB0aGFuayBvdXIgbG9jYWwgY29tbXVuaXR5LCBhcnRpc3QsIGdhbGxlcnkgYW5kIHN0dWRpbyBwYXJ0aWNpcGFudHMgYW5kIHNwb25zb3JzIGZvciBicmluZ2luZyB0aGlzIHRydWx5IG9uZS1vZi1hLWtpbmQgY29tbXVuaXR5IGFydCBmZXN0aXZhbCB0byBmcnVpdGlvbi4gRW5qb3kgQk9TICcxMyE8L3A+CjxwPjxzdHJvbmc+Jm5ic3A7Jm1kYXNoOyZuYnNwO1RoZSBBSUIgVGVhbTwvc3Ryb25nPjwvcD4KPC9ib2R5Pgo8L2h0bWw+Cgo=";
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.web_layout);
		
		WebView webView = (WebView)findViewById(R.id.webview);
		WebSettings settings = webView.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
		    String base64 = Base64.encodeToString(message.getBytes(), Base64.DEFAULT);
		    System.out.println(base64);
		    webView.loadData(base64, "text/html; charset=utf-8", "base64");
		} else {
		    String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    webView.loadData(header + message, "text/html; chartset=UTF-8", null);
		}
	}
	
	public class WebClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView webView, String url) {
			return false;
		}
	}
}