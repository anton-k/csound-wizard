package com.csound.wizard.csound.listener;

import com.csound.wizard.Utils;
import com.csound.wizard.csound.channel.DoubleInput;
import com.csound.wizard.view.Listener.OnSlide2;
import com.csound.wizard.view.Listener.OnSlide2Listener;
import com.csounds.CsoundObj;

public class CachedSlide2 {
	
	private DoubleInput mx, my;
	private OnSlide2Listener mUnit;
	
	public CachedSlide2(String id, double initX, double initY, OnSlide2Listener unit) {
		mx = new DoubleInput(Utils.addSuffix(id, "x"), initX);
		my = new DoubleInput(Utils.addSuffix(id, "y"), initY);
		mUnit = unit;
		
		mUnit.setOnSlide2Listener(new OnSlide2() {
			
			@Override
			public void slide(float x, float y) {
				mx.setValue(x);
				my.setValue(y);
			}
			
		});
	}

	public void addToCsound(CsoundObj csd) {
		csd.addValueCacheable(mx);		
		csd.addValueCacheable(my);
	}	
	
}
