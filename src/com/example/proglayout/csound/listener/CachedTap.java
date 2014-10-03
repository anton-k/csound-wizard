package com.example.proglayout.csound.listener;

import com.csounds.CsoundObj;
import com.example.proglayout.csound.channel.IntegerInput;
import com.example.proglayout.view.Listener.OnTap;
import com.example.proglayout.view.Listener.OnTapListener;

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

