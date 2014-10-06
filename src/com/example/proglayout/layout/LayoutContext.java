package com.example.proglayout.layout;

import android.app.Activity;
import android.content.Context;

import com.csounds.CsoundObj;
import com.example.proglayout.App;
import com.example.proglayout.Player;

public class LayoutContext {	
	private Context context;
	private Player player;
	
	public CsoundObj getCsoundObj() {
		return player.getCsoundObj();
	}
	
	public Context getContext() {
		return context;
	}
	
	public LayoutContext(Context c, Player csd) {		
		context = c;				
		player = csd;
	}

	public App getApp() {
		return (App) (((Activity) context).getApplication());		
	}
	
	public Player getPlayer() {		
		return player;
	}	
}
