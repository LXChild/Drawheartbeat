package com.temperature;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

public class PaintThread extends Thread {

	private String TAG = "PaintThread";

	private SurfaceHolder holder;

	private TemperaturePaint tem_paint;

	public PaintThread(SurfaceHolder holder, Context cxt) {
		// TODO Auto-generated constructor stub
		this.holder = holder;
		tem_paint = new TemperaturePaint(cxt);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Canvas canvas = null;

		double tem = 30;
		while (true) {
			try {
				canvas = holder.lockCanvas();
				canvas.drawColor(Color.BLACK);// ���û���������ɫ

				tem_paint.DrawBorder(canvas);
				tem_paint.DrawValue(canvas, tem);

				if (tem < 300) {
					tem += 10;
				} else {
					tem = 30;
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
	}
}
