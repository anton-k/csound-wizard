package com.csound.wizard.layout.param;

import java.io.Serializable;
import java.util.HashMap;
import org.json.simple.JSONObject;
import android.graphics.Color;

import com.csound.wizard.Const;
import com.csound.wizard.layout.Json;

public class ColorParam implements Serializable {
	private static final long serialVersionUID = 2294150836982227464L;
	
	private Integer mBkg, mFst, mSnd;
	
	public ColorParam() {
		mFst = Const.getColor("aqua");			
		mSnd = Const.getColor("gray");
		mBkg = Const.getColor("transparent");
	}
	
	public ColorParam(Integer fst, Integer snd, Integer bkg) {
		mBkg = bkg;
		mFst = fst;
		mSnd = snd;
	}
	
	public ColorParam(String fst, String snd, String bkg) {
		mBkg = (bkg != null) ? parseColor(bkg) : null;
		mFst = (fst != null) ? parseColor(fst) : null;
		mSnd = (snd != null) ? parseColor(snd) : null;			
	}
	
	public Integer getBkgColor() { return mBkg; }
	public Integer getFstColor() { return mFst; }
	public Integer getSndColor() { return mSnd; }	
	
	public void setFstColor(int n) { mFst = n; }
	public void setSndColor(int n) { mSnd = n; }
	public void setBkgColor(int n) { mBkg = n; }
	
	public static int parseColor(String x) {
		if (x.length() > 0 && x.charAt(0) == '#') {
			return Color.parseColor(x);
		}
		
		if (stdColors.containsKey(x)) {
			return Color.parseColor(stdColors.get(x));
		}
		
		return Color.parseColor(x);
	}
	
	private static HashMap<String,String> stdColors = Const.stdColors;; 
	
	public static ColorParam parse(JSONObject obj) {			
		return new ColorParam(
			Json.getString(Const.FST_COLOR, obj),
			Json.getString(Const.SND_COLOR, obj),
			Json.getString(Const.BKG_COLOR, obj));					
	}

	public static ColorParam merge(ColorParam a, ColorParam b) {
		if (a == null) {
			return b;
		}
		
		if (b == null) {
			return a;
		}
		
		return new ColorParam(
			(Integer) Param.mergeObjects(a.mFst, b.mFst),				
			(Integer) Param.mergeObjects(a.mSnd, b.mSnd),
			(Integer) Param.mergeObjects(a.mBkg, b.mBkg));
	}
	
}
