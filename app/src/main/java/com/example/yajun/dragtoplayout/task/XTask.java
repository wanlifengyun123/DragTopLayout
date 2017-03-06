package com.example.yajun.dragtoplayout.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步任务执行者
 *
 */
public class XTask {

	/**
	 * 线程池
	 */
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);

	private static final int MESSAGE_POST_RESULT = 0x1;
	private static final int MESSAGE_POST_PROGRESS = 0x2;

	private final InternalHandler sHandler = new InternalHandler();

	public FragmentActivity activity;
	public Fragment fragment;

	private ITaskHandle taskHandle;

	public XTask(ITaskHandle taskHandle) {
		this.taskHandle = taskHandle;
		if (taskHandle instanceof FragmentActivity) {
			activity = (FragmentActivity) taskHandle;
		} else if (taskHandle instanceof Fragment) {
			fragment = (Fragment) taskHandle;
			activity = fragment.getActivity();
		}
	}

	public XTask doTask(final int action, final ITask itask) {
		onPreExecute();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Object obj = itask.doBack(action, XTask.this);
				Message message = sHandler.obtainMessage(MESSAGE_POST_RESULT, new TaskResult(XTask.this, obj));
				message.arg1 = action;
				message.sendToTarget();
			}
		};
		executorService.execute(runnable);
		return this;
	}

	/**
	 * 异步执行之前
	 * 继承此类 实现该方法扩展
	 */
	public void onPreExecute() {
	}

	/**
	 * 异步执行之后
	 * 继承此类 实现该方法扩展
	 */
	public void onPostExecute() {

	}

	/**
	 * 运行在主线程
	 *
	 * @param runnable
	 */
	public void runOnUiThread(Runnable runnable) {
		activity.runOnUiThread(runnable);
	}

	public void cancelTask() {
		sHandler.removeCallbacksAndMessages(null);
	}

	private static class TaskResult {
		final XTask task;
		final Object data;

		public TaskResult(XTask task, Object data) {
			this.task = task;
			this.data = data;
		}
	}

	private class InternalHandler extends Handler {

		private InternalHandler() {
			super(Looper.getMainLooper());
		}

		@Override
		public void handleMessage(Message msg) {
			TaskResult result = (TaskResult) msg.obj;
			if(msg.what == MESSAGE_POST_RESULT){
				Message message = new Message();
				message.what = msg.arg1;
				message.obj = result.data;
				result.task.onPostExecute();
				if (result.task.taskHandle != null && !result.task.taskHandle.isDestroyed()) {
					result.task.taskHandle.handleAction(message);
				}
			}
		}
	}


}
