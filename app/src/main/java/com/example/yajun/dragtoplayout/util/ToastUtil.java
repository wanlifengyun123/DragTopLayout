package com.example.yajun.dragtoplayout.util;

import android.content.Context;
import android.widget.Toast;

import com.example.yajun.dragtoplayout.base.App;

public class ToastUtil {
	private static Toast t;
	private static Context context;

	static {
		context = App.getInstance().getApplicationContext();
		t = Toast.makeText(context, "", Toast.LENGTH_SHORT);
	}

	/**
	 * @param msg
	 * @param time 多少时间
	 */
	public static void showAsTime(CharSequence msg, int time) {
		t.setText(msg);
		t.setDuration(time);
		t.show();
	}

	/**
	 * 4.0后不适用 预留改为handler
	 *
	 * @param resId
	 * @param time
	 */
	public static void showAsTime(int resId, int time) {
		CharSequence msg = "";
		try {
			msg = context.getText(resId);
		} catch (Exception e) {
			msg = resId + "";
			e.printStackTrace();
		}
		showAsTime(msg, time);
	}

	public static void show(CharSequence msg,int gravity) {
		t.setText(msg);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(gravity,0,0);
		t.show();
	}

	/**
	 * 默认短时
	 *
	 * @param msg
	 */
	public static void show(CharSequence msg) {
		t.setText(msg);
		t.setDuration(Toast.LENGTH_SHORT);
		t.show();
	}

	public static void show(int resId) {
		CharSequence msg = "";
		try {
			msg = context.getText(resId);
		} catch (Exception e) {
			msg = resId + "";
			e.printStackTrace();
		}
		show(msg);
	}

	public static void showLong(CharSequence msg) {
		t.setText(msg);
		t.setDuration(Toast.LENGTH_LONG);
		t.show();
	}

	public static void showLong(int resId) {
		CharSequence msg = "";
		try {
			msg = context.getText(resId);
		} catch (Exception e) {
			msg = resId + "";
			e.printStackTrace();
		}
		showLong(msg);
	}

}
