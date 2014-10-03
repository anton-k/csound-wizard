package com.example.proglayout.view.param;

import java.io.Serializable;

import org.json.simple.JSONObject;
import android.graphics.Color;
import android.graphics.Paint.Align;

import com.example.proglayout.Const;
import com.example.proglayout.layout.Json;

public class TextParam  implements Serializable {
	private static final long serialVersionUID = -6926468613279426022L;
	
	private Integer mSize;
	private Integer mColor;
	private Align mAlign;
	private Float mScale;
	
	public TextParam() {
		mSize = 35;
		mColor = Const.getColor("black");
		mAlign = Align.CENTER;
		mScale = 1f;
	}		
	
	public TextParam(Integer size, Integer color, Align align, Float scale) {
		mSize = size;			
		mColor = color;
		mAlign = align;	
		mScale = scale;
	}
	
	public TextParam(Integer size, String color, String align, Float scale) {
		mSize = size;			
		mColor = (color != null) ? ColorParam.parseColor(color) : Color.BLACK;
		mScale = scale;
		
		mAlign = Align.CENTER;
		if (align != null) {
			if (align.equals("center")) {
				mAlign = Align.CENTER;
			} else if (align.equals("left")) {
				mAlign = Align.LEFT;
			} else if (align.equals("rght")) {
				mAlign = Align.RIGHT;
			}
		}			
	}
	
	public Integer getSize()  { return mSize; }
	public Integer getColor() { return mColor; }	
	public Align   getAlign() { return mAlign; }
	public Float   getScale() { return mScale; }

	
	public void setSize(int n) { mSize = n;	}
	public void setColor(int n) { mColor = n;	}
	public void setAlign(Align n) { mAlign = n;	}	
	public void setScale(float n) { mScale = n;	}
				
	public static TextParam parse(JSONObject obj) {		
		return new TextParam(
			Json.getInteger("text-size", obj),
			Json.getString("text-color", obj),
			Json.getString("text-align", obj),
			Json.getFloat("text-scale", obj));				
	}

	public static TextParam merge(TextParam a, TextParam b) {
		if (a == null) {
			return b;
		}
		
		if (b == null) {
			return a;
		}		
					
		return new TextParam(
			(Integer) Param.mergeObjects(a.mSize, b.mSize),
			(Integer) Param.mergeObjects(a.mColor, b.mColor),
			(Align)   Param.mergeObjects(a.mAlign, b.mAlign),
			(Float)   Param.mergeObjects(a.mScale, b.mScale));
	}	
}