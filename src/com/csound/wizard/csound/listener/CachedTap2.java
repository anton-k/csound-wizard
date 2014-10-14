package com.csound.wizard.csound.listener;

import com.csound.wizard.Utils;
import com.csound.wizard.csound.channel.IntegerInput;
import com.csound.wizard.view.Listener.OnTap2;
import com.csound.wizard.view.Listener.OnTap2Listener;
import com.csounds.CsoundObj;

public class CachedTap2 {
	
	private IntegerInput mx, my;
	private OnTap2Listener mUnit;
	
	public CachedTap2(String id, int initX, int initY, OnTap2Listener unit) {
		mx = new IntegerInput(Utils.addSuffix(id, "x"), initX);
		my = new IntegerInput(Utils.addSuffix(id, "y"), initY);
		mUnit = unit;
		
		mUnit.setOnTap2Listener(new OnTap2() {
			
			@Override
			public void tap(int x, int y) {
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
