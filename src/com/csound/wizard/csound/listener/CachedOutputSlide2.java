package com.csound.wizard.csound.listener;

import com.csound.wizard.Player;
import com.csound.wizard.Utils;
import com.csound.wizard.csound.channel.DoubleOutput;
import com.csound.wizard.csound.channel.Output;
import com.csound.wizard.view.Listener.SetSlide2;

public class CachedOutputSlide2 implements Output {
	
	private DoubleOutput mChnX, mChnY;	
	private SetSlide2 mUnit;
	
	public CachedOutputSlide2(String id, SetSlide2 unit) {
		mChnX = new DoubleOutput(Utils.addSuffix(id, "x"));
		mChnY = new DoubleOutput(Utils.addSuffix(id, "y"));
		mUnit = unit;		
	}
	
	public void addToCsound(Player app) {		
		app.getCsoundObj().addValueCacheable(mChnX);
		app.getCsoundObj().addValueCacheable(mChnY);
		app.addOutput(this);
	}
		
	@Override
	public void update() {
		if (mChnX.hasNext() || mChnY.hasNext()) {
			mUnit.setSlide((float) mChnX.getValue(), (float) mChnY.getValue());
		}		
	}
	
}
