package com.example.proglayout.csound.listener;

import com.csounds.CsoundObj;
import com.example.proglayout.csound.channel.DoubleInput;
import com.example.proglayout.view.Listener.OnSlide;
import com.example.proglayout.view.Listener.OnSlideListener;

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
