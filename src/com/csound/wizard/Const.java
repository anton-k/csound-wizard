package com.csound.wizard;

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

	// ---------------------------------------------------------------------
	// Text 
	
	// text scale bounds

	public static final float
		minTextScale = 0,
		maxTextScale = 3;
	
	
	// ---------------------------------------------------------------------
	// Json names
	
	// Units
	
	public static final String
		TEXT = "text",
		BUTTON = "button",
		TOGGLE = "toggle",
		SLIDER = "slider",
		CHESS = "chess",
		KNOB = "knob",
		PLANE = "plane",
		PLANE_X = "plane-x",
		PLANE_Y = "plane-y",
		MULITOUCH = "mtouch",
		MULITOUCH_X = "mtouch-x",
		MULITOUCH_Y = "mtouch-y",
		MULITOUCH_CHESS = "mtouch-chess",
		HOR_RADIO = "hor-radio",
		VER_RADIO = "ver-radio",
		TAP = "tap",
		TAP_CLICK = "tap-click",
		TAP_TOGGLE = "tap-toggle",
		SPINNER = "spinner",
		INTS = "int",
		NAMES = "toggles",
				
		// groups
		
		HOR = "hor",
		VER = "ver",
		HOR_SCROLL = "hor-scroll",
		VER_SCROLL = "ver-scroll",
		TABLE = "table",
		OPTIONS = "options",
		TABS = "tabs",
		EMPTY = "empty",
		LINE = "line",
		
		// outputs
		
		OUT_SLIDER = "out-slider",
		OUT_KNOB = "out-knob",
		OUT_PLANE = "out-plane",
		RAINBOW_CIRCLE = "rainbow-circle",
		METER = "meter",
		CENTER_METER = "center-meter",
		CIRCLE_METER = "circle-meter",
		CENTER_CIRCLE_METER = "center-circle-meter",
		SHOW_NAMES = "show-names",
		SHOW_INTS = "show-ints",
		SHOW_FLOATS = "show-floats",
		
	// Fields
		
		// layout
			
		WIDTH = "width",
		HEIGHT = "height",
		GRAVITY = "gravity",
		WEIGHT = "weight",
		MARGIN = "margin",
		PADDING = "padding",
		ORIENT = "orient",
				
		// color
			
		FST_COLOR = "fst-color",
		SND_COLOR = "snd-color",
		BKG_COLOR = "bkg-color",
		
		// range
		
		RANGE = "range",
		RANGE_X = "range-x",
		RANGE_Y = "range-y",		
	
		// text
		
		TEXT_SIZE = "text-size",
		TEXT_SCALE = "text-scale",
		TEXT_COLOR = "text-color",
		TEXT_ALIGN = "text-align",	
		
		TOUCH_LIM = "touch-limit",
		
		// init
		
		INIT = "init";
	
	
		// -----------------------------------------------------------------------
		// UIs look
	
		public static int 
			desiredWidth = 500,
			desiredSliderWidth = desiredWidth,
			desiredSliderHeight = 80,		
			desiredCircleSize = 120,
			desiredLineHeight = 10;  
}
