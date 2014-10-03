package com.example.proglayout.view.param;

import java.io.Serializable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import com.example.proglayout.layout.Json;
import com.example.proglayout.view.param.Types.Sides;

public class LayoutParam  implements Serializable {
	private static final long serialVersionUID = -2136222645778979152L;

	public static enum LayoutSizeType { WRAP, FILL, NUMBER };
	
	public static class LayoutSize {
		private static final String 
			WRAP = "wrap",
			FILL = "fill";
		
		private Integer val;			
		private	LayoutSizeType sizeType;
		
		public Integer getNumber() {
			return val;				
		}
		
		public LayoutSizeType getType() {
			return sizeType;				
		}
		
		public LayoutSize(LayoutSizeType st, Integer num) { 
			val = num;
			sizeType = st; 
		}
		
		public static LayoutSize parse(Object obj) {
			if (obj != null) {
				if (Json.isString(obj)) {
					String str = (String) obj;
					if (str.equals(WRAP)) {
						return new LayoutSize(LayoutSizeType.WRAP, null);
					}
					
					if (str.equals(FILL)) {
						return new LayoutSize(LayoutSizeType.FILL, null);							
					}					
				}
				
				if (Json.isNumber(obj)) {
					return new LayoutSize(LayoutSizeType.NUMBER, Json.getInt(obj));
				}
			}
			
			return null;
		}

		public int getInteger() {
			switch (sizeType) {
				case NUMBER:
					return val;
				case FILL:
					return LayoutParams.MATCH_PARENT;
				case WRAP:
					return LayoutParams.WRAP_CONTENT;
				default:
					return LayoutParams.WRAP_CONTENT;
			}			
		}
	}
	
	private LayoutSize mWidth, mHeight;
	private Sides mPadding, mMargin;
	private Integer mGravity;
	private Float mWeight;
	private Boolean mOrient;
	
	public LayoutParam() {
		mWidth = null; 
		mHeight = null;
		mPadding = null; 
		mMargin = new Sides(12);	
		mGravity = null;
		mWeight = null;
		mOrient = null;
	}
	
	public LayoutParam(LayoutSize width, LayoutSize height, Sides padding, Sides margin, Integer gravity, Float weight, Boolean orient) {
		mWidth = width; 
		mHeight = height; 
		mPadding = padding; 
		mMargin = margin;
		mGravity = gravity;
		mWeight = weight;
		mOrient = orient;
	}
	
	public LayoutSize getWidth()   { return mWidth; }
	public LayoutSize getHeight()  { return mHeight; }
	public Sides getPadding() { return mPadding; }
	public Sides getMargin()  { return mMargin; }
	public Integer getGravity()   { return mGravity; }
	public Float getWeight()   { return mWeight; }
	public Boolean getOrient() { return mOrient; }
	
	public static LayoutParam parse(JSONObject obj) {		
		return new LayoutParam(
			LayoutSize.parse(Json.getJson("width", obj)),
			LayoutSize.parse(Json.getJson("height", obj)),
			getSides("padding", obj),
			getSides("margin", obj),
			parseGravity(Json.getString("gravity", obj)),
			Json.getFloat("weight", obj),
			Json.getBoolean("orient", obj));					
	}
	
	private static Integer parseGravity(String str) {
		/*
		The `gravity` is where container should stick to if there is
		more space then the element needs. It's for the alignment of the
		elements. We can use string constants: `"top"`, `"bottom"`, `"center"`,
		`"right"`, `"left"`, `"center-hor"`, `"center-ver"`
		*/
		if (str != null) {
			if (str.equals("top")) {
				return Gravity.TOP; 					
			} else if (str.equals("bottom")) {
				return Gravity.BOTTOM;
			} else if (str.equals("center")) {
				return Gravity.CENTER;
			} else if (str.equals("right")) {
				return Gravity.RIGHT;
			} else if (str.equals("left")) {
				return Gravity.LEFT;
			} else if (str.equals("center-hor")) {
				return Gravity.CENTER_HORIZONTAL;
			} else if (str.equals("center-ver")) {
				return Gravity.CENTER_VERTICAL;
			} else {
				return null;
			}				
		}
		return null;
	}
	
	private static Sides getSides(String key, JSONObject obj) {
		Object vals = Json.getJson(key, obj);
		if (Json.isNumber(vals)) {
			Integer a = Json.getInt(vals);
			return new Sides(a, a, a, a);
		}
		
		if (Json.isArray(vals)) {
			JSONArray arr = (JSONArray) vals;
			if (arr.size() == 2 && Json.isNumber(arr.get(0)) && Json.isNumber(arr.get(1))) {
				int x = Json.getInt(arr.get(0));
				int y = Json.getInt(arr.get(1));
				return new Sides(x, x, y, y);
			}
		}
		
		if (Json.isObject(vals)) {
			return new Sides(
				Json.getInteger("left", vals),						
				Json.getInteger("right", vals),
				Json.getInteger("top", vals),
				Json.getInteger("bottom", vals));
		}
		
		return null;
	}

	public static LayoutParam merge(LayoutParam a, LayoutParam b) {
		if (a == null) {
			return b;
		}
		
		if (b == null) {
			return a;
		}
										
		return new LayoutParam(
			(LayoutSize) Param.mergeObjects(a.mWidth, a.mWidth),
			(LayoutSize) Param.mergeObjects(a.mHeight, a.mHeight),
			Sides.merge(a.mPadding, b.mPadding),
			Sides.merge(a.mMargin, b.mMargin),
			(Integer) Param.mergeObjects(a.mGravity, a.mGravity),
			(Float) Param.mergeObjects(a.mWeight, a.mWeight),
			(Boolean) Param.mergeObjects(a.mOrient, a.mOrient));
	}

}
