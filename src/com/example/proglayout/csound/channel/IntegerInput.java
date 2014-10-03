package com.example.proglayout.csound.channel;

import com.csounds.CsoundObj;
import com.csounds.valueCacheable.AbstractValueCacheable;

import csnd6.CsoundMYFLTArray;
import csnd6.controlChannelType;

public class IntegerInput extends AbstractValueCacheable {
	private String mChannelName;
	protected int mCachedValue;
	boolean mCacheDirty;
	CsoundMYFLTArray mPtr;
	
	public IntegerInput(String channelName, int initVal) {		
		mChannelName = channelName;	
		mCachedValue = initVal;
	}
	
	public void setValue(int x) {
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
