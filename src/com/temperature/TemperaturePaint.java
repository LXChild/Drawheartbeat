package com.temperature;

import com.tools.GetResolution;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class TemperaturePaint {

	private String TAG = "TemperaturePaint";

	private Paint paint;

	private GetResolution res;
	private int s_width, s_height;

	private int border_left_x, border_btm_y, border_right_x, border_abv_y;

	private int tem_x;
	private double end_y;

	public TemperaturePaint(Context cxt) {
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setStrokeWidth(3);
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);

		res = new GetResolution(cxt);
		s_width = res.GetWidth();
		s_height = res.GetHeight();

		border_left_x = s_width / 2 - 100;
		border_abv_y = 100;
		border_right_x = s_width / 2;
		border_btm_y = s_height - 200;

		tem_x = border_left_x + 50;
	}

	public void DrawValue(Canvas canvas, double tem_current) {

		end_y = tem_current + 200;

		paint.setStrokeWidth(3);
		Log.d(TAG, border_left_x + "," + border_abv_y + "," + border_right_x + ","
				+ border_btm_y);
		canvas.drawLine(tem_x, border_btm_y, tem_x, (float) end_y, paint);
	}

	public void DrawBorder(Canvas canvas) {

		int bp_scale = (border_btm_y - border_abv_y - 100) / 70;// 温度计刻度
		int current_y = border_btm_y - 50;// 绘制刻度y轴位置

		paint.setStrokeWidth(3);

		RectF border_Rect = new RectF(border_left_x, border_abv_y, border_right_x,
				border_btm_y);
		canvas.drawRoundRect(border_Rect, 20, 20, paint);// 画出边框

		// 绘制温度计边框
		int i = 0;// 绘制刻度次数
		int current_tem = 35;// 起始温度
		while (current_y > border_abv_y + 50) {

			if (i % 10 == 5) {
				paint.setStrokeWidth(2);
				canvas.drawLine(border_left_x, current_y, border_left_x + 20,
						current_y, paint);

				paint.setTextSize(18);
				paint.setStrokeWidth(1);
				canvas.drawText(current_tem + "", s_width / 2 - 140,
						current_y + 7, paint);
				current_tem += 1;
			} else {
				paint.setStrokeWidth(1);
				canvas.drawLine(border_left_x, current_y, border_left_x + 10,
						current_y, paint);
			}
			i++;
			current_y -= bp_scale;
		}

		// 绘制体温计单位
		paint.setTextSize(20);
		paint.setStrokeWidth(1);
		canvas.drawText("单位 : ℃", s_width - 100, s_height - 130, paint);
	}
}
