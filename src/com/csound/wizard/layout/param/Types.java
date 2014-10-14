package com.csound.wizard.layout.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

import com.csound.wizard.layout.Json;

public class Types {
	
	
	public static class Id {		
		public static String parse(Object obj) {
			if (Json.isString(obj)) {
				return (String) obj;
			}
			return null;
		}		
	}	
	
	public static class InstrId {		
		public static Integer parse(Object obj) {
			if (Json.isNumber(obj)) {
				return Json.getInt(obj);
			}
			return null;
		}		
	}
	
	public static class Range  implements Serializable {
		private static final long serialVersionUID = -2554371743232498399L;
		
		private float mMin, mMax;
		private float mDelta;
		
		public Range() {
			this(0, 1);			
		}
		
		public Range(float max) {
			this(0, max);
		}	
		
		public Range(float min, float max) {
			mMin = min;
			mMax = max;
			mDelta = max - min;
		}
		
		public float getMin() { return mMin; }
		public float getMax() { return mMax; }
		
		
		public static Range parse(Object obj) {		
			if (Json.isArray(obj)) {
				JSONArray arr = (JSONArray) obj;
				if (arr.size() == 2 && Json.isNumber(arr.get(0)) && Json.isNumber(arr.get(1))) {
					float
						min = Json.getFloat(arr.get(0)),
						max = Json.getFloat(arr.get(1));
					return new Range(min, max);
				}				
			}			
			
			if (Json.isNumber(obj)) {
				return new Range(Json.getFloat(obj));
			}
			
			return new Range();		
		}
		
		public float fromRelative(float x) {
			return mMin + x * mDelta;
		}
		
		public float toRelative(float x) {
			return (x - mMin) / mDelta;
		}
	}
	
	
	public static class Names implements Serializable {
		private static final long serialVersionUID = -2533990134597683945L;
		
		private List<String> mNames;	
		
		public Names() {
			this(null);
		}
		
		public Names(List<String> names) {
			mNames = names;
		}
		
		public List<String> getNames() { return mNames; }
		
		public static Names parse(Object obj) {
			return new Names(getNamesFromObject(obj));
		}
		
		private static List<String> getNamesFromObject(Object obj) {
			if (Json.isArray(obj)) {
				JSONArray names = (JSONArray) obj;
				List<String> res = new ArrayList<String>();
				for (Object s: names) {
					res.add(s.toString());
				}
				return res;				
			}
			
			return new ArrayList<String>();
		}		
			
	}
	
	
	public static class Sides implements Serializable {
		private static final long serialVersionUID = -1200783634772009816L;
		
		private Integer mLeft, mTop, mRight, mBottom;	
		
		public Sides(Integer val) {
			mLeft = val;
			mRight = val;
			mTop = val;
			mBottom = val;			
		}
		
		public Sides(Integer left, Integer right, Integer top, Integer bottom) {
			mLeft = left;
			mRight = right;
			mTop = top;
			mBottom = bottom;
		}	
		
		public Integer getLeft() 	{ return mLeft; }
		public Integer getTop()     { return mTop; }
		public Integer getRight()   { return mRight; }
		public Integer getBottom()  { return mBottom; }
		
		public void setLeft(int n)  { mLeft = n; }  
		public void setRight(int n) { mRight = n; }
		public void setTop(int n)   { mTop = n; }
		public void setBottom(int n){ mBottom = n; }
			
		public static Sides merge(Sides a, Sides b) {
			if (a == null) {
				return b;
			}
			
			if (b == null) {
				return a;
			}
			
			return new Sides(
				(Integer) Param.mergeObjects(a.mLeft, b.mLeft),
				(Integer) Param.mergeObjects(a.mRight, b.mRight),
				(Integer) Param.mergeObjects(a.mTop, b.mTop),
				(Integer) Param.mergeObjects(a.mBottom, b.mBottom));
		}

			
	}

}
