package com.example.proglayout.csound.listener;

import android.widget.TextView;

import com.example.proglayout.Player;
import com.example.proglayout.csound.channel.DoubleOutput;
import com.example.proglayout.csound.channel.Output;

public class CachedOutputNamesFloat implements Output {
	
	private DoubleOutput mChn;
	private TextView mUnit;
	
	public CachedOutputNamesFloat(String id, TextView unit) {
		mChn = new DoubleOutput(id);		
		mUnit = unit;		
	}
	
	@Override
	public void update() {		
		if (mChn.hasNext()) {
			double d = mChn.getValue();			
			mUnit.setText(Float.toString((float) d));		
		}				
	}
	
	public void addToCsound(Player app) {
		app.getCsoundObj().addValueCacheable(mChn);
		app.addOutput(this);
	}
}

