package com.heartbeat;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class HeartBeatActivity extends Activity {

//	private LinearLayout ll_hb;
//	private HeartView hb_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	//	setContentView(R.layout.layout_heartbeat);
		initView();
		setContentView(new HeartBeatView(this));
		super.onCreate(savedInstanceState);
	}

//	private void initView() {
//		// TODO Auto-generated method stub
//		ll_hb = (LinearLayout) findViewById(R.id.ll_hb);
//		hb_view = new HeartView(this);
//		// 通知view组件重绘
//		hb_view.invalidate();
//		ll_hb.addView(hb_view);
//	}
	@SuppressLint("NewApi")
	private void initView() {
		// TODO Auto-generated method stub
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("心跳");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		finish();
		return super.onOptionsItemSelected(item);
	}
}
