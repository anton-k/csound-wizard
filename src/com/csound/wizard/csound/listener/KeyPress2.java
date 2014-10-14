package com.csound.wizard.csound.listener;

import java.util.Locale;

import com.csound.wizard.view.Listener.OnKeyPress2;
import com.csound.wizard.view.Listener.OnKeyPress2Listener;
import com.csounds.CsoundObj;
import com.csounds.valueCacheable.AbstractValueCacheable;

public class KeyPress2 extends AbstractValueCacheable {
		
	private OnKeyPress2Listener mUnit;
	private CsoundObj mCsoundObj;
	private String mInstrName;		
	private int mnx, mny;
	private boolean[] mIsOns;
	private boolean needToFireOns;
	
	private int mMaxZeroes;
	
		
	public KeyPress2(int instrId, int nx, int ny, boolean[] isOns, OnKeyPress2Listener unit) {			
		mInstrName = Integer.toString(instrId);	
		mnx = nx;
		mny = ny;
		mIsOns = isOns;
		mMaxZeroes = countZeroes(mnx * mny);
		
		mUnit = unit;		
		mUnit.setOnKeyPress2Listener(new OnKeyPress2() {
			
			@Override
			public void on(int x, int y) {	
				mCsoundObj.sendScore(onMsg(x, y));				
			}

			@Override
			public void off(int x, int y) {	
				mCsoundObj.sendScore(offMsg(x, y));				
			}			
		});
	}	
	
	public void addToCsound(CsoundObj csd) {
		mCsoundObj = csd;	
		csd.addValueCacheable(this);
	}	
	
	private String onMsg(int x, int y) {		
		return String.format(Locale.ROOT, "i %s.%s 0 -2 %d %d", mInstrName, getNoteId(x, y), x, y);
	}
	
	private String offMsg(int x, int y) {
		return String.format(Locale.ROOT, "i -%s.%s 0 0 %d %d", mInstrName, getNoteId(x, y), x, y);
	}
	
	private String getNoteId(int x, int y) {
		return addZeroes(x * mny + y);
	}
	
	private String addZeroes(int n) {
		int numOfZeroes = mMaxZeroes - countZeroes(n);
		String pref = "";
		while (numOfZeroes >= 0) {
			pref = pref + '0';
			numOfZeroes--;	
		}		
		return pref + Integer.toString(n);	
	}
	
	private int countZeroes(int n) {
		int res = 0;
		while (n > 0) {
			n = n / 10;
			res++;
		}
		return res;
	}
	
	@Override
	public void setup(CsoundObj csoundObj) {
		needToFireOns = true;		
	}
	
	@Override
	public void updateValuesToCsound() {
		if (needToFireOns) {
			for (int i = 0; i < mIsOns.length; i++) {
				if (mIsOns[i]) {
					int x = i / mny;
					int y = i % mny;
					mCsoundObj.sendScore(onMsg(x, y));					
				}
			}			
			needToFireOns = false;
		}
	}
	
	@Override
	public void cleanup() {
				
	}
}
	
