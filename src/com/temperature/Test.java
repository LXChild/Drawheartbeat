package com.temperature;

import java.util.Timer;
import java.util.TimerTask;

import com.example.drawheartbeat.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class Test extends Activity implements
		android.view.SurfaceHolder.Callback {
	private SurfaceView surface = null;
	private SurfaceHolder holder = null;
	private int WIDTH;
	private int HEIGHT;

	private Timer mTimer;
	private MyTimerTask mTimerTask;
	int Y_axis[],// �������Ҳ���Y���ϵĵ�
			centerY,// ������
			oldX, oldY,// ��һ��XY��
			currentX;// ��ǰ���Ƶ���X���ϵĵ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ���ر���
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȫ����ʾ
		setContentView(R.layout.layout_temperature);
		hor();
		surface = (SurfaceView) findViewById(R.id.sv_show);
		holder = surface.getHolder();
		holder.addCallback(this);
		surface.setZOrderOnTop(true);
		surface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		mTimer = new Timer();
		mTimerTask = new MyTimerTask();

		/* ��ʼ��y������ */
		centerY = (getWindowManager().getDefaultDisplay().getHeight() - surface
				.getTop()) / 2;
		Y_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];
		for (int i = 1; i < Y_axis.length; i++) {// �������Ҳ�
			Y_axis[i - 1] = centerY
					- (int) (100 * Math.sin(i * 2 * Math.PI / 180));
		}
	}

	public void hor() {
		if (this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		oldY = centerY;
		mTimer.schedule(mTimerTask, 0, 5);// ��̬�������Ҳ�

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		new line().start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	class line extends Thread {

		public void run() {
			WIDTH = surface.getWidth();
			HEIGHT = surface.getHeight();
			Canvas canvas = holder.lockCanvas();
			// ���廭��
			canvas.drawColor(Color.BLACK);
			// ���ƺ�ɫ����
			Paint p = new Paint();
			// ���廭��
			p.setAntiAlias(true);
			// ���û��ʿ����
			p.setStyle(Paint.Style.STROKE);
			// ���û�ͼ����(stroke����)
			p.setStrokeWidth(4);
			// �����߿�
			p.setColor(Color.GREEN);
			// ���û�����ɫ
			canvas.drawRect(new RectF(0, 0, WIDTH, HEIGHT), p);
			// �����ⲿ���б߿�

			/*
			 * ����������
			 */
			int length = WIDTH / 2;// �����������ߣ�����֮����Ϊ
			int bound = HEIGHT / 2;// ����
			PathEffect effects = new DashPathEffect(new float[] { 2, 4, 2, 4 },
					1);
			p.setPathEffect(effects);
			p.setStrokeWidth(6);
			/*
			 * ������
			 */
			canvas.drawLine(length, 0, length, HEIGHT, p);
			/*
			 * ������
			 */
			canvas.drawLine(0, bound, WIDTH, bound, p);
			int l = WIDTH / 10;// �����������ߣ�����֮����Ϊ
			int b = HEIGHT / 8;// ����
			p.setStrokeWidth(1);
			for (int i = 0; i < b * 4; i++) {
				for (int j = 0; j < l; j++) {
					/*
					 * ������
					 */
					canvas.drawLine(j * l + 3, 0, j * l + 3, HEIGHT, p);
					/*
					 * ������
					 */
					canvas.drawLine(0, i * b + 2, WIDTH, i * b + 2, p);
				}
			}
			/*
			 * �����������ύ����
			 */
			holder.unlockCanvasAndPost(canvas);
		}
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {

			SimpleDraw(currentX);
			currentX++;// ��ǰ��
			if (currentX == Y_axis.length - 1) {// ��������յ㣬����������
				ClearDraw();
				currentX = 0;
				oldY = centerY;
			}
		}
	}

	/**
	 * ����ָ������
	 */
	void SimpleDraw(int length) {
		if (length == 0)
			oldX = 0;
		Canvas canvas = holder.lockCanvas(new Rect(oldX, 0, oldX + length,
				getWindowManager().getDefaultDisplay().getHeight()));// �ؼ�:��ȡ����
		Log.i("Canvas:",
				String.valueOf(oldX) + "," + String.valueOf(oldX + length));
		Paint mPaint = new Paint();
		mPaint.setColor(Color.GREEN);// ����Ϊ��ɫ
		mPaint.setStrokeWidth(2);// ���û��ʴ�ϸ
		int y;
		for (int i = oldX + 1; i < length; i++) {// �滭���Ҳ�
			y = Y_axis[i - 1];
			try {
				canvas.drawLine(oldX, oldY, i, y, mPaint);
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("ss", e.toString());
			}
			oldX = i;
			oldY = y;
		}
		try {
			holder.unlockCanvasAndPost(canvas);// �����������ύ���õ�ͼ��
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("sss", e.toString());
		}

	}

	void ClearDraw() {
		try {
			Canvas canvas = holder.lockCanvas(null);
			canvas.drawColor(Color.BLACK);// �������
			holder.unlockCanvasAndPost(canvas);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("ssss", e.toString());
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			mTimer.cancel();
			mTimer = null;
		}
		return super.onKeyDown(keyCode, event);
	}
}
