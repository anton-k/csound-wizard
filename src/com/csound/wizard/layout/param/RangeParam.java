package com.csound.wizard.layout.param;

import java.io.Serializable;

import org.json.simple.JSONObject;

import com.csound.wizard.Const;
import com.csound.wizard.layout.Json;
import com.csound.wizard.layout.param.Types.Range;

public class RangeParam  implements Serializable {
	private static final long serialVersionUID = 270578162172330493L;
	
	private Range mRange, mRangeX, mRangeY;
	
	public RangeParam() {
		this(null, null, null);
	}
	
	public RangeParam(Range x) {
		this(x, null, null);		
	}
	
	public RangeParam(Range x, Range y) {
		this(null, x, y);
	}
	
	public RangeParam(Range xy, Range x, Range y) {
		mRange = xy;
		mRangeX = x;
		mRangeY = y;
	}
	
	public Range getRange() { return mRange; }
	public Range getRangeX() { return mRangeX; }
	public Range getRangeY() { return mRangeY; }
	
	public int getIntRange() { return (int) mRange.getMax(); }
	public int getIntRangeX() { return (int) mRangeX.getMax(); }
	public int getIntRangeY() { return (int) mRangeY.getMax(); }

	
	public static RangeParam parse(JSONObject obj) {
		return new RangeParam(
				parseRangeAt(Const.RANGE, obj),
				parseRangeAt(Const.RANGE_X, obj),
				parseRangeAt(Const.RANGE_Y, obj));
	}
	
	public static RangeParam merge(RangeParam a, RangeParam b) {
		if (a == null) {
			return b;
		}
		
		if (b == null) {
			return a;
		}
										
		return new RangeParam(
			(Range) Param.mergeObjects(a.mRange, b.mRange),
			(Range) Param.mergeObjects(a.mRangeX, b.mRangeX),
			(Range) Param.mergeObjects(a.mRangeY, b.mRangeY));
	}
	
	private static Range parseRangeAt(String key, JSONObject obj) {
		Object x = Json.getJson(key, obj);
		if (x != null) {
			return Range.parse(x);
		} 
		return new Range();
	}	

}	