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
	int Y_axis[],// 保存正弦波的Y轴上的点
			centerY,// 中心线
			oldX, oldY,// 上一个XY点
			currentX;// 当前绘制到的X轴上的点

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏显示
		setContentView(R.layout.layout_temperature);
		hor();
		surface = (SurfaceView) findViewById(R.id.sv_show);
		holder = surface.getHolder();
		holder.addCallback(this);
		surface.setZOrderOnTop(true);
		surface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		mTimer = new Timer();
		mTimerTask = new MyTimerTask();

		/* 初始化y轴数据 */
		centerY = (getWindowManager().getDefaultDisplay().getHeight() - surface
				.getTop()) / 2;
		Y_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];
		for (int i = 1; i < Y_axis.length; i++) {// 计算正弦波
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
		mTimer.schedule(mTimerTask, 0, 5);// 动态绘制正弦波

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
			// 定义画布
			canvas.drawColor(Color.BLACK);
			// 绘制黑色背景
			Paint p = new Paint();
			// 定义画笔
			p.setAntiAlias(true);
			// 设置画笔抗锯齿
			p.setStyle(Paint.Style.STROKE);
			// 设置绘图类型(stroke空心)
			p.setStrokeWidth(4);
			// 设置线宽
			p.setColor(Color.GREEN);
			// 设置画笔颜色
			canvas.drawRect(new RectF(0, 0, WIDTH, HEIGHT), p);
			// 绘制外部举行边框

			/*
			 * 绘制坐标轴
			 */
			int length = WIDTH / 2;// 画多少条竖线，竖线之间宽度为
			int bound = HEIGHT / 2;// 竖长
			PathEffect effects = new DashPathEffect(new float[] { 2, 4, 2, 4 },
					1);
			p.setPathEffect(effects);
			p.setStrokeWidth(6);
			/*
			 * 画竖线
			 */
			canvas.drawLine(length, 0, length, HEIGHT, p);
			/*
			 * 画横线
			 */
			canvas.drawLine(0, bound, WIDTH, bound, p);
			int l = WIDTH / 10;// 画多少条竖线，竖线之间宽度为
			int b = HEIGHT / 8;// 竖长
			p.setStrokeWidth(1);
			for (int i = 0; i < b * 4; i++) {
				for (int j = 0; j < l; j++) {
					/*
					 * 画竖线
					 */
					canvas.drawLine(j * l + 3, 0, j * l + 3, HEIGHT, p);
					/*
					 * 画横线
					 */
					canvas.drawLine(0, i * b + 2, WIDTH, i * b + 2, p);
				}
			}
			/*
			 * 解锁画布并提交内容
			 */
			holder.unlockCanvasAndPost(canvas);
		}
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {

			SimpleDraw(currentX);
			currentX++;// 往前进
			if (currentX == Y_axis.length - 1) {// 如果到了终点，则清屏重来
				ClearDraw();
				currentX = 0;
				oldY = centerY;
			}
		}
	}

	/**
	 * 绘制指定区域
	 */
	void SimpleDraw(int length) {
		if (length == 0)
			oldX = 0;
		Canvas canvas = holder.lockCanvas(new Rect(oldX, 0, oldX + length,
				getWindowManager().getDefaultDisplay().getHeight()));// 关键:获取画布
		Log.i("Canvas:",
				String.valueOf(oldX) + "," + String.valueOf(oldX + length));
		Paint mPaint = new Paint();
		mPaint.setColor(Color.GREEN);// 画笔为绿色
		mPaint.setStrokeWidth(2);// 设置画笔粗细
		int y;
		for (int i = oldX + 1; i < length; i++) {// 绘画正弦波
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
			holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("sss", e.toString());
		}

	}

	void ClearDraw() {
		try {
			Canvas canvas = holder.lockCanvas(null);
			canvas.drawColor(Color.BLACK);// 清除画布
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
