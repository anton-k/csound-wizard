package com.csound.wizard.csound.listener;

import com.csound.wizard.csound.channel.IntegerInput;
import com.csound.wizard.view.Listener.OnPress;
import com.csound.wizard.view.Listener.OnPressListener;
import com.csounds.CsoundObj;

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
