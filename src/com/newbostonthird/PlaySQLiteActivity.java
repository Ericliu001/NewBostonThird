package com.newbostonthird;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class PlaySQLiteActivity extends Activity {
	
	Button btSQLUpdate, btSQLOpenView, btGetInfo, btSQLModify, btSQLDelete;
	EditText etSQLName, etSQLHotness, etSQLRowInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_sqlite);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_sqlite, menu);
		return true;
	}

}
