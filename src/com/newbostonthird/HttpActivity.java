package com.newbostonthird;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class HttpActivity extends Activity {

	String stringUrl = "http://www.google.com";
	
	TextView tvHttp, tvNetworkInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http);

		tvNetworkInfo = (TextView) findViewById(R.id.tvNetworkInfo);
		tvHttp = (TextView) findViewById(R.id.tvHttp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.http, menu);
		return true;
	}

	public void myClickHandler(View view) {

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			tvNetworkInfo.setText(networkInfo.toString());

			new DownloadWebpageTask().execute(stringUrl);

		} else {
			tvNetworkInfo.setText("No connection!");
		}
	}

	private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			try {
				return downloadUrl(urls[0]);
			} catch (Exception e) {
				return "Unable to retrieve web page";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			tvHttp.setText(result);
		}
		
		private String downloadUrl(String myUrl) throws IOException {

			InputStream is = null;
			
			
			// only display the first 500 characters of the retrieved
			
			try {
				
				URL url = new URL(myUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(15000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				
				
				//start the query
				conn.connect();
				int response = conn.getResponseCode();
				Log.i("eric", "The response is: " + response);
				
				is = conn.getInputStream();
				
				String contentString = readIt(is);
				return contentString;
				
				
			} finally {
				if (is != null) {
					is.close();
					
				}
			}
		}

		private String readIt(InputStream stream) throws IOException {
			BufferedReader reader = null;
			reader = new BufferedReader(new  InputStreamReader(stream, "UTF-8"));
			StringBuffer data = new StringBuffer();
			String l = "";
			String nl = System.getProperty("line.separator");
			
			while( (l =reader.readLine()) != null){
				data.append(l + nl);
			}
			return data.toString();
		}
	}
}
