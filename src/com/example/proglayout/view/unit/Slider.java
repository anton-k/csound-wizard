package com.example.proglayout.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;

import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.Units.StatefulUnit;
import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnPress;
import com.example.proglayout.view.Listener.OnPressListener;
import com.example.proglayout.view.Listener.OnSlide;
import com.example.proglayout.view.Listener.OnSlideListener;
import com.example.proglayout.view.Listener.SetSlide;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;
import com.example.proglayout.view.param.Types.Range;

public class Slider extends View implements StatefulUnit, OnSlideListener, OnPressListener, SetSlide {
	private static int
		desiredWidth = 500,
		desiredHeight = 80;
	
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
		mx = initVal;
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
		return UnitUtils.getUnitStateFloat(mx);	
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
