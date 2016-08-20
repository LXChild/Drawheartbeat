package com.heartbeat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

public class PaintThread extends Thread {
	private SurfaceHolder holder;

	private HeartBeatPaint hb_paint;

	public PaintThread(SurfaceHolder holder, Context cxt) {
		// TODO Auto-generated constructor stub
		this.holder = holder;

		hb_paint = new HeartBeatPaint(cxt);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Canvas canvas = null;
		boolean x = true;

		while (true) {

			try {
				canvas = holder.lockCanvas();// ����������һ����������Ϳ���ͨ���䷵�صĻ�������Canvas���������滭ͼ�Ȳ����ˡ�
				canvas.drawColor(Color.BLACK);// ���û���������ɫ

				hb_paint.DrowCoord(canvas);
				hb_paint.DrawValue(canvas, x);
				if(x){
					x = false;
				} else {					x = true;
				}
				sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);// ����������ͼ�����ύ�ı䡣
				}
			}
		}
	}
}
