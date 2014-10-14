package com.csound.wizard.layout;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class LayoutParam {
	public static final String 
		LAYOUT = "layout",
		PADDING = "padding",
		MARGIN = "margin",
		
		FILL = "fill",
		WRAP = "wrap",	
	
		// orientation
		TOP = "top",
		BOTTOM = "bottom",
		CENTER = "center",
		RIGHT = "right",
		LEFT = "left",		
		CENTER_HORIZONTAL = "center-hor",
		CENTER_VERTICAL = "center-ver";		
	
	private int[] margin = {0, 0, 0, 0};
	private int[] padding = {0, 0, 0, 0};
	
	private int width = 0;
	private int height = 0;
	
	private int gravity = Gravity.NO_GRAVITY;
	private float weight = 0;
	
	private Boolean 
		isSetMargin = false, 
		isSetPadding = false, 
		isSetLayout = false,		 
		isSetGravity = false, 
		isSetWeight = false;
	
	public static void set(JSONObject obj, View v) {
		LayoutParam lp = new LayoutParam(obj);
		lp.setLayout(v);
	}
	
	private LayoutParam(JSONObject obj) {
		if (obj.containsKey(PADDING)) {			
			isSetPadding = decodeBorders(padding, obj.get(PADDING));			
		}
		
		if (obj.containsKey(MARGIN)) {
			isSetMargin = decodeBorders(margin, obj.get(MARGIN));			
		}
		
		if (obj.containsKey(LAYOUT)) {
			decodeLayout(obj.get(LAYOUT));
		}
	}
	
	private void setLayout(View v) {
		if (isSetPadding) {
			v.setPadding(padding[0], padding[1], padding[2], padding[3]);
		}		
		LinearLayout.LayoutParams lp;		
		if (isSetLayout) {
			lp = new LinearLayout.LayoutParams(width, height);
		} else {
			lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);			
		}
		
		if (isSetGravity) {
			lp.gravity = gravity;
		}
		
		if (isSetWeight) {
			lp.weight = weight;
		}
		
		if (isSetMargin) {
			lp.setMargins(margin[0], margin[1], margin[2], margin[3]);
		}
		
		v.setLayoutParams(lp);
	}
	
	// ----------------------------------------------------------------
	
	void decodeLayout(Object obj) {
		if (Json.isArray(obj)) {
			JSONArray arr = (JSONArray) obj;
			if (arr.size() == 0) {						
			} else if (arr.size() == 1) {
				int n = decodeLayoutParam(arr.get(0));
				width = n; 
				height = n;
				isSetLayout = true;
			} else if (arr.size() == 2) {
				width = decodeLayoutParam(arr.get(0));
				height = decodeLayoutParam(arr.get(1));	
				isSetLayout = true;
			} else if (arr.size() == 3 && Json.isNumber(arr.get(2))) {
				width = decodeLayoutParam(arr.get(0));
				height = decodeLayoutParam(arr.get(1));
				weight = Json.getFloat(arr.get(2));
				isSetLayout = true;
				isSetWeight = true;
			} else if (arr.size() > 3 && Json.isNumber(arr.get(2)) && Json.isString(arr.get(3))) {
				width = decodeLayoutParam(arr.get(0));
				height = decodeLayoutParam(arr.get(1));
				weight = Json.getFloat(arr.get(2));						
				gravity = decodeGravity((String) arr.get(3));
				isSetLayout = true;
				isSetWeight = true;
				isSetGravity = true;
			}
		} else {
			int n = decodeLayoutParam(obj);
			width = n; 
			height = n;
			isSetLayout = true;								
		}				
	}	
	
	Boolean decodeBorders(int[] res, Object obj) {
		if (Json.isNumber(obj)) {
			int n = Json.getInt(obj);
			for (int i = 0; i < 4; i++) {
				res[i] = n;
			}
			return true;
		} else if (Json.isArray(obj)) {
			JSONArray arr = (JSONArray) obj;
			if (arr.size() == 4 
					&& Json.isNumber(arr.get(0)) 
					&& Json.isNumber(arr.get(1))
					&& Json.isNumber(arr.get(2))
					&& Json.isNumber(arr.get(3))) {
				res[0] = Json.getInt(arr.get(0));
				res[1] = Json.getInt(arr.get(1));
				res[2] = Json.getInt(arr.get(2));
				res[3] = Json.getInt(arr.get(3));
				return true;
			} 		
		}
		return false;				
	}
	
	
	
	private static int decodeLayoutParam(Object obj) {
		if (Json.isNumber(obj)) {
			return Json.getInt(obj);
		} else if (Json.isString(obj)) {
			String s = (String) obj;			
			if (s.equals(FILL)) {
				return LayoutParams.MATCH_PARENT;				
			} else if (s.equals(WRAP)) {
				return LayoutParams.WRAP_CONTENT;			
			}								
		} 
		return LayoutParams.WRAP_CONTENT;
	}
	
	private static int decodeGravity(String s) {
		if (s.equals(TOP)) {
			return Gravity.TOP;
		} else if (s.equals(BOTTOM)) {
			return Gravity.BOTTOM;
		} else if (s.equals(CENTER)) {
			return Gravity.CENTER;
		} else if (s.equals(RIGHT)) {
			return Gravity.RIGHT;
		} else if (s.equals(LEFT)) {
			return Gravity.LEFT;
		} else if (s.equals(CENTER_HORIZONTAL)) {
			return Gravity.CENTER_HORIZONTAL;
		} else if (s.equals(CENTER_VERTICAL)) {
			return Gravity.CENTER_VERTICAL;
		}		
		return Gravity.NO_GRAVITY;
	}
}
