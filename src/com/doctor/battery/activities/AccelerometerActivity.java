package com.doctor.battery.activities;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.battery.R;

public class AccelerometerActivity extends Activity implements
		SensorEventListener {

	private SensorManager mSensorManager;
	Sensor accelerometer;
	private Display mDisplay;

	TextView txtValues;
	ImageView imgView;

	//static private String folderImagePath = "pvc/images";
	//static private String image_file = "picture_capture.jpg";
	private float mSensorX;
	private float mSensorY;
	private float mSensorZ;
	//private float rotation;

	private static final float NS2S = 1.0f / 1000000000.0f;
	private float timestamp = 0;
	private float[] angle = new float[3];
	StringBuilder str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);

		str = new StringBuilder();
		
		imgView = (ImageView) findViewById(R.id.imgView);
		txtValues = (TextView) findViewById(R.id.textValues);

		imgView.setImageResource(R.drawable.mobile_icon);

		// Get Camera test image
		// try {
		// File mediaStorageDir = new File(
		// Environment.getExternalStorageDirectory(), folderImagePath);
		// if (mediaStorageDir.exists()) {
		// File imgFile = new File(mediaStorageDir.getPath()
		// + File.separator + image_file);
		// if(imgFile.exists()) {
		// Uri outpuUri = Uri.fromFile(imgFile);
		// imgView.setImageURI(outpuUri);
		// }
		// }else {
		// Log.e("TAG", "-------------------------!mediaStorageDir.exists()");
		// }
		// } catch (Exception e) {/* do nothing */
		// }

		WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		mDisplay = mWindowManager.getDefaultDisplay();
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		List<Sensor> list = mSensorManager
				.getSensorList(Sensor.TYPE_ACCELEROMETER);
		for (Sensor sensor : list) {
			Log.d("TAG", sensor.getName());
		}

	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_UI);

	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
			return;

		switch (mDisplay.getRotation()) {
		case Surface.ROTATION_0:
			mSensorX = event.values[0];
			mSensorY = event.values[1];
			mSensorZ = event.values[2];
			//rotation = 0;
			break;
		case Surface.ROTATION_90:
			mSensorX = -event.values[1];
			mSensorY = event.values[0];
			mSensorZ = event.values[2];
			//rotation = 90;
			break;
		case Surface.ROTATION_180:
			mSensorX = -event.values[0];
			mSensorY = -event.values[1];
			mSensorZ = event.values[2];
			//rotation = 180;
			break;
		case Surface.ROTATION_270:
			mSensorX = event.values[1];
			mSensorY = -event.values[0];
			mSensorZ = event.values[2];
			//rotation = 270;
			break;
		}

		float gyrox = event.values[0];
		float gyroy = event.values[1];
		float gyroz = event.values[2];
		// here we integrate over time to figure out the rotational angle around
		// each axis
		if (timestamp != 0) {
			final float dT = (event.timestamp - timestamp) * NS2S;
			angle[0] += gyrox * dT;
			angle[1] += gyroy * dT;
			angle[2] += gyroz * dT;
		}

		// Log.d("TAG", "x : " + mSensorX + ", y : " + mSensorY + ", z : " +
		// mSensorZ+", Angle : "+rotation);
		str.delete(0, str.length());
		str.append("ACC raw data  : \n x : ")
			.append(mSensorX)
			.append(", y : ")
			.append(mSensorY)
			.append(", z : ")
			.append(mSensorZ)
			.append(",\n Angle : X:")
			.append((int) angle[0])
			.append(", Y:")
			.append((int) angle[1])
			.append(", Z:")
			.append((int) angle[2]);
			
		txtValues.setText(str);

		// Rotation: " + rotation+
		timestamp = event.timestamp;
	}

}