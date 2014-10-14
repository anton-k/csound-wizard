package com.csound.wizard.csound.channel;

import com.csounds.CsoundObj;
import com.csounds.valueCacheable.AbstractValueCacheable;

import csnd6.CsoundMYFLTArray;
import csnd6.controlChannelType;

public class DoubleInput extends AbstractValueCacheable {
	private String mChannelName;
	protected double mCachedValue;
	boolean mCacheDirty;
	CsoundMYFLTArray mPtr;
	
	public DoubleInput(String channelName, double initVal) {		
		mChannelName = channelName;	
		mCachedValue = initVal;
	}
	
	public void setValue(double x) {
		if (mCachedValue != x) {
			mCachedValue = x;
			mCacheDirty = true;			
		}
	}
	
	@Override
	public void setup(CsoundObj csoundObj) {			
		mCacheDirty = true;		
		mPtr = csoundObj.getInputChannelPtr(mChannelName, controlChannelType.CSOUND_CONTROL_CHANNEL);
	}
	
	@Override
	public void updateValuesToCsound() {
		if (mCacheDirty) {
			if(mPtr != null) mPtr.SetValue(0, mCachedValue);
			mCacheDirty = false;
		}
	}

	@Override
	public void cleanup() {		
		mPtr.Clear();
		mPtr = null;
	}
}
