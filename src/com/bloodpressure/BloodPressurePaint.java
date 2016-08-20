package com.bloodpressure;

import com.tools.GetResolution;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class BloodPressurePaint {

	private String TAG = "BloodPressurePaint";

	private Paint paint;

	private GetResolution res;
	private int s_width, s_height;

	private int begin_x, begin_y, end_x;
	private double end_y;

	public BloodPressurePaint(Context cxt) {
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setStrokeWidth(3);
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);

		res = new GetResolution(cxt);
		s_width = res.GetWidth();
		s_height = res.GetHeight();

		begin_x = s_width / 2 - 50;
		begin_y = s_height - 180;
		end_x = begin_x;
	}

	public void DrawValue(Canvas canvas, double bp_current) {

		end_y = bp_current + 200;

		paint.setStrokeWidth(3);
		Log.d(TAG, begin_x + "," + begin_y + "," + end_x + "," + end_y);
		canvas.drawLine(begin_x, begin_y, end_x, (float) end_y, paint);
	}

	public void DrawBorder(Canvas canvas) {

		int bp_scale = (s_height - 270) / 100;// 血压计刻度
		int border_abv_y = 50;// 边框上边y轴坐标
		int current_y = s_height - 250;// 绘制刻度y轴位置

		paint.setStrokeWidth(3);

		RectF border_Rect = new RectF(s_width / 2 - 100, border_abv_y,
				s_width / 2, s_height - 180);
		canvas.drawRoundRect(border_Rect, 20, 20, paint);// 画出边框

		// 绘制血压计边框
		int i = 0;// 绘制刻度次数
		int bp_value = 30;
		while (current_y > border_abv_y + 40) {

			if (i % 10 == 2) {
				paint.setStrokeWidth(2);
				canvas.drawLine(s_width / 2 - 100, current_y, s_width / 2 - 80,
						current_y, paint);

				paint.setTextSize(18);
				paint.setStrokeWidth(1);
				canvas.drawText(bp_value + "", s_width / 2 - 140,
						current_y + 7, paint);
				bp_value += 20;
			} else {
				paint.setStrokeWidth(1);
				canvas.drawLine(s_width / 2 - 100, current_y, s_width / 2 - 90,
						current_y, paint);
			}
			i++;
			current_y -= bp_scale;
		}

		// 绘制血压单位
		paint.setTextSize(20);
		paint.setStrokeWidth(1);
		canvas.drawText("单位 : kPa(7.5mmHg)", s_width - 200, s_height - 130,
				paint);
	}
}
