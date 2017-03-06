package com.example.yajun.dragtoplayout.task;

import android.app.ProgressDialog;

/**
 * 带有对话框的任务执行者 (构造参数决定了异步任务完成后的回调)
 *
 */
public class Tasker extends XTask {

	private static CharSequence DIALOG_MSG = "正在加载,请稍候...";

	private ProgressDialog progressDialog;

	/**
	 * 是否显示等待框
	 */
	private boolean visible = true;

	/**
	 * dialog是否只显示一次
	 * false则异步任务执行结束后不会关闭对话框
	 */
	private boolean isShowOnce = true;

	public Tasker(ITaskHandle iTaskHandle) {
		super(iTaskHandle);
		progressDialog = createProgressDialog();
	}

	public Tasker(ITaskHandle iTaskHandle,boolean visible) {
		this(iTaskHandle);
		this.visible = visible;
	}

	private ProgressDialog createProgressDialog(){
		if(progressDialog == null){
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage(DIALOG_MSG);
			progressDialog.setCancelable(false);
		}
		return progressDialog;
	}

	@Override
	public void onPreExecute() {
		if (visible) {
			//针对手动控制对话框的 再次开启异步任务 时,不再开启对话框
			if(!progressDialog.isShowing()){
				progressDialog.show();
			}
		} else {
			progressDialog.dismiss();
		}
		super.onPreExecute();
	}

	@Override
	public void onPostExecute() {
		if (isShowOnce) {
			progressDialog.dismiss();
		}
		super.onPostExecute();
	}


	/**
	 * 设置dialog的文字
	 *
	 * @param str
	 */
	public Tasker setDialogMsg(CharSequence str) {
		progressDialog.setMessage(str);
		return this;
	}

	/**
	 * 在异步中修改dialog文字
	 *
	 * @param str
	 */
	public void postDialogMsg(final CharSequence str) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (progressDialog != null && progressDialog.isShowing()) {
					if (progressDialog instanceof ProgressDialog) {
						((ProgressDialog) progressDialog).setMessage(str);
					}
				}
			}
		});
	}

	public void setDialogVisible(boolean visible) {
		this.visible = visible;
	}

	public void setDialogCancel() {
		if (progressDialog != null) {
			progressDialog.setMessage(DIALOG_MSG);
			progressDialog.cancel();
		}
	}

	public void setShowOnce(boolean isShowOnce) {
		this.isShowOnce = isShowOnce;
	}

	public void dismissDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.cancel();
		}
	}

	public void cancelTask() {
		super.cancelTask();
		dismissDialog();
	}

}
