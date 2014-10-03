package com.example.proglayout;

import java.io.Serializable;

import android.graphics.Paint.Align;

import com.example.proglayout.view.param.Param;

public class Settings implements Serializable {
	private static final long serialVersionUID = 4462150475964240694L;
	
	private Param defaultParam;	
		
	public Settings() {
		defaultParam = new Param();
	}	
	
	public Param getParam() {
		return defaultParam;
	}
	
	// -----------------------------------------------
	// Text
	
	// text size
	
	public int getTextSize() {
		return defaultParam.getText().getSize();
	}
	
	public void setTextSize(int n) {
		defaultParam.getText().setSize(n);		
	}
	
	// text scale

	public float getTextScale() {
		return defaultParam.getText().getScale();
	}

	public void setTextScale(float n) {
		defaultParam.getText().setScale(n);		
	}
	
	// text color
	
	public int getTextColor() {
		return defaultParam.getText().getColor();
	}

	public void setTextColor(int n) {
		defaultParam.getText().setColor(n);		
	}	

	// text align

	public Align getTextAlign() {
		return defaultParam.getText().getAlign();
	}

	public void setTextAlign(Align n) {
		defaultParam.getText().setAlign(n);		
	}
	
	// -----------------------------------------------
	// Color
	
	// fst color
	
	public int getFstColor() {
		return defaultParam.getColor().getFstColor();
	}

	public void setFstColor(int n) {
		defaultParam.getColor().setFstColor(n);		
	}	
	
	// snd color
	
	public int getSndColor() {
		return defaultParam.getColor().getSndColor();
	}

	public void setSndColor(int n) {
		defaultParam.getColor().setSndColor(n);		
	}	
		
	// bkg color
	
	public int getBkgColor() {
		return defaultParam.getColor().getBkgColor();
	}

	public void setBkgColor(int n) {
		defaultParam.getColor().setBkgColor(n);		
	}

	
	// -----------------------------------------------
	// Layout
	
		
	// margins

	// left
	
	public int getMarginLeft() {
		return defaultParam.getLayout().getMargin().getLeft();
	}
	
	public void setMarginLeft(int n) {
		defaultParam.getLayout().getMargin().setLeft(n);		
	}	
	
	// right

	public int getMarginRight() {
		return defaultParam.getLayout().getMargin().getRight();
	}
	
	public void setMarginRight(int n) {
		defaultParam.getLayout().getMargin().setRight(n);		
	}	

	// top
	
	public int getMarginTop() {
		return defaultParam.getLayout().getMargin().getTop();
	}
	
	public void setMarginTop(int n) {
		defaultParam.getLayout().getMargin().setTop(n);		
	}	

	// bottom
	
	public int getMarginBottom() {
		return defaultParam.getLayout().getMargin().getBottom();
	}
	
	public void setMarginBottom(int n) {
		defaultParam.getLayout().getMargin().setBottom(n);		
	}	

	
}
