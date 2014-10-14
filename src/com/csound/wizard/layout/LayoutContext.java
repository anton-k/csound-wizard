package com.csound.wizard.layout;

import android.app.Activity;
import android.content.Context;

import com.csound.wizard.App;
import com.csound.wizard.Player;
import com.csounds.CsoundObj;

public class LayoutContext {	
	private Context context;
	private Player player;
	private int idCount = 0;
	
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
	
	public String getFreshId() {
		idCount++;
		return "csd_wizard_new_fresh_id_" + idCount;
	}
}
