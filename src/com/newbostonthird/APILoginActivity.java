package com.newbostonthird;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

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
import android.widget.Toast;

public class APILoginActivity extends Activity {
	
	TextView tvTweet;

	String tweetUrl = "https://bundoorapresbyterian.ccbchurch.com/api.php?srv=group_search";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apilogin);
		tvTweet = (TextView) findViewById(R.id.tvTweet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.json, menu);
		return true;
	}

	public void onButtonClicked(View view) {
		switch (view.getId()) {
		case R.id.btGetTweet:

			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				new GetTweetsTask().execute(tweetUrl);
			} else {
				Toast.makeText(this, "Network is not connected",
						Toast.LENGTH_LONG);
			}

			break;

		default:
			break;
		}
	}

	private class GetTweetsTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... twitterURL) {

			String searchURL = twitterURL[0];
			try {
				return getTweets(searchURL);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Unable to retrieve web page";

		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			tvTweet.setText(result);
		}

		private String getTweets(String myUrl) throws IOException, URISyntaxException {
			URL url = new URL(myUrl);
			InputStream is = null;

			try {
				
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet();
				URI uri = new URI(myUrl);
				httpGet.setURI(uri);
				httpGet.addHeader(BasicScheme.authenticate(
						new UsernamePasswordCredentials("liucescs", "qazscsace123"), HTTP.UTF_8, false));
				HttpResponse httpResponse = httpClient.execute(httpGet);
				is = httpResponse.getEntity().getContent();
				
				
				

				String contentString = readIt(is);
				is.close();
				return contentString;

			} finally {
				if (is != null) {
					is.close();
				}
			}
		}

		private String readIt(InputStream stream) throws IOException {
			BufferedReader reader = null;
			reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuffer data = new StringBuffer();
			String l = "";
			String nl = System.getProperty("line.separator");

			while ((l = reader.readLine()) != null) {
				data.append(l + nl);
			}
			return data.toString();
		}

	}

}
