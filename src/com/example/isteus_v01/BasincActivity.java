package com.example.isteus_v01;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

public class BasincActivity extends Activity {

	private SensorManager SensorManager;
	private SensorManager mSensorManager = null;
	private TextView pressureView;
	private TextView heightView;
	private Float pressure;
	private Float height;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bas);

		pressureView = (TextView) findViewById(R.id.atmLabel);
		heightView = (TextView) findViewById(R.id.kotLabel);

		// get SensorManager instance.
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

	}

	@Override
	protected void onResume() {
		super.onResume();
		// Register listener
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
				android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Unregister listener
		mSensorManager.unregisterListener(mSensorListener);
	}

	private SensorEventListener mSensorListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// when accuracy changed, this method will be called.
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// when pressure value is changed, this method will be called.
			float pressure_value = 0.0f;
			float height = 0.0f;

			// if you use this listener as listener of only one sensor (ex,
			// Pressure), then you don't need to check sensor type.
			if (Sensor.TYPE_PRESSURE == event.sensor.getType()) {
				pressure_value = event.values[0];
				height = android.hardware.SensorManager
						.getAltitude(
								android.hardware.SensorManager.PRESSURE_STANDARD_ATMOSPHERE,
								pressure_value);

				pressureView.setText(String.valueOf(pressure_value));
				heightView.setText(String.valueOf(height));

			}
		}
	};
}
