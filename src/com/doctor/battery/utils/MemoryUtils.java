package com.doctor.battery.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class MemoryUtils {
	
	static String TAG = "MemoryUtils";
	
	public static void testMemoryInfo(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);

		Log.i(TAG, " memoryInfo.availMem " + memoryInfo.availMem + "\n");
		Log.i(TAG, " memoryInfo.lowMemory " + memoryInfo.lowMemory + "\n");
		Log.i(TAG, " memoryInfo.threshold " + memoryInfo.threshold + "\n" );
		
		List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();

		Map<Integer, String> pidMap = new TreeMap<Integer, String>();
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses)
		{
		    pidMap.put(runningAppProcessInfo.pid, runningAppProcessInfo.processName);
		}

		Collection<Integer> keys = pidMap.keySet();

		for(int key : keys)
		{
		    int pids[] = new int[1];
		    pids[0] = key;
		    android.os.Debug.MemoryInfo[] memoryInfoArray = activityManager.getProcessMemoryInfo(pids);
		    for(android.os.Debug.MemoryInfo pidMemoryInfo: memoryInfoArray)
		    {
		        Log.i(TAG, String.format("** MEMINFO in pid %d [%s] **\n",pids[0],pidMap.get(pids[0])));
		        Log.i(TAG, " pidMemoryInfo.getTotalPrivateDirty(): " + pidMemoryInfo.getTotalPrivateDirty() + "\n");
		        Log.i(TAG, " pidMemoryInfo.getTotalPss(): " + pidMemoryInfo.getTotalPss() + "\n");
		        Log.i(TAG, " pidMemoryInfo.getTotalSharedDirty(): " + pidMemoryInfo.getTotalSharedDirty() + "\n");
		    }
		}
	}

	public static String getDeviceRam() {

		String str1 = "/proc/meminfo";
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString) {
				Log.i(str2, num + "\t");
			}
			initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
			localBufferedReader.close();

			float ram = (initial_memory / 1024) / 1024;

			DecimalFormat df = new DecimalFormat("#.0");

			if (ram >= 1024) {

				return df.format(ram / 1024) + " GB";
			} else {

				return df.format(ram) + " MB";
			}

		} catch (IOException e) {
			return "-";
		}
	}

	public static boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	@SuppressWarnings("deprecation")
	// @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static String getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		// blockSize = stat.getBlockSizeLong();
		long availableBlocks = stat.getAvailableBlocks();
		// availableBlocks = stat.getAvailableBlocksLong();
		return formatSize(availableBlocks * blockSize);
	}

	@SuppressWarnings("deprecation")
	public static String getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		// blockSize = stat.getBlockSizeLong();
		long totalBlocks = stat.getBlockCount();
		// totalBlocks = stat.getBlockSizeLong();
		return formatSize(totalBlocks * blockSize);
	}

	@SuppressWarnings("deprecation")
	public static String getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			// blockSize = stat.getBlockSizeLong();
			long availableBlocks = stat.getAvailableBlocks();
			// availableBlocks = stat.getAvailableBlocksLong();
			return formatSize(availableBlocks * blockSize);
		} else {
			return "-";
		}
	}

	@SuppressWarnings("deprecation")
	public static String getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			// blockSize = stat.getBlockSizeLong();
			long totalBlocks = stat.getBlockCount();
			// totalBlocks = stat.getBlockSizeLong();
			return formatSize(totalBlocks * blockSize);
		} else {
			return "-";
		}
	}

	public static String formatSize(long size) {
		String suffix = null;

		if (size >= 1024) {
			suffix = "KB";
			size /= 1024;
			if (size >= 1024) {
				suffix = "MB";
				size /= 1024;
				if (size >= 1024) {
					suffix = "GB";
					size /= 1024;
				}
			}
		}
		return size + " " + suffix;
	}
}
