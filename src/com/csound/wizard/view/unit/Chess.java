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
import com.csound.wizard.view.Listener;
import com.csound.wizard.view.ViewUtils;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.Listener.OnPress;
import com.csound.wizard.view.Listener.OnPressListener;
import com.csound.wizard.view.Listener.OnTap2;
import com.csound.wizard.view.Listener.OnTap2Listener;
import com.csound.wizard.view.ViewUtils.OnMeasure;

public class Chess extends View implements StatefulUnit, OnPressListener, OnTap2Listener {	
	private static int
		desiredWidth = Const.desiredWidth,
		desiredHeight = Const.desiredWidth;
	
	private OnTap2  mTapListener   = Listener.defaultOnTap2();	
	private OnPress mPressListener = Listener.defaultOnPress();
	
	private float mx = 0.5f, my = 0.5f;
	private int 
		mnx = 4, 
		mny = 4, 
		mSelectedX = mnx / 2, 
		mSelectedY = mny / 2;
	
	private Paint paint = new Paint();
	private Rect mRect = new Rect();
	private List<String> mLabels = new ArrayList<String>();
	
	private ColorParam colors  = new ColorParam();
	private int textColor = Color.BLACK;
	
	private String mid;
	
	public Chess(Context ctx, String id, int nx, int ny, int selectedX, int selectedY, List<String> labels, ColorParam colorParam, TextParam textParam) {
		super(ctx);
		ViewUtils.initPaint(paint);
		colors = colorParam;	
		textColor = textParam.getColor();
		paint.setTextSize(textParam.getSize());
		paint.setTextAlign(textParam.getAlign());
		
		mnx = nx;
		mny = ny;
		mid = id;
		mSelectedX = selectedX;
		mSelectedY = selectedY;
		
		float dx = 1.0f / nx;
		float dy = 1.0f / ny;
		mx = (mSelectedX + 0.5f) * dx;
		my = (mSelectedY + 0.5f) * dy;
		
		if (labels != null) {
			mLabels = labels;
		}
	}

	public Chess(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		ViewUtils.initPaint(attrs, paint);
	}
	
	@Override
	public String getUnitId() {		
		return mid;
	}	
	
	@Override
	public double[] getUnitState() {	
		return UnitUtils.getUnitStateIntPair(mSelectedX, mSelectedY);
	}
	
	public static Entry<Integer,Integer> defaultState() {
		return new AbstractMap.SimpleEntry<Integer,Integer>(0, 0);
	}
	
	@Override	
	public void setOnTap2Listener(OnTap2 listener) {
		mTapListener = listener;
	}
	
	@Override
	public void setOnPressListener(OnPress listener) {
		mPressListener = listener;
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
		mRect.drawChess(c, mnx, mny, paint);
		
		paint.setColor(colors.getFstColor());
		mRect.drawCross(c, mx, my, paint);
		
		paint.setColor(colors.getSndColor());
		mRect.drawCell(c, mnx, mny, mSelectedX, mSelectedY, paint);
		
		if (!mLabels.isEmpty()) {
			paint.setColor(textColor);
			mRect.drawChessLabels(c, mnx, mny, mLabels, paint);			
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
			int selectedX = ViewUtils.getCell(mnx, x1);
			int selectedY = ViewUtils.getCell(mny, y1);
			if (mSelectedX != selectedX || mSelectedY != selectedY) {
				mTapListener.tap(selectedX, selectedY);
				mSelectedX = selectedX;				
				mSelectedY = selectedY;
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


