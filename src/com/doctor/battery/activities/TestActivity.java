package com.doctor.battery.activities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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

		// initBatteryUpdateReceiver();

		/**
		 * Example for Wifi Hotspot settings
		 */
		// Check if Wifi tathering is supported or not
		/*
		 * boolean isSupported = WifiApControl.isApSupported(); if (isSupported)
		 * { textView.append("\nWIFI TATHERING IS SUPPORTED." + isSupported);
		 * 
		 * // Initialize WifiApControl WifiManager wifiManager = (WifiManager)
		 * getSystemService(Context.WIFI_SERVICE); WifiApControl wifiApControl =
		 * WifiApControl .getApControl(wifiManager);
		 * 
		 * WifiConfiguration config = wifiApControl.getWifiApConfiguration();
		 * //textView.append("\n" + config.SSID);
		 * 
		 * // This is how you got wifiap state int state =
		 * wifiApControl.getWifiApState();
		 * textView.append("\nWifi AP enabled state : " + state);
		 * 
		 * // Check wifiap state if (wifiApControl.isWifiApEnabled()) {
		 * textView.append("\nDisabling wifi ap..."); config =
		 * wifiApControl.getWifiApConfiguration(); config.SSID =
		 * "TetherFallback"; // Disable wifiap state
		 * wifiApControl.setWifiApEnabled(config, false); }
		 * 
		 * // This is how you got wifiap state state =
		 * wifiApControl.getWifiApState();
		 * textView.append("\nWifi AP enabled state now : " + state); }
		 */
		
		openDataRoamingSettingsPage();
	}
	
	private void openDataRoamingSettingsPage() {
		Intent intent=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
		ComponentName cName = new ComponentName("com.android.phone","com.android.phone.Settings");
		intent.setComponent(cName); 
		startActivity(intent);
	}

	private void dumpSysInfo() {
		try {
			String cmd = "dumpsys battery";
			Log.i("###### ********", "EXECUTING CMD: " + cmd);
			Process script = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					script.getInputStream()));
			Log.i("##########", "****** >> Battery stats *******");

			String line = null;
			while ((line = in.readLine()) != null) {
				Log.i("#############", line);
			}

			Log.i("##########", "******/Battery stats *******");

		} catch (Exception e) {
			e.printStackTrace();
		}
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
