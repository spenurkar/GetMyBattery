package com.doctor.battery.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public abstract class BatteryUpdateReceiverActivity extends Activity {

	private String TAG = "BatteryUpdateReceiverActivity";
	private StringBuilder sb;
	private int smallBatteryImageId = 0;
	
	protected abstract void setUpdatedBatteryState(String status);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sb = new StringBuilder();
	}
	
	protected void initBatteryUpdateReceiver() {
		// Start listening to the action battery changed
		this.registerReceiver(batteryInfoReciever, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
	}
	
	private BroadcastReceiver batteryInfoReciever = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			

			// get current battery health
			int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

			// gets resource id for small stastus bar icon
			int icon_small = intent.getIntExtra(
					BatteryManager.EXTRA_ICON_SMALL, 0);

			// get current battery level, starts from 0
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

			// check if device is plugged into a power resource. 0 means device
			// is on battery
			int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

			// check if battery id present or not
			boolean isPresent = intent.getBooleanExtra(
					BatteryManager.EXTRA_PRESENT, false);

			// get max battery level
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);

			// get battery status
			int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);

			// get battery info
			String technology = intent
					.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);

			// get battery temparature
			int temperature = intent.getIntExtra(
					BatteryManager.EXTRA_TEMPERATURE, 0);

			// get battery voltage
			int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

			sb.delete(0, sb.length());
			sb.append("Health: ");
			sb.append(getHealthString(health));
			sb.append("\n");
			sb.append("Level: ");
			sb.append(level);
			sb.append("%");
			sb.append("\n");
			sb.append("Plugged: ");
			sb.append(getPlugTypeString(plugged));
			sb.append("\n");
			sb.append("Present: ");
			sb.append(isPresent);
			sb.append("\n");
			sb.append("Maximum Power: ");
			sb.append(scale);
			sb.append("%");
			sb.append("\n");
			sb.append("Status: ");
			sb.append(getStatusString(status));
			sb.append("\n");
			sb.append("Battery Type: ");
			sb.append(technology);
			sb.append("\n");
			sb.append("Temperature: ");
			sb.append(convertTemperatureInCentigrade(temperature));
			sb.append(" C");
			sb.append("\n");
			sb.append("Battery Voltage: ");
			sb.append(convertVoltageInVolts(voltage));
			sb.append(" V");

			Log.d(TAG,sb.toString());
			
			smallBatteryImageId = icon_small;
			setUpdatedBatteryState(sb.toString());
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try{
			this.unregisterReceiver(batteryInfoReciever);
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Gets battery status in user readable string.
	 * 
	 * @param code
	 *            Battery status code
	 * @return status User readable form
	 */
	private String getStatusString(int code) {

		String status = "";

		switch (code) {
		case BatteryManager.BATTERY_STATUS_CHARGING:
			status = "Charging";
			break;
		case BatteryManager.BATTERY_STATUS_DISCHARGING:
			status = "Discharge";
			break;
		case BatteryManager.BATTERY_STATUS_FULL:
			Toast.makeText(this, "battery full", Toast.LENGTH_LONG).show();
			status = "Full";
			break;
		case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
			status = "Not Changing";
			break;
		case BatteryManager.BATTERY_STATUS_UNKNOWN:
			status = "Unknown";
			break;
		default:
			break;
		}
		return status;
	}
	
	protected int getBatterySmallIcon() {
		return smallBatteryImageId;
	}
	
	/**
	 * Gets battery health status in user readable string.
	 * 
	 * @param code
	 *            Battery health status code
	 * @return health status User readable form
	 */
	private String getHealthString(int code) {

		String healthStatus = "";

		switch (code) {
		case BatteryManager.BATTERY_HEALTH_COLD:
			healthStatus = "Cold";
			break;
		case BatteryManager.BATTERY_HEALTH_DEAD:
			healthStatus = "Dead";
			break;
		case BatteryManager.BATTERY_HEALTH_GOOD:
			healthStatus = "Good";
			break;
		case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
			healthStatus = "Over Voltage";
			break;
		case BatteryManager.BATTERY_HEALTH_OVERHEAT:
			healthStatus = "Overheat";
			break;
		case BatteryManager.BATTERY_HEALTH_UNKNOWN:
			healthStatus = "Unknown";
			break;
		case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
			healthStatus = "Unspecified Failure";
			break;
		default:
			break;
		}
		return healthStatus;
	}
	
	private String getPlugTypeString(int code){
		String source = "";
		switch (code) {
		case BatteryManager.BATTERY_PLUGGED_AC:
			source = "AC";
			break;
		case BatteryManager.BATTERY_PLUGGED_USB:
			source = "USB";
			break;
		case BatteryManager.BATTERY_PLUGGED_WIRELESS:
			source = "WIRELESS";
			break;
		default:
			break;
		}
		return source;
	}

	/**
	 * Converts battery temparature from current value which is 
	 * in tenths of a degree Centigrade.
	 * @param rawValue temperature
	 * @return temprature in centigrade
	 */
	private int convertTemperatureInCentigrade(int rawValue) {
		return rawValue/10;
	}
	
	/**
	 * Converts battery voltage from current value which is 
	 * in millivolts.
	 * @param rawValue millivolts
	 * @return temprature in Volts
	 */
	private int convertVoltageInVolts(int rawValue) {
		return rawValue/1000;
	}

}
