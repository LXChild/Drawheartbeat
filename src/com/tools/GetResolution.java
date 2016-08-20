package com.tools;

import android.content.Context;
import android.util.DisplayMetrics;

public class GetResolution {
	DisplayMetrics dm;
	int screenWidth;
	int screenHeight;

	public GetResolution(Context cxt) {
		// TODO Auto-generated constructor stub
		dm = new DisplayMetrics();
		dm = cxt.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

	public int GetWidth() {
		return screenWidth;
	}

	public int GetHeight() {
		return screenHeight;
	}
}
