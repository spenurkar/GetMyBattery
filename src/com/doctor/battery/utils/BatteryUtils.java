package com.doctor.battery.utils;


public class BatteryUtils {
/*
	*//**
	 * Gets battery status in user readable string.
	 * 
	 * @param code
	 *            Battery status code
	 * @return status User readable form
	 *//*
	public static String getStatusString(int code) {

		String status = "";

		switch (code) {
		case BatteryManager.BATTERY_STATUS_CHARGING:
			status = "Charging";
			break;
		case BatteryManager.BATTERY_STATUS_DISCHARGING:
			status = "Discharge";
			break;
		case BatteryManager.BATTERY_STATUS_FULL:
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
	
	*//**
	 * Gets battery health status in user readable string.
	 * 
	 * @param code
	 *            Battery health status code
	 * @return health status User readable form
	 *//*
	public static String getHealthString(int code) {

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
	
	public static String getPlugTypeString(int code){
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

	*//**
	 * Converts battery temparature from current value which is 
	 * in tenths of a degree Centigrade.
	 * @param rawValue temperature
	 * @return temprature in centigrade
	 *//*
	public static int convertTemperatureInCentigrade(int rawValue) {
		return rawValue/10;
	}
	
	*//**
	 * Converts battery voltage from current value which is 
	 * in millivolts.
	 * @param rawValue millivolts
	 * @return temprature in Volts
	 *//*
	public static int convertVoltageInVolts(int rawValue) {
		return rawValue/1000;
	}
*/}
