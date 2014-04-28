package com.doctor.battery.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.battery.R;
import com.doctor.battery.utils.MemoryUtils;

public class TestActivity extends BatteryUpdateReceiverActivity {

	TextView textView;
	ImageView imgView;
	Button btnGpsOn,btnGPsOff;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView);
		imgView = (ImageView) findViewById(R.id.imageView);
		
		btnGpsOn = (Button) findViewById(R.id.btnGPSOn);
		btnGPsOff = (Button) findViewById(R.id.btnGPSOff);
		
		btnGpsOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
				intent.putExtra("enabled", true);
				sendBroadcast(intent);
//				turnGPSOn();
			}
		});

		btnGPsOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
				intent.putExtra("enabled", false);
				sendBroadcast(intent);
//				turnGPSOff();
			}
		});

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
		
//		Intent intentBatteryUsage = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);        
//	    startActivity(intentBatteryUsage);
		
//		Toast.makeText(this, "1) Device is rooted ? "+Root.isDeviceRooted(this), Toast.LENGTH_LONG).show();
//		
//		Toast.makeText(this, "2) Device is rooted ? "+isRooted(), Toast.LENGTH_LONG).show();
	}
	
	private void turnGPSOn(){
	    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

	    if(!provider.contains("gps")){ //if gps is disabled
	        final Intent poke = new Intent();
	        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
	        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	        poke.setData(Uri.parse("3")); 
	        sendBroadcast(poke);
	    }
	}

	private void turnGPSOff(){
	    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

	    if(provider.contains("gps")){ //if gps is enabled
	        final Intent poke = new Intent();
	        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
	        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	        poke.setData(Uri.parse("3")); 
	        sendBroadcast(poke);
	    }
	}
	
	
	 /**
	   * Checks if the device is rooted.
	   *
	   * @return <code>true</code> if the device is rooted, <code>false</code> otherwise.
	   */
	  public static boolean isRooted() {

	    // get from build info
	    String buildTags = android.os.Build.TAGS;
	    if (buildTags != null && buildTags.contains("test-keys")) {
	      return true;
	    }

	    // check if /system/app/Superuser.apk is present
	    try {
	      File file = new File("/system/app/Superuser.apk");
	      if (file.exists()) {
	        return true;
	      }
	    } catch (Exception e1) {
	      // ignore
	    }

	    // try executing commands
	    return canExecuteCommand("/system/xbin/which su")
	        || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
	  }

	  // executes a command on the system
	  private static boolean canExecuteCommand(String command) {
	    boolean executedSuccesfully;
	    try {
	      Runtime.getRuntime().exec(command);
	      executedSuccesfully = true;
	    } catch (Exception e) {
	      executedSuccesfully = false;
	    }

	    return executedSuccesfully;
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
