package com.csound.wizard.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.layout.param.ColorParam;
import com.csound.wizard.layout.param.Types.Range;
import com.csound.wizard.view.ViewUtils;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.Listener.SetSlide;
import com.csound.wizard.view.ViewUtils.OnMeasure;

public class Meter extends View implements SetSlide {
	private static int
		desiredWidth = Const.desiredSliderWidth,
		desiredHeight = Const.desiredSliderHeight;
	
	private ColorParam mColor;
	private boolean mIsHor; 
	private float mx = 0.5f;
	private Paint paint = new Paint();
	private Rect mRect = new Rect();	
	private static final int 
		n = 12,
		green = Color.parseColor("#2ECC40"), 
		yellow = Color.parseColor("#FFDC00"),
		red  = Color.parseColor("#FF4136");
	private static final float offset = 3;		
	private Range mRange;
	
	public Meter(Context context, Range range, boolean isHor, ColorParam color) {
		super(context);	
		ViewUtils.initPaint(paint);	
		
		mRange = range;
		mIsHor = isHor;	
		mColor = color;
	}
		
	@Override
	public void setSlide(float x) {
		mx = ViewUtils.withinBounds(mRange.toRelative(x));
		invalidate();
	}
	
	protected void onDraw(Canvas canvas) {		
		mRect.setRect(getPaddingLeft(), getPaddingTop(), getWidth(), getHeight());
		paint.setColor(mColor.getBkgColor());
		mRect.draw(canvas, paint);
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
		if (mIsHor) {
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

