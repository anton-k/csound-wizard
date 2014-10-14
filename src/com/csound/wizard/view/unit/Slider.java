package com.csound.wizard.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;

import com.csound.wizard.Const;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.Units.StatefulUnit;
import com.csound.wizard.layout.param.ColorParam;
import com.csound.wizard.layout.param.Types.Range;
import com.csound.wizard.view.Listener;
import com.csound.wizard.view.ViewUtils;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.Listener.OnPress;
import com.csound.wizard.view.Listener.OnPressListener;
import com.csound.wizard.view.Listener.OnSlide;
import com.csound.wizard.view.Listener.OnSlideListener;
import com.csound.wizard.view.Listener.SetSlide;
import com.csound.wizard.view.ViewUtils.OnMeasure;

public class Slider extends View implements StatefulUnit, OnSlideListener, OnPressListener, SetSlide {
	private static int
		desiredWidth = Const.desiredSliderWidth,
		desiredHeight = Const.desiredSliderHeight;
	
	private OnSlide mSlideListener = Listener.defaultOnSlide();	
	private OnPress mPressListener = Listener.defaultOnPress();
	
	private String mid;
	private float mx = 0.5f;
	private Paint paint = new Paint();
	private Rect mRect = new Rect();
	private boolean isOutputOnlyMode = false;
	private boolean mIsHor;
	private Range mRange;
	
	private ColorParam colors = new ColorParam();
	
	public Slider(Context ctx, String id, float initVal, Range range, ColorParam colorParam, boolean isHor) {
		super(ctx);
		ViewUtils.initPaint(paint);
		colors = colorParam;
		mx = range.toRelative(initVal);
		mid = id;
		mIsHor = isHor;
		mRange = range;
	}

	public Slider(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		ViewUtils.initPaint(attrs, paint);
	}
	
	@Override
	public void setOnSlideListener(OnSlide listener) {
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
		return UnitUtils.getUnitStateFloat(mRange.fromRelative(mx));	
	}
	
	@Override
	public void setSlide(float x) {		
		mx = ViewUtils.withinBounds(mRange.toRelative(x));	
		invalidate();
	}
	
	public void setOutputOnlyMode() {
		isOutputOnlyMode = true;		
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
		paint.setAlpha(200);	
		mRect.drawSlider(c, mx, paint);
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
	
	private boolean isHorSlider() {
		return mRect.getWidth() > mRect.getHeight();
		
	}
	
	private void updateValue(float x, float y) {
		float val;
		if (isHorSlider()) {
			val = mRect.relativeX(x);
		} else {
			val = mRect.relativeY(y);			
		}					
		
		if (Math.abs(mx - val) > ViewUtils.EPS) {
			mSlideListener.slide(mRange.fromRelative(val));
			invalidate();
		}		
		mx = val;		
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		 if (mIsHor) {
			 ViewUtils.mkOnMeasure(widthMeasureSpec, heightMeasureSpec, desiredWidth, desiredHeight, onMeasureRunner);			 			 			 			 
		 } else {
			 ViewUtils.mkOnMeasure(widthMeasureSpec, heightMeasureSpec, desiredHeight, desiredWidth, onMeasureRunner);			 			 
		 }	
	}
	
	private OnMeasure onMeasureRunner = new OnMeasure() {
		@Override
		public void apply(int width, int height) {			
			mRect.setRect(ViewUtils.offset, ViewUtils.offset, width - ViewUtils.offset, height - ViewUtils.offset);
			setMeasuredDimension(width, height);
		}
	};

	public static float defaultState() {		
		return 0.5f;
	}

}
