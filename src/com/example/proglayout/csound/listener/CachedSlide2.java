package com.example.proglayout.csound.listener;

import com.csounds.CsoundObj;
import com.example.proglayout.Utils;
import com.example.proglayout.csound.channel.DoubleInput;
import com.example.proglayout.view.Listener.OnSlide2;
import com.example.proglayout.view.Listener.OnSlide2Listener;

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
