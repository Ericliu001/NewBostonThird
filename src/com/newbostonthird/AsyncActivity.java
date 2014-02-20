package com.newbostonthird;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

public class AsyncActivity extends Activity {

	int progressPercent;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async);

		new ShowPercentageTask().execute();
	}

	private class ShowPercentageTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressPercent = 0;
			dialog = new ProgressDialog(AsyncActivity.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(100);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			for (int i = 0; i < 50; i++) {
				try {
					publishProgress(2);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progressValues) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(progressValues);
			dialog.incrementProgressBy(progressValues[0]);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.async, menu);
		return true;
	}

}
