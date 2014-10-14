package com.csound.wizard.csound.listener;

import java.util.Locale;

import com.csound.wizard.Utils;
import com.csound.wizard.view.Listener.OnKey2;
import com.csound.wizard.view.Listener.OnKey2Listener;
import com.csounds.valueCacheable.AbstractValueCacheable;


import com.csounds.CsoundObj;

import csnd6.CsoundMYFLTArray;
import csnd6.controlChannelType;

public class Key2 extends AbstractValueCacheable {
		
	private OnKey2Listener mUnit;
	private CsoundObj mCsoundObj;
	private String mInstrName, mTouchValName;
	
	private int mMaxTouch = 10;
	private double[] valX, valY;
	private CsoundMYFLTArray[] ptrX, ptrY;	
	private boolean mIsOn = false;
	
	public Key2(int instrId, int maxTouch, OnKey2Listener unit) {			
		mInstrName = Integer.toString(instrId);
		mTouchValName = Utils.addSuffix("touch", mInstrName);	
		
		mMaxTouch = maxTouch;
		mUnit = unit;
		ptrX = new CsoundMYFLTArray[maxTouch];
		ptrY = new CsoundMYFLTArray[maxTouch];
		valX = new double[maxTouch];
		valY = new double[maxTouch];
		
		mUnit.setOnKey2Listener(new OnKey2() {
			
			@Override
			public void on(int key, float x, float y) {				
				valX[key] = x;
				valY[key] = y;
				if (mIsOn) {
					ptrX[key].SetValue(0, valX[key]);
					ptrY[key].SetValue(0, valY[key]);
					mCsoundObj.sendScore(onMsg(key));
				}
			}
			
			@Override
			public void move(int key, float x, float y) {
				valX[key] = x;
				valY[key] = y;
			}
			
			@Override
			public void off(int key) {
				mCsoundObj.sendScore(offMsg(key));				
			}
			
		});
	}
	
	
	public void addToCsound(CsoundObj csd) {
		mCsoundObj = csd;
		csd.addValueCacheable(this);
	}	
	
	private String onMsg(int key) {
		return String.format(Locale.ROOT, "i %s.%d 0 -2 %d", mInstrName, key, key);
	}
	
	private String offMsg(int key) {		
		return String.format(Locale.ROOT, "i -%s.%d 0 0 %d", mInstrName, key, key);
	}
	
	// ------------------------------------------------------------------------------
	// Value cacheable
	
	@Override
	public void setup(CsoundObj csoundObj) {
		String xName, yName;
		for(int i = 0; i < mMaxTouch; i++) {
			xName = Utils.addSuffix(Utils.addSuffix(mTouchValName, Integer.toString(i)), "x");
			yName = Utils.addSuffix(Utils.addSuffix(mTouchValName, Integer.toString(i)), "y");
			
			ptrX[i] = csoundObj.getInputChannelPtr(xName, controlChannelType.CSOUND_CONTROL_CHANNEL);
			ptrY[i] = csoundObj.getInputChannelPtr(yName, controlChannelType.CSOUND_CONTROL_CHANNEL);
		}	
		mIsOn = true;
	}
	
	@Override
	public void updateValuesToCsound() {
		for(int i = 0; i < mMaxTouch; i++) {
			ptrX[i].SetValue(0, valX[i]);
			ptrY[i].SetValue(0, valY[i]);
		}
	}
	
	@Override
	public void cleanup() {
		for(int i = 0; i < mMaxTouch; i++) {
			ptrX[i].Clear();
			ptrX[i] = null;
			ptrY[i].Clear();
			ptrY[i] = null;
		}		
		mIsOn = false;
	}

}
