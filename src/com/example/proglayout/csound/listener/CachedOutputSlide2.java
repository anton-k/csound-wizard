package com.example.proglayout.csound.listener;

import com.example.proglayout.App;
import com.example.proglayout.Utils;
import com.example.proglayout.csound.channel.DoubleOutput;
import com.example.proglayout.csound.channel.Output;
import com.example.proglayout.view.Listener.SetSlide2;

public class CachedOutputSlide2 implements Output {
	
	private DoubleOutput mChnX, mChnY;	
	private SetSlide2 mUnit;
	
	public CachedOutputSlide2(String id, SetSlide2 unit) {
		mChnX = new DoubleOutput(Utils.addSuffix(id, "x"));
		mChnY = new DoubleOutput(Utils.addSuffix(id, "y"));
		mUnit = unit;		
	}
	
	public void addToCsound(App app) {		
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
