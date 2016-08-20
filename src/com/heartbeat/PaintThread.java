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
				canvas = holder.lockCanvas();// 锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
				canvas.drawColor(Color.BLACK);// 设置画布背景颜色

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
					holder.unlockCanvasAndPost(canvas);// 结束锁定画图，并提交改变。
				}
			}
		}
	}
}
