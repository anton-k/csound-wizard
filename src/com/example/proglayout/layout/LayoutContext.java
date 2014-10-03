package com.example.proglayout.layout;

import android.app.Activity;
import android.content.Context;

import com.csounds.CsoundObj;
import com.example.proglayout.App;

public class LayoutContext {	
	private Context context;
	
	public CsoundObj getCsoundObj() {
		return getApp().getCsoundObj();
	}
	
	public Context getContext() {
		return context;
	}
	
	public LayoutContext(Context c) {		
		context = c;				
	}

	public App getApp() {
		return (App) (((Activity) context).getApplication());		
	}
}
