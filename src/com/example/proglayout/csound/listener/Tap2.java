package com.example.proglayout.csound.listener;

import java.util.Locale;

import com.csounds.CsoundObj;
import com.example.proglayout.view.Listener.OnTap2;
import com.example.proglayout.view.Listener.OnTap2Listener;

public class Tap2 {
		
	private OnTap2Listener mUnit;
	private CsoundObj mCsoundObj;
	private String mInstrName;		
		
	public Tap2(int instrId, OnTap2Listener unit) {			
		mInstrName = Integer.toString(instrId);			
		
		
		mUnit = unit;
		
		mUnit.setOnTap2Listener(new OnTap2() {
			
			@Override
			public void tap(int x, int y) {	
				mCsoundObj.sendScore(onMsg(x, y));				
			}
			
		});
	}
	
	
	public void addToCsound(CsoundObj csd) {
		mCsoundObj = csd;		
	}	
	
	private String onMsg(int x, int y) {
		return String.format(Locale.ROOT, "i %s 0 0 %d %d", mInstrName, x, y);
	}
}
	
