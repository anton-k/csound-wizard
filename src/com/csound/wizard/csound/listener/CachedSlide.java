package com.csound.wizard.csound.listener;

import com.csound.wizard.csound.channel.DoubleInput;
import com.csound.wizard.view.Listener.OnSlide;
import com.csound.wizard.view.Listener.OnSlideListener;
import com.csounds.CsoundObj;

public class CachedSlide {
	
	DoubleInput mChannel;
	OnSlideListener mSlider;
	
	public CachedSlide(String id, double initVal, OnSlideListener slider) {
		mChannel = new DoubleInput(id, initVal);
		mSlider = slider;
		
		mSlider.setOnSlideListener(new OnSlide() {
			@Override
			public void slide(float value) {
				mChannel.setValue(value);				
			}			
		});
	}
	
	public void addToCsound(CsoundObj csd) {
		csd.addValueCacheable(mChannel);		
	}	
}
