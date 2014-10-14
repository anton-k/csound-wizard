package com.csound.wizard.csound.listener;

import android.widget.TextView;

import com.csound.wizard.Player;
import com.csound.wizard.csound.channel.IntegerOutput;
import com.csound.wizard.csound.channel.Output;

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
