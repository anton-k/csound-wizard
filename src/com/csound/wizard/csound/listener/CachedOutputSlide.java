package com.csound.wizard.csound.listener;

import com.csound.wizard.Player;
import com.csound.wizard.csound.channel.DoubleOutput;
import com.csound.wizard.csound.channel.Output;
import com.csound.wizard.view.Listener.SetSlide;

public class CachedOutputSlide implements Output {
	
	private DoubleOutput mChn;
	private SetSlide mUnit;
	
	public CachedOutputSlide(String id, SetSlide unit) {
		mChn = new DoubleOutput(id);
		mUnit = unit;		
	}
	
	public void addToCsound(Player app) {		
		app.getCsoundObj().addValueCacheable(mChn);
		app.addOutput(this);
	}
		
	@Override
	public void update() {
		if (mChn.hasNext()) {
			mUnit.setSlide((float) mChn.getValue());
		}		
	}
	
}
