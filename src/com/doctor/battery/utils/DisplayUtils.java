package com.doctor.battery.utils;

import android.content.Context;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class DisplayUtils {

	private final static String TAG = "DisplayUtils";
	public final static int MODE_MANUAL = 0;
	public final static int MODE_AUTO = 1;
	private final static int MAX_VALUE = 255;
	private final static int MIN_VALUE = 20;

	public static boolean isBrightnessOnAutoMode(Context context) {
		try {
			int brightnessMode = System.getInt(context.getContentResolver(),
					System.SCREEN_BRIGHTNESS_MODE);
			return brightnessMode == System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC ? true
					: false;
		} catch (SettingNotFoundException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void setBrightnessMode(Context context, int mode) {
		System.putInt(context.getContentResolver(),
				System.SCREEN_BRIGHTNESS_MODE,
				mode == MODE_MANUAL ? System.SCREEN_BRIGHTNESS_MODE_MANUAL
						: System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

	}

	/**
	 * Displays system's current brightness.
	 */
	public static int getCurrentBrightness(Context context) {
		int brightness = 0;
		if (!isBrightnessOnAutoMode(context)) {
			try {
				brightness = System.getInt(context.getContentResolver(),
						System.SCREEN_BRIGHTNESS);
			} catch (SettingNotFoundException e) {
				Log.d(TAG, "Cannot access system brightness");
				e.printStackTrace();
			}
		} else {
			Log.d(TAG, "Brightness mode is Auto.");
		}
		return brightness;
	}

	/**
	 * Sets user selected brightness value to system and current application
	 * window.
	 */
	public static void updateOverallBrightness(Context context, Window window,
			int brightness) {
		if (validateBrightness(brightness)) {
			updateSystemBrightness(context, brightness);
			updateWindowBrightness(window, brightness);
		} else {
			Log.e(TAG, "Brightness value is not valid.");
		}
	}

	/**
	 * Sets user selected brightness value to system.
	 * 
	 * @param context
	 *            {@link Context}
	 * @param brightness
	 *            int
	 */
	public static void updateSystemBrightness(Context context, int brightness) {
		if (validateBrightness(brightness)) {
			// Set the system brightness using the brightness variable value
			System.putInt(context.getContentResolver(),
					System.SCREEN_BRIGHTNESS, brightness);
		} else {
			Log.e(TAG, "Brightness value is not valid.");
		}
	}

	/**
	 * Sets user selected brightness value to current application window.
	 * 
	 * @param window
	 *            {@link Window}
	 * @param brightness
	 *            int
	 */
	public static void updateWindowBrightness(Window window, int brightness) {
		if (validateBrightness(brightness)) {
			// Get the current window attributes
			LayoutParams layoutpars = window.getAttributes();
			// Set the brightness of this window
			layoutpars.screenBrightness = brightness / (float) MAX_VALUE;
			// Apply attribute changes to this window
			window.setAttributes(layoutpars);
		} else {
			Log.e(TAG, "Brightness value is not valid.");
		}
	}

	/**
	 * Validate user entered brightness value. Its should be between 0 to 255.
	 * @param brightness
	 * @return result true if valid or false
	 */
	public static boolean validateBrightness(int brightness) {
		int validated_brightness = 0;
		if (brightness < 0) {
			validated_brightness = 0;
		} else if (brightness <= MIN_VALUE) {
			// Set the brightness to MIN_VALUE
			validated_brightness = MIN_VALUE;
		} else if (brightness > MAX_VALUE) {
			// Set brightness variable based on the progress bar
			validated_brightness = 0;
		} else {
			validated_brightness = brightness;
		}
		return validated_brightness == 0 ? false : true;
	}

	/**
	 * Returns brightness value in percentage
	 * @param brightness int
	 * @return percentage float
	 */
	public static float getBrightnessPercentage(int brightness) {
		if (validateBrightness(brightness)) {
			// Calculate the brightness percentage
			return (brightness / (float) MAX_VALUE) * 100;
		}
		return 0f;
	}
}
