package com.example.proglayout.csound.listener;

import com.example.proglayout.Player;
import com.example.proglayout.csound.channel.DoubleOutput;
import com.example.proglayout.csound.channel.Output;
import com.example.proglayout.view.Listener.SetSlide;

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
