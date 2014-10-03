package com.example.proglayout.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener.SetSlide;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.Types.Range;

public class Meter extends View implements SetSlide {
	private static int
		desiredWidth = 500,
		desiredHeight = 80;
	
	private float mx = 0.5f;
	private Paint paint = new Paint();
	private Rect mRect = new Rect();	
	private static final int 
		n = 12,
		green = Color.parseColor("#2ECC40"), 
		yellow = Color.parseColor("#FFDC00"),
		red  = Color.parseColor("#FF4136");
	private static final float offset = 3;
	
	private LayoutParent mOrient;	
	private Range mRange;
	
	public Meter(Context context, Range range, LayoutParent orient) {
		super(context);	
		ViewUtils.initPaint(paint);		
		mOrient = orient;
		mRange = range;
	}
		
	@Override
	public void setSlide(float x) {
		mx = ViewUtils.withinBounds(mRange.toRelative(x));
		invalidate();
	}
	
	protected void onDraw(Canvas canvas) {
		mRect.setView(this);
		if (mRect.getHeight() < mRect.getWidth()) {
			drawHor(canvas);
		} else {
			drawVer(canvas);			
		}	
	}
	
	private void drawHor(Canvas canvas) {
		paint.setColor(green);
		int m = Math.round(n * mx);
		float dx = mRect.getWidth() / n;
		for (int i = 0; i < m; i++) {
			if (i == n - 4) {
				paint.setColor(yellow);
			}
			if (i == n - 2) {
				paint.setColor(red);
			}			
			canvas.drawRoundRect(
					new RectF(mRect.getLeft() + dx * i + offset , 
					mRect.getTop(), 
					mRect.getLeft() + dx * (i + 1) - offset,  
					mRect.getBottom()),
					2 * offset, 2 * offset,					
					paint);						
		}
	}	
	
	private void drawVer(Canvas canvas) {
		paint.setColor(green);
		int m = Math.round(n * mx);
		float dy = mRect.getHeight() / n;
		for (int i = 0; i < m; i++) {
			if (i == n - 4) {
				paint.setColor(yellow);
			}
			if (i == n - 2) {
				paint.setColor(red);
			}			
			canvas.drawRoundRect(
					new RectF(mRect.getLeft(), 
					mRect.getBottom() - dy * (i + 1) + offset, 
					mRect.getRight(), 
					mRect.getBottom() - dy * i - offset),
					2 * offset, 2 * offset,
					paint);						
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mOrient == LayoutParent.VER) {
			 ViewUtils.mkOnMeasure(widthMeasureSpec, heightMeasureSpec, desiredWidth, desiredHeight, onMeasureRunner);			 			 
		 } else {
			 ViewUtils.mkOnMeasure(widthMeasureSpec, heightMeasureSpec, desiredHeight, desiredWidth, onMeasureRunner);			 
		 }
	}
	
	private OnMeasure onMeasureRunner = new OnMeasure() {
		@Override
		public void apply(int width, int height) {					
			setMeasuredDimension(width, height);
		}
	};

}

