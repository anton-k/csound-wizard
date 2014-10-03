package com.example.proglayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.util.SparseIntArray;

public class Const {
	
	// ----------------------------------------------------------------
	// Colors
	
	public static final String[] colorNames = {
			"navy", "blue", "aqua", "teal", "olive", "green", 
			"lime", "yellow", "orange", "red", "maroon", "fuchsia", 
			"purple", "black", "gray", "silver", "white", "transparent" };
		
	public static final String[] colorValues = {
			"#001F3F", "#0074D9", "#7FDBFF", "#39CCCC",	"#3D9970",
			"#2ECC40", "#01FF70", "#FFDC00", "#FF851B",	"#FF4136",
			"#85144B", "#F012BE", "#B10DC9", "#111111",	"#AAAAAA",
			"#DDDDDD", "#FFFFFF", "#00000000" };
	
	public static HashMap<String,String> stdColors; 

	static {
			stdColors = new HashMap<String,String>();

			stdColors.put("navy", "#001F3F");
			stdColors.put("blue", "#0074D9");
			stdColors.put("aqua", "#7FDBFF");
			stdColors.put("teal", "#39CCCC");
			stdColors.put("olive", "#3D9970");
			stdColors.put("green", "#2ECC40");
			stdColors.put("lime", "#01FF70");
			stdColors.put("yellow", "#FFDC00");
			stdColors.put("orange", "#FF851B");
			stdColors.put("red", "#FF4136");
			stdColors.put("maroon", "#85144B");
			stdColors.put("fuchsia", "#F012BE");
			stdColors.put("purple", "#B10DC9");
			stdColors.put("black", "#111111");
			stdColors.put("gray", "#AAAAAA");
			stdColors.put("silver", "#DDDDDD");
			stdColors.put("white", "#FFFFFF");
			stdColors.put("transparent", "#00000000");
			
	}

	public static final List<String> colorNameList = new ArrayList<String>(Arrays.asList(colorNames));
	
	public static final List<Integer> colorValueList;
	
	static {
		colorValueList = new ArrayList<Integer>();
		
		for (String val: colorValues) {
			colorValueList.add(Color.parseColor(val));		
		}
	}
	
	public static int getColor(String name) {
		return Color.parseColor(stdColors.get(name));		
	}
	
	
	private static final SparseIntArray colorPositions;
	
	static {
		colorPositions = new SparseIntArray();
		
		for (int i = 0; i < colorValues.length; i++) {
			colorPositions.put(Color.parseColor(colorValues[i]), i);			
		}
	}
	
	public static int getColorPosition(int color) {
		return colorPositions.get(color);
	}	

	// Text 
	
	// text scale bounds

	public static final float
		minTextScale = 0,
		maxTextScale = 3;
	
}
