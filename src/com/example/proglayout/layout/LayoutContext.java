package com.example.proglayout.layout;

import android.app.Activity;
import android.content.Context;

import com.csounds.CsoundObj;
import com.example.proglayout.App;

public class LayoutContext {	
	private Context context;
	private CsoundObj csoundObj;
	
	public CsoundObj getCsoundObj() {
		return csoundObj;
	}
	
	public Context getContext() {
		return context;
	}
	
	public LayoutContext(Context c, CsoundObj csd) {		
		context = c;				
		csoundObj = csd;
	}

	public App getApp() {
		return (App) (((Activity) context).getApplication());		
	}
}
