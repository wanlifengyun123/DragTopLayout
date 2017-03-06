package com.example.yajun.dragtoplayout.task;

import android.os.Message;

public interface ITaskHandle {
	public void handleAction(Message msg);
	boolean isDestroyed();
}
