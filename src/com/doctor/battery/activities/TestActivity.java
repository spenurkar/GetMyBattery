package com.doctor.battery.activities;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.battery.R;
import com.doctor.battery.utils.MemoryUtils;
import com.doctor.battery.utils.WifiApControl;

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

		/**
		 * Example for Wifi Hotspot settings
		 */
		// Check if Wifi tathering is supported or not
		boolean isSupported = WifiApControl.isApSupported();
		textView.append("\nWIFI TATHERING IS SUPPORTED." + isSupported);

		// Initialize WifiApControl
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiApControl wifiApControl = WifiApControl.getApControl(wifiManager);

		WifiConfiguration config = wifiApControl.getWifiApConfiguration();
		textView.append("\n" + config.SSID);

		// This is how you got wifiap state
		int state = wifiApControl.getWifiApState();
		textView.append("\nWifi AP enabled state : " + state);

		// Check wifiap state
		if (wifiApControl.isWifiApEnabled()) {
			textView.append("\nDisabling wifi ap...");
			config = wifiApControl.getWifiApConfiguration();
			config.SSID = "TetherFallback";
			// Disable wifiap state
			wifiApControl.setWifiApEnabled(config, false);
		}

		// This is how you got wifiap state
		state = wifiApControl.getWifiApState();
		textView.append("\nWifi AP enabled state now : " + state);
	}

	@Override
	protected void setUpdatedBatteryState(String status) {
		textView.setText("");
		textView.setText(status);
		// Set battery small image icon to imageview
		imgView.setImageResource(getBatterySmallIcon());

		// Testing
		textView.append("\n-------------------\n");
		textView.append("\nExternal Memory \n(Available/Total) : "
				+ MemoryUtils.getAvailableExternalMemorySize() + "/"
				+ MemoryUtils.getTotalExternalMemorySize());
		textView.append("\n\nInternal Memory \n(Available/Total) : "
				+ MemoryUtils.getAvailableInternalMemorySize() + "/"
				+ MemoryUtils.getTotalInternalMemorySize());

		textView.append("\n\nDevice RAM : " + MemoryUtils.getDeviceRam());

		// MemoryUtils.testMemoryInfo(this);
	}

}
