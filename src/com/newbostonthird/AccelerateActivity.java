package com.newbostonthird;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AccelerateActivity extends Activity implements SensorEventListener {
	SensorManager mSensorManager;
	Sensor mSensor;
	float x, y, sensorX, sensorY;
	Bitmap ball;
	EricIsSexySurface ourSurface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ourSurface = new EricIsSexySurface(this);
		ourSurface.resume();
		setContentView(ourSurface);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
			mSensor = mSensorManager
					.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}

		ball = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		x = y = sensorX = sensorY = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accelerate, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// Sleep
		
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sensorX = event.values[0];
		sensorY = event.values[1];

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		ourSurface.resume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSensorManager.unregisterListener(this);
		ourSurface.pause();
	}

	private class EricIsSexySurface extends SurfaceView implements Runnable {

		SurfaceHolder ourHolder;
		Thread ourThread;
		boolean isRunning = false;
		Paint myPaint;
		Canvas canvas;

		float centerY = 0f, centerX = 0f;

		private EricIsSexySurface(Context context) {
			super(context);
			ourHolder = getHolder();
		}

		private void resume() {
			isRunning = true;
			ourThread = new Thread(this);
			ourThread.start();

		}

		private void pause() {
			isRunning = false;
			while (true) {
				try {
					ourThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			ourThread = null;
		}

		@Override
		public void run() {
			while (isRunning) {
				if (!ourHolder.getSurface().isValid())
					continue;

				canvas = ourHolder.lockCanvas();

				myPaint = new Paint();
				canvas.drawColor(Color.BLUE);
				// get the canvas center
				centerX = canvas.getWidth()/2;
				centerY = canvas.getHeight()/2;

				// Draw a dropping ball
					canvas.drawCircle(centerX + sensorX*20, centerY + sensorY*20, 55, myPaint);

				ourHolder.unlockCanvasAndPost(canvas);
			}
		}

	}
}
