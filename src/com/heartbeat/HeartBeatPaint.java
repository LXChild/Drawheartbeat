package com.heartbeat;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.tools.GetResolution;

public class HeartBeatPaint {

	private String TAG = "HeartBeatPaint";

	private Paint paint;
	private int begin_x, end_x, begin_y, end_y;

	private GetResolution res;
	private int s_width, s_height;

	private int coord_x;
	private int coord_scale;
	private int hb_up_height, hb_btm_height;// 坐标系刻度

	private int[] hb_height;
	private ArrayList<Boolean> show_list;

	public HeartBeatPaint(Context cxt) {
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setStrokeWidth(2);
		paint.setColor(Color.GREEN);
		paint.setAntiAlias(true);

		res = new GetResolution(cxt);
		s_width = res.GetWidth();
		s_height = res.GetHeight();

		coord_x = s_height / 2 - 50;// x轴在屏幕坐标系中的位置

		hb_up_height = (s_height - 300) / 2 - 50;// 心跳高度
		hb_btm_height = coord_x + 50;
		hb_height = new int[] { hb_up_height, coord_x, hb_btm_height, coord_x };

		coord_scale = (s_width - 20) / 20;

		show_list = new ArrayList<Boolean>();
	}

	public void DrawValue(Canvas canvas, boolean hb_data) {

		Log.d("size", show_list.size() + ",");
		begin_x = s_width - 10 - (show_list.size() * coord_scale * 4);
		end_x = begin_x + coord_scale;
		begin_y = coord_x;// x轴在屏幕坐标系中的位置

		for (int i = 0; i < show_list.size(); i++) {

			for (int j = 0; j < 4; j++) {
				if (show_list.get(i)) {

					end_y = hb_height[j];

					Log.d(TAG, begin_x + "," + end_x + "," + begin_y + ","
							+ end_y);
					canvas.drawLine(begin_x, begin_y, end_x, end_y, paint);

				} else {

					Log.d(TAG + "line", begin_x + "," + end_x + "," + begin_y);
					end_y = coord_x;
					canvas.drawLine(begin_x, begin_y, end_x, end_y, paint);
				}

				begin_x = end_x;
				end_x = begin_x + coord_scale;
				begin_y = end_y;
			}
		}

		if (show_list.size() < 5) {

			show_list.add(hb_data);

		} else {
			show_list.remove(0);
			show_list.add(hb_data);
		}
	}

	public void DrowCoord(Canvas canvas) {

		int origin_x = 10;// 原点x坐标
		int coord_scale = (s_width - 20) / 20;// 坐标系刻度
		int current_x = 10;

		Paint p = new Paint(); // 创建画笔
		p.setColor(Color.GREEN);
		p.setAntiAlias(true);
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(3);

		canvas.drawLine(origin_x, coord_x, s_width - 10, coord_x, p);// 画出x轴
		canvas.drawLine(origin_x, 100, origin_x, s_height - 200, p);// 画出y轴

		p.setStrokeWidth(1);
		while (current_x < s_width - 20) {
			canvas.drawLine(current_x, coord_x, current_x, coord_x + 10, p);
			current_x += coord_scale;
		}

	}
}
