package com.csound.wizard.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;

public class ViewUtils {
	public static float
		offset = 8.0f,
		EPS = 0.00001f;
	
	public static float getDefaultStrokeWidth() { return 7; }
	
	public static void initPaint(Paint paint) {
		
		paint.setStrokeWidth(getDefaultStrokeWidth());
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);		
	}
		
	public static void initPaint(AttributeSet attrs, Paint paint) {
		// int color;
		String xmlProvidedColor = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "color");
		if (xmlProvidedColor != null) {
			// color = Color.parseColor(xmlProvidedColor);
		} else {
		//	color = mainColor;
		}
		initPaint(paint);		
	}
	
	
	
	public static float withinBounds(float x) {
		if (x < 0) {
			return 0.0f;
		} else if (x >= 1) {
			return 1.0f - EPS;
		} else {
			return x;
		}		
	}	
	
	public static void mkOnMeasure(int widthMeasureSpec, int heightMeasureSpec, int desiredWidth, int desiredHeight, OnMeasure onMeasure) {
	    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
	    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

	    int width;
	    int height;

	    //Measure Width
	    if (widthMode == MeasureSpec.EXACTLY) {
	        //Must be this size
	        width = widthSize;
	    } else if (widthMode == MeasureSpec.AT_MOST) {
	        //Can't be bigger than...
	        width = Math.min(desiredWidth, widthSize);
	    } else {
	        //Be whatever you want
	        width = desiredWidth;
	    }

	    //Measure Height
	    if (heightMode == MeasureSpec.EXACTLY) {
	        //Must be this size
	        height = heightSize;
	    } else if (heightMode == MeasureSpec.AT_MOST) {
	        //Can't be bigger than...
	        height = Math.min(desiredHeight, heightSize);
	    } else {
	        //Be whatever you want
	        height = desiredHeight;
	    } 
	    
	    onMeasure.apply(width, height);
	}
	
	public interface OnMeasure {
		public void apply(int width, int height);
	}	
	
	public static int getCell(int n, float val) {
		return (int) Math.floor(n * val);				
	}
	
	public static int getRainbowColor(float x) {	
		return Color.HSVToColor(new float[]{ 360 * (1 - x - 1f/6), 0.7f, 0.7f });		
	}
}
