package com.csound.wizard.view.unit;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
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
import com.csound.wizard.layout.param.TextParam;
import com.csound.wizard.layout.param.Types.Range;
import com.csound.wizard.view.Listener;
import com.csound.wizard.view.ViewUtils;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.Listener.OnPress;
import com.csound.wizard.view.Listener.OnPressListener;
import com.csound.wizard.view.Listener.OnSlide;
import com.csound.wizard.view.Listener.OnSlideListener;
import com.csound.wizard.view.Listener.OnTap;
import com.csound.wizard.view.Listener.OnTapListener;
import com.csound.wizard.view.ViewUtils.OnMeasure;

public class PositionDiscreteY extends View implements StatefulUnit, OnPressListener, OnSlideListener, OnTapListener {	
	private static int
		desiredWidth = Const.desiredWidth,
		desiredHeight = Const.desiredWidth;
	
	private OnSlide mSlideListener = Listener.defaultOnSlide();
	private OnTap   mTapListener   = Listener.defaultOnTap();	
	private OnPress mPressListener = Listener.defaultOnPress();
	
	private float mx = 0.5f, my = 0.5f;
	private int mn = 10, mSelected = mn / 2;
	private Paint paint = new Paint();
	private Rect mRect = new Rect();
	
	private String mid;
	private List<String> mLabels = new ArrayList<String>();
	private ColorParam colors = new ColorParam();
	private int textColor = Color.BLACK;
	private Range mRangeX;
	
	public PositionDiscreteY(Context ctx, String id, int selected, float x, Range rangeX, int n, List<String> labels, ColorParam colorParam, TextParam textParam) {
		super(ctx);
		ViewUtils.initPaint(paint);
		colors = colorParam;
		
		mid = id;
		mn = n;
		mSelected = selected;
		float dy = 1.0f / n; 
		mx = rangeX.toRelative(x);
		my = (mSelected + 0.5f) * dy; 
		mRangeX = rangeX;
				
		paint.setTextSize(textParam.getSize());
		paint.setTextAlign(textParam.getAlign());
		textColor = textParam.getColor();
		
		if (labels != null) {
			mLabels = labels;
		}
	}

	public PositionDiscreteY(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		ViewUtils.initPaint(attrs, paint);
	}
	
	@Override
	public void setOnSlideListener(OnSlide listener) {
		mSlideListener = listener;
	}
	
	@Override
	public void setOnTapListener(OnTap listener) {
		mTapListener = listener;
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
		return UnitUtils.getUnitStateIntegerFloatPair(mSelected, mRangeX.fromRelative(mx));
	}
	
	public static Entry<Integer,Float> defaultState() {
		return new AbstractMap.SimpleEntry<Integer,Float>(0, 0.5f);
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
		mRect.drawStripesY(c, mn, paint);
		paint.setColor(colors.getSndColor());
		
		paint.setColor(colors.getFstColor());
		mRect.drawCross(c, mx, my, paint);
		
		paint.setColor(colors.getSndColor());
		mRect.drawSelectedStripesY(c, mn, mSelected, paint);
		
		if (!mLabels.isEmpty()) {
			paint.setColor(textColor);
			mRect.drawStripesYText(c, mn, paint, mLabels);
		}
	}	
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
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
			if (Math.abs(mx - x1) > ViewUtils.EPS) {
				mSlideListener.slide(mRangeX.fromRelative(x1));
			}
			
			int selected = (int) (mn * y1);
			if (mSelected != selected) {
				mTapListener.tap(selected);
				mSelected = selected;				
			}
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

