package com.example.proglayout.view.param;

import java.io.Serializable;

import org.json.simple.JSONObject;

import com.example.proglayout.layout.Json;

public class Param implements Serializable {	
	private static final long serialVersionUID = -3483730145256635807L;
	
	private ColorParam mColor;
	private LayoutParam mLayout;
	private TextParam mText;
	private RangeParam mRange;
	private NamesParam mNames;
	private TouchParam mTouch;
	
	public ColorParam getColor() 	{ return mColor; }
	public LayoutParam getLayout() 	{ return mLayout; }
	public TextParam getText() 		{ return mText; }
	public RangeParam getRange()    { return mRange; }
	public NamesParam getNames()    { return mNames; }
	public TouchParam getTouch()    { return mTouch; }
	
	public Param() {
		mColor = new ColorParam();
		mLayout = new LayoutParam();
		mText = new TextParam();
		mRange = new RangeParam();
		mNames = new NamesParam();
		mTouch = new TouchParam();
	}
	
	public Param(LayoutParam layout, ColorParam color, TextParam text, RangeParam range, NamesParam names, TouchParam touch) {
		mLayout = layout;
		mColor = color;
		mText = text;
		mRange = range;
		mNames = names;
		mTouch = touch;
	}	
			
	public static Param parse(Object obj) {
		if (Json.isObject(obj)) {
			JSONObject jobj = (JSONObject) obj;
			
			LayoutParam layout = LayoutParam.parse(jobj);
			ColorParam color = ColorParam.parse(jobj);
			TextParam text = TextParam.parse(jobj);
			RangeParam range = RangeParam.parse(jobj);
			NamesParam names = NamesParam.parse(jobj);
			TouchParam touch = TouchParam.parse(jobj);
			
			return new Param(layout, color, text, range, names, touch);
		}
		
		return null;
	}
	public static Param merge(Param a, Param b) {
		if (a == null) {
			return b;
		}
		
		if (b == null) {
			return a;
		}
		
		return new Param(
				LayoutParam.merge(a.mLayout, b.mLayout),
				ColorParam.merge(a.mColor, b.mColor),
				TextParam.merge(a.mText, b.mText),
				RangeParam.merge(a.mRange, b.mRange),
				NamesParam.merge(a.mNames, b.mNames),
				TouchParam.merge(a.mTouch, b.mTouch));	
	}
	
	public static Object mergeObjects(Object a, Object b) {
		if (a != null) {
			return a;
		} else {
			return b;
		}
	}	
}
















