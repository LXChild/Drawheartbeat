package com.heartbeat;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class HeartBeatView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder holder;
	private PaintThread pThread;

	public HeartBeatView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		holder = this.getHolder();
		holder.addCallback(this);
		pThread = new PaintThread(holder, context);// ����һ����ͼ�߳�
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		pThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
}