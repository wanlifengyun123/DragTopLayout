package com.example.yajun.dragtoplayout.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.base.BaseActivity;
import com.example.yajun.dragtoplayout.util.NetWorkUtil;
import com.example.yajun.dragtoplayout.util.ToastUtil;

import butterknife.Bind;


public class WebViewActivity extends BaseActivity {

	@Bind(R.id.common_webview_webview)
	WebView webView;
	@Bind(R.id.common_webview_error_msg_tv)
	TextView tvErrorMsg;
	@Bind(R.id.searchbar_progress_bar)
	ProgressBar progressbar;
	TextView tvTitle;

	private String module;

	@Override
	protected int getLayoutId() {
		return R.layout.layout_common_web_view;
	}

	@Override
	protected void init(Bundle savedInstanceState) {
		if (!NetWorkUtil.isNetworkConnected()) {
			ToastUtil.show("请检查网络连接");
		}
		initWebViewSetting();
		//拼装URL
		setWebViewClient();
		webView.loadUrl(module);
	}

	@Override
	protected void initBundle(Bundle savedInstanceState, Intent intent) {
		Bundle bundle = savedInstanceState != null ? savedInstanceState : intent.getExtras();
		module = bundle.getString("inner");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("inner", module);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItemCompat.setShowAsAction(menu.add("刷新"), MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ("刷新".equals(item.getTitle())) {
			webView.reload();
			webView.setVisibility(View.VISIBLE);
		}
		return super.onOptionsItemSelected(item);
	}

	private void setWebViewClient() {
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				progressbar.setProgress(newProgress);
				progressbar.setIndeterminate(false);
				int current = progressbar.getProgress();// 得到当前进度值
				int currentMax = progressbar.getMax();// 得到进度条的最大进度值
//					int secCurrent = progressbar.getSecondaryProgress();// 得到底层当前进度值

				// 以下代码实现进度值越来越大，越来越小的一个动态效果
				if (current >= currentMax) {
					progressbar.setVisibility(View.GONE);
				} else {
					if (progressbar.getVisibility() == View.GONE)
						progressbar.setVisibility(View.VISIBLE);
					// 设置进度值
					progressbar.setProgress(current + 1);
					// 设置底层进度值
					progressbar.setSecondaryProgress(current + 1);

				}
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}
		});
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public boolean shouldOverrideUrlLoading(final WebView view, String url) {
				view.loadUrl(url);
				return false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				view.setVisibility(View.GONE);
				Log.e("ddd", "errorCode" + errorCode + "descText" + description);
			}
		});
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebViewSetting() {
//		webView = (ProgressWebView) findViewById(R.id.common_webview_webview);
//		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.requestFocus();
		webView.getSettings().setLoadWithOverviewMode(true);
//		webView.getSettings().setBuiltInZoomControls(true);
		webView.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				if (url != null && url.startsWith("http://"))
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
		});
	}

	@Override
	protected void onDestroy() {
		ViewGroup group = (ViewGroup) webView.getParent();
		group.removeView(webView);
		webView.destroy();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}

}
