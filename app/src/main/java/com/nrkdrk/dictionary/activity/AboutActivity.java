package com.nrkdrk.dictionary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.nrkdrk.dictionary.R;

/**
  This is just a place holder
*/
public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		TextView getapp =(TextView) findViewById(R.id.textView4);
		TextView getapp1 =(TextView) findViewById(R.id.textView5);
		getapp.setMovementMethod(LinkMovementMethod.getInstance());
		getapp1.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
