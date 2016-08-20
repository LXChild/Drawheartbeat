package com.bloodpressure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

public class PaintThread extends Thread {

	private String TAG = "PaintThread";

	private SurfaceHolder holder;

	private BloodPressurePaint bp_paint;

	public PaintThread(SurfaceHolder holder, Context cxt) {
		// TODO Auto-generated constructor stub
		this.holder = holder;
		bp_paint = new BloodPressurePaint(cxt);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Canvas canvas = null;

		double bp = 30;
		while (true) {
			try {
				canvas = holder.lockCanvas();
				canvas.drawColor(Color.BLACK);// ���û���������ɫ

				bp_paint.DrawBorder(canvas);
				bp_paint.DrawValue(canvas, bp);
				
				if(bp < 300){
					bp += 10;
				}else{
					bp = 30;
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {

				if (canvas != null) {

					holder.unlockCanvasAndPost(canvas);// ����������ͼ�����ύ�ı䡣
				}
			}
		}
		// super.run();
	}
}
