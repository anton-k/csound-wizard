package com.example.proglayout.csound.channel;

import com.csounds.CsoundObj;
import com.csounds.valueCacheable.AbstractValueCacheable;

import csnd6.CsoundMYFLTArray;
import csnd6.controlChannelType;

public class IntegerOutput extends AbstractValueCacheable {
	private String mChannelName;
	protected int mCachedValue;
	private boolean mCacheDirty;
	
	CsoundMYFLTArray mPtr;
	
	public IntegerOutput(String channelName) {		
		mChannelName = channelName;	
		mCachedValue = 0;
	}
	
	public boolean hasNext() {
		return mCacheDirty;
	}
	
	public int getValue() {
		mCacheDirty = false;
		return mCachedValue;
	}
	
	@Override
	public void setup(CsoundObj csoundObj) {			
		mCacheDirty = true;		
		mPtr = csoundObj.getOutputChannelPtr(mChannelName, controlChannelType.CSOUND_CONTROL_CHANNEL);
		if (mPtr != null) {
			mCachedValue = (int) mPtr.GetValue(0);
		}
	}
	
	@Override
	public void updateValuesFromCsound() {	
		int x; 

		if (mPtr != null) {
			x = (int) mPtr.GetValue(0);
		} else {
			return;
		}
				
		if (mCachedValue != x) {
			mCachedValue = x;
			mCacheDirty = true;
		}
	}

	@Override
	public void cleanup() {		
		mPtr.Clear();
		mPtr = null;
	}
}
