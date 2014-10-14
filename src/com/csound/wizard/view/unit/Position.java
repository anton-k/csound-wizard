package com.csound.wizard.view.unit;

import java.util.AbstractMap;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.Units.StatefulUnit;
import com.csound.wizard.layout.param.ColorParam;
import com.csound.wizard.layout.param.Types.Range;
import com.csound.wizard.view.Listener;
import com.csound.wizard.view.ViewUtils;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.Listener.OnPress;
import com.csound.wizard.view.Listener.OnPressListener;
import com.csound.wizard.view.Listener.OnSlide2;
import com.csound.wizard.view.Listener.OnSlide2Listener;
import com.csound.wizard.view.Listener.SetSlide2;
import com.csound.wizard.view.ViewUtils.OnMeasure;

public class Position extends View implements StatefulUnit, OnPressListener, OnSlide2Listener, SetSlide2 {	
	private static int
		desiredWidth = Const.desiredWidth,
		desiredHeight = Const.desiredWidth;
	
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
		mx = rangeX.toRelative(x);
		my = rangeY.toRelative(y);
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
		return UnitUtils.getUnitStateFloatPair(mRangeX.fromRelative(mx), mRangeY.fromRelative(my));
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
