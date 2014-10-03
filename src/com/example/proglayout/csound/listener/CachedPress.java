package com.example.proglayout.csound.listener;

import com.csounds.CsoundObj;
import com.example.proglayout.csound.channel.IntegerInput;
import com.example.proglayout.view.Listener.OnPress;
import com.example.proglayout.view.Listener.OnPressListener;

public class CachedPress {
	
	private OnPressListener mUnit;
	private IntegerInput mChannel;
	
	public CachedPress(String id, OnPressListener unit) {
		mChannel = new IntegerInput(id, 0);
		mUnit = unit;
		
		mUnit.setOnPressListener(new OnPress() {
			@Override
			public void press() {
				mChannel.setValue(1);			
			}

			@Override
			public void release() {
				mChannel.setValue(0);				
			}
		});
	}
	
	public void addToCsound(CsoundObj csd) {
		csd.addValueCacheable(mChannel);		
	}
}
