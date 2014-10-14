package com.csound.wizard;

import android.app.Activity;
import android.os.Bundle;

public class ExitActivity extends Activity {
	private Boolean isExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isExit = true;		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		isExit = false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (isExit) { onExit(); }
	}	
	
	
	public void onExit() {		
	}
}
