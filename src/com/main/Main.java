package com.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bloodpressure.BloodPressureActivity;
import com.example.drawheartbeat.R;
import com.heartbeat.HeartBeatActivity;
import com.temperature.TemperatureActivity;
import com.temperature.Test;

public class Main extends Activity implements OnClickListener {

	private Button btn_hb, btn_t, btn_bp;
	public static boolean[] data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.layout_main);
		initView();
		initData();
		super.onCreate(savedInstanceState);
	}

	private void initData() {
		// TODO Auto-generated method stub
		data = new boolean[] { true, false, true, false, true, false, true,
				false, false, true, true };
		btn_hb.setOnClickListener(this);
		btn_t.setOnClickListener(this);
		btn_bp.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_hb = (Button) findViewById(R.id.btn_hb);
		btn_t = (Button) findViewById(R.id.btn_t);
		btn_bp = (Button) findViewById(R.id.btn_bp);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		switch (v.getId()) {
		case R.id.btn_hb:
			i.setClass(this, HeartBeatActivity.class);
			startActivity(i);
			break;
		case R.id.btn_t:
			i.setClass(this, TemperatureActivity.class);
			startActivity(i);
			break;
		case R.id.btn_bp:
			i.setClass(this, BloodPressureActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
	}
}
