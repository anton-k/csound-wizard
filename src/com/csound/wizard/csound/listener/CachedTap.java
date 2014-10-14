package com.csound.wizard.csound.listener;

import com.csound.wizard.csound.channel.IntegerInput;
import com.csound.wizard.view.Listener.OnTap;
import com.csound.wizard.view.Listener.OnTapListener;
import com.csounds.CsoundObj;

public class CachedTap {
	
	IntegerInput mChannel;
	OnTapListener mUnit;
	
	public CachedTap(String id, int initVal, OnTapListener unit) {
		mChannel = new IntegerInput(id, initVal);
		mUnit = unit;
		
		mUnit.setOnTapListener(new OnTap() {
			@Override
			public void tap(int value) {
				mChannel.setValue(value);				
			}			
		});
	}
	
	public void addToCsound(CsoundObj csd) {
		csd.addValueCacheable(mChannel);		
	}	
}

