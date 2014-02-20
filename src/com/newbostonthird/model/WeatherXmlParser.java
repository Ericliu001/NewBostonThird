package com.newbostonthird.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

public class WeatherXmlParser  {
	
	static final String KEY_CITY = "city";
	static final String KEY_TEMPERATURE = "temperature";

	
	public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
		List<Entry> cityWeathers = new ArrayList<Entry>();
		
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			
			// set the parser input stream
			parser.setInput(in, null);
			
			//get initial eventType
			int eventType = parser.getEventType();
			
			// Loop through pull events until we reach END_DOCUMENT
			while (eventType != parser.END_DOCUMENT) {
				// get current tag 
				String tagname = parser.getName();
				
				eventType = parser.next();
				Log.i("eric", "Tagname: " + tagname);
			}
			
			
			
		} finally {
			in.close();
		}
		
		return null;
	}
}
