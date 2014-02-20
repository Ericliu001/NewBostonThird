package com.newbostonthird;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.newbostonthird.model.Entry;
import com.newbostonthird.model.WeatherXmlParser;

public class XMLParsingActivity extends Activity {
	
	static final String baseURL = "api.openweathermap.org/data/2.5/weather?q=";
	static final String apiMode =		"&mode=xml";
	
	
	TextView tvWeather;
	EditText etCity, etState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xmlparsing);

		
		initVars();
	}

	private void initVars() {
		tvWeather = (TextView) findViewById(R.id.tvWeather);
		etCity = (EditText) findViewById(R.id.etCity);
		etState = (EditText) findViewById(R.id.etState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xmlparsing, menu);
		return true;
	}

	
	public void onButtonClicked(View view){
		String c = etCity.getText().toString();
		String s = etState.getText().toString();
		
		StringBuilder myURL = new StringBuilder(baseURL);
		myURL.append(c);
		myURL.append(apiMode);
		
		String fullUrl = myURL.toString();
		Log.i("eric", fullUrl);
		
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		
		if(networkInfo != null && networkInfo.isConnected()){
		new DownloadXmlTask().execute(fullUrl);
		}else{
			tvWeather.setText(networkInfo.toString());
		}
	}
	
	private class DownloadXmlTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... urls) {
			try {
				return loadXmlFromNetwork(urls[0]);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		private String loadXmlFromNetwork(String urlString) throws IOException, XmlPullParserException {
			InputStream stream = null;
			List<Entry> entries = null;
			WeatherXmlParser weatherXmlParser = new WeatherXmlParser();
			
			try {
	            stream = downloadUrl(urlString);
	            entries = weatherXmlParser.parse(stream);
	            
	            return null;
	        // Makes sure that the InputStream is closed after the app is
	        // finished using it.
	        } finally {
	            if (stream != null) {
	                stream.close();
	            }
	        }
			
		}
		
		private InputStream downloadUrl(String urlString) throws IOException {
	        URL url = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        InputStream stream = conn.getInputStream();
	        return stream;
	    }
		
	}
	
	
}
