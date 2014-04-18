package com.doctor.battery.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.battery.R;
import com.doctor.battery.utils.MemoryUtils;

public class TestActivity extends BatteryUpdateReceiverActivity {

	TextView textView;
	ImageView imgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView);
		imgView = (ImageView) findViewById(R.id.imageView);

		initBatteryUpdateReceiver();
	}

	@Override
	protected void setUpdatedBatteryState(String status) {
		textView.setText("");
		textView.setText(status);
		// Set battery small image icon to imageview
		imgView.setImageResource(getBatterySmallIcon());

		// Testing
		textView.append("\n-------------------\n");
		textView.append("\nExternal Memory :"
				+ MemoryUtils.getAvailableExternalMemorySize() + "/"
				+ MemoryUtils.getTotalExternalMemorySize());
		textView.append("\nInternal Memory :"
				+ MemoryUtils.getAvailableInternalMemorySize() + "/"
				+ MemoryUtils.getTotalInternalMemorySize());

		textView.append("\nDevice RAM :"+MemoryUtils.getDeviceRam());
		
		MemoryUtils.testMemoryInfo(this);
	}

}
