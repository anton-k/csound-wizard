package com.example.proglayout.csound.listener;

import android.widget.TextView;

import com.example.proglayout.Player;
import com.example.proglayout.csound.channel.IntegerOutput;
import com.example.proglayout.csound.channel.Output;

public class CachedOutputNamesInt implements Output {
	
	private IntegerOutput mChn;
	private TextView mUnit;
	
	public CachedOutputNamesInt(String id, TextView unit) {
		mChn = new IntegerOutput(id);		
		mUnit = unit;		
	}
	
	@Override
	public void update() {		
		if (mChn.hasNext()) {
			int i = mChn.getValue();			
			mUnit.setText(Integer.toString(i));		
		}				
	}
	
	public void addToCsound(Player app) {
		app.getCsoundObj().addValueCacheable(mChn);
		app.addOutput(this);
	}
}
