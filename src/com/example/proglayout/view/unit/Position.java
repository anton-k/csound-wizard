package com.example.proglayout.view.unit;

import java.util.AbstractMap;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.Units.StatefulUnit;
import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnPress;
import com.example.proglayout.view.Listener.OnSlide2;
import com.example.proglayout.view.Listener.OnSlide2Listener;
import com.example.proglayout.view.Listener.OnPressListener;
import com.example.proglayout.view.Listener.SetSlide2;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;
import com.example.proglayout.view.param.Types.Range;

public class Position extends View implements StatefulUnit, OnPressListener, OnSlide2Listener, SetSlide2 {	
	private static int
		desiredWidth = 500,
		desiredHeight = 400;
	
	private OnSlide2 mSlideListener = Listener.defaultOnSlide2();	
	private OnPress mPressListener   = Listener.defaultOnPress();
	
	private String mid;
	private float mx = 0.5f, my = 0.5f;
	private Paint paint = new Paint();
	private Rect mRect = new Rect();
	
	private ColorParam colors = new ColorParam();
	private boolean isOutputOnlyMode = false;
	
	private Range mRangeX, mRangeY;
	
	public Position(Context ctx, String id, float x, float y, Range rangeX, Range rangeY, ColorParam colorParam) {
		super(ctx);
		ViewUtils.initPaint(paint);
		colors = colorParam;
		mid = id;
		mx = x;
		my = y;
		mRangeX = rangeX;
		mRangeY = rangeY;
	}

	public Position(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		ViewUtils.initPaint(attrs, paint);
	}
	
	@Override
	public void setOnSlide2Listener(OnSlide2 listener) {
		mSlideListener = listener;
	}
	
	@Override
	public void setOnPressListener(OnPress listener) {
		mPressListener = listener;
	}
	
	@Override
	public String getUnitId() {	
		return mid;
	}
	
	@Override
	public double[] getUnitState() {	
		return UnitUtils.getUnitStateFloatPair(mx, my);
	}
	
	public static Entry<Float,Float> defaultState() {
		return new AbstractMap.SimpleEntry<Float,Float>(0.5f, 0.5f);
	}
	
	
	public void setOutputOnlyMode() {
		isOutputOnlyMode = true;		
	}
	
	@Override
	public void setSlide(float x, float y) {
		mx = ViewUtils.withinBounds(mRangeX.toRelative(x));
		my = ViewUtils.withinBounds(mRangeY.toRelative(y));
		invalidate();		
	}
	
	@Override
	public void onDraw(Canvas c) {
		mRect.setView(this);
		
		if (colors.getBkgColor() != Color.TRANSPARENT) {
			paint.setColor(colors.getBkgColor());
			mRect.drawRounded(c, paint);			
		}
		
		paint.setColor(colors.getSndColor());
		mRect.drawRimRounded(c, paint);
		paint.setColor(colors.getFstColor());
		mRect.drawCross(c, mx, my, paint);		
	}	
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}
		
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isOutputOnlyMode) {
			return true;
		}
		
		float x, y;
		x = event.getX(0);
		y = event.getY(0);
		boolean isInside = mRect.contains(x, y);
		
		switch(event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:					
				if (isInside) {
					performClick();
					updateValue(x, y);
					mPressListener.press();
				}				
				break;
			case MotionEvent.ACTION_UP:
				mPressListener.release();					
				break;	
				
			case MotionEvent.ACTION_MOVE:
				if (isInside) {
					updateValue(x, y);				
				}
				break;
		}
	
		return true;
	}
	
	private void updateValue(float x, float y) {
		float 
			x1 = mRect.relativeX(x),
			y1 = mRect.relativeY(y);
		
		if (Math.abs(mx - x1) + Math.abs(my - y1) > ViewUtils.EPS) {
			mSlideListener.slide(mRangeX.fromRelative(x1), mRangeY.fromRelative(y1));
			invalidate();
		}		
		mx = x1;
		my = y1;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		ViewUtils.mkOnMeasure(widthMeasureSpec, heightMeasureSpec, desiredWidth, desiredHeight, onMeasureRunner);
	}
	
	private OnMeasure onMeasureRunner = new OnMeasure() {
		@Override
		public void apply(int width, int height) {
			mRect.setRect(ViewUtils.offset, ViewUtils.offset, width - ViewUtils.offset, height - ViewUtils.offset);
			setMeasuredDimension(width, height);
		}
	};
}
