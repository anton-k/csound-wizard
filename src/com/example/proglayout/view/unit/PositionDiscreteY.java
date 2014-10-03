package com.example.proglayout.view.unit;

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

import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.Units.StatefulUnit;
import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnPress;
import com.example.proglayout.view.Listener.OnPressListener;
import com.example.proglayout.view.Listener.OnSlide;
import com.example.proglayout.view.Listener.OnSlideListener;
import com.example.proglayout.view.Listener.OnTap;
import com.example.proglayout.view.Listener.OnTapListener;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;
import com.example.proglayout.view.param.TextParam;
import com.example.proglayout.view.param.Types.Range;

public class PositionDiscreteY extends View implements StatefulUnit, OnPressListener, OnSlideListener, OnTapListener {	
	private static int
		desiredWidth = 400,
		desiredHeight = 500;
	
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
		mx = x;
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
		return UnitUtils.getUnitStateIntegerFloatPair(mSelected, my);
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

