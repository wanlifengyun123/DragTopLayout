package com.example.yajun.dragtoplayout.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.example.yajun.dragtoplayout.R;

public class DialogUtil {


	/**
	 * 无网络弹出的提示窗口
	 */
	public static void showNotNetworkDialog(final Activity activity) {
		Dialog dialog = new AlertDialog.Builder(activity, R.style.AlertDialogStyle).setCancelable(false).setMessage("网络未开启，请检查网络设置并重试！")// 设置内容
				.setPositiveButton("设置", // 設置按钮
						new OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								try {
									activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
								} catch (Exception e) {
									ToastUtil.show("请手动设置...");
								}
							}
						}).setNegativeButton("取消",// 确定按钮
						new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
//								activity.finish();
							}
						}).create();
		dialog.show();
	}


	public static void showSingleChoiceDialog(Context context,boolean cancelable ,String title, String[] items, int checkedItem,
											  OnClickListener okListener, OnClickListener cancelListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
		builder.setTitle(title);
		builder.setSingleChoiceItems(items, checkedItem, okListener);
		builder.setPositiveButton("取消", cancelListener);
		builder.setCancelable(cancelable);
		builder.create().show();
	}

	public static void showSingleChoiceDialog(Context context, String title, String[] items,
											  OnClickListener onSelectedListener) {
		showSingleChoiceDialog(context,true,title,items,onSelectedListener);
	}
	public static void showSingleChoiceDialog(Context context,boolean cancelable, String title, String[] items,
											  OnClickListener onSelectedListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
		builder.setTitle(title);
		builder.setCancelable(cancelable);
		builder.setItems(items, onSelectedListener);
		builder.create().show();
	}


	public static ProgressDialog showProgressDialog(Context context, CharSequence msg, boolean cancelable, int style) {
		final ProgressDialog pd = createProgressDialog(context, msg, cancelable, style);
		pd.show();
		return pd;
	}

	/**
	 * @param context
	 * @param msg
	 * @param cancelable
	 * @param style
	 * @return
	 */
	public static ProgressDialog createProgressDialog(Context context, CharSequence msg, boolean cancelable, int style) {
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setProgressStyle(style);
		pd.setCancelable(cancelable);
		pd.setMessage(msg);
		if (!cancelable) {
			pd.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_SEARCH)
						return true;
					else
						return false;
				}
			});
		}
		return pd;
	}

	public static ProgressDialog showSystemProgressDialog(Context context, CharSequence msg, boolean cancelable, int style) {
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setProgressStyle(style);
		pd.setCancelable(cancelable);
		pd.setMessage(msg);
		pd.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		pd.show();
		return pd;
	}


	/**
	 * 创建对话框
	 *
	 * @param context
	 * @param title
	 * @param msg
	 * @param cancelable
	 * @param positiveStr
	 * @param onPositiveClickListener
	 * @param negativeStr
	 * @param onNegativeListener
	 * @return
	 */
	public static Dialog createDialog(Context context, CharSequence title, CharSequence msg, boolean cancelable,
									  String positiveStr, OnClickListener onPositiveClickListener, String negativeStr, OnClickListener onNegativeListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(positiveStr, onPositiveClickListener);
		if (negativeStr != null) {
			builder.setNegativeButton(negativeStr, onNegativeListener);
		}
		builder.setCancelable(cancelable);
		AlertDialog dialog = builder.create();
		if (!cancelable) {
			dialog.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_SEARCH)
						return true;
					else
						return false;
				}
			});
		}
		return dialog;
	}

	public static Dialog showDialog(Context context, CharSequence title, CharSequence msg, boolean cancelable,
									String positiveStr, OnClickListener onPositiveClickListener, String negativeStr, OnClickListener onNegativeListener) {
		Dialog dialog = createDialog(context, title, msg, cancelable, positiveStr, onPositiveClickListener, negativeStr, onNegativeListener);
		dialog.show();
		return dialog;
	}

	public static Dialog showDialog(Context context, CharSequence title, CharSequence msg, boolean cancelable,
									String positiveStr, OnClickListener onPositiveClickListener) {
		return showDialog(context, title, msg, cancelable, positiveStr, onPositiveClickListener, null, null);
	}


	/**
	 * 系统出错
	 *
	 * @param context
	 */
	public static Dialog showSystemDialog(Context context, CharSequence title, CharSequence msg, String positiveStr, OnClickListener onPositiveClickListener, String negativeStr, OnClickListener onNegativeListener) {
		Dialog dialog = createDialog(context, title, msg, false, positiveStr, onPositiveClickListener, negativeStr, onNegativeListener);
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
		return dialog;
	}

	/**
	 * 系统出错
	 *
	 * @param context
	 */
	public static Dialog showSystemDialog(Context context, CharSequence title, CharSequence msg, String positiveStr, OnClickListener onPositiveClickListener) {
		Dialog dialog = createDialog(context, title, msg, false, positiveStr, onPositiveClickListener, null, null);
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
		return dialog;
	}

}
