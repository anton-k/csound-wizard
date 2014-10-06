package com.example.proglayout.layout;

import android.app.Activity;
import android.content.Context;

import com.csounds.CsoundObj;
import com.example.proglayout.App;
import com.example.proglayout.Player;

public class LayoutContext {
	private boolean skipConnectionToCsound;
	private Context context;
	private Player player;
	
	public CsoundObj getCsoundObj() {
		return player.getCsoundObj();
	}
	
	public Context getContext() {
		return context;
	}
	
	public LayoutContext(Context c, Player csd, boolean skipConnectionToCsoundFlag) {		
		skipConnectionToCsound = skipConnectionToCsoundFlag;
		context = c;				
		player = csd;
	}

	public App getApp() {
		return (App) (((Activity) context).getApplication());		
	}
	
	public Player getPlayer() {		
		return player;
	}

	public boolean needsConnection() {		
		return !skipConnectionToCsound;
	}
}
