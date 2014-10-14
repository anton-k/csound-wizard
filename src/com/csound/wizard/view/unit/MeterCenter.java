package com.csound.wizard.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.layout.param.Types.Range;
import com.csound.wizard.view.ViewUtils;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.Listener.SetSlide;
import com.csound.wizard.view.ViewUtils.OnMeasure;

public class MeterCenter extends View implements SetSlide {
	private static int
	desiredWidth = Const.desiredSliderWidth,
	desiredHeight = Const.desiredSliderHeight;
	
	private float mx = 0.5f;
	private Paint paint = new Paint();
	private Rect mRect = new Rect();	
	private static final int 
		n = 11,		
		olive = Const.getColor("olive"),
		yellow = Const.getColor("yellow"),
		red  = Const.getColor("red");
	private static final float offset = 5;
			
	private Range mRange;
	private boolean mIsHor;
	
	public MeterCenter(Context context, Range range, boolean isHor) {
		super(context);	
		ViewUtils.initPaint(paint);	
		mRange = range;
		mIsHor = isHor;
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
		paint.setColor(olive);
		mRect.drawRimRounded(canvas, paint);
		paint.setStyle(Style.FILL);
		int m = Math.round(n * Math.abs(mx - 0.5f));
		float dx = mRect.getWidth() / n;
		float cx = mRect.getCenterX();
		
		canvas.drawRoundRect(
				new RectF(cx - 0.5f * dx + offset, 
				mRect.getTop(), 
				cx + 0.5f * dx - offset,  
				mRect.getBottom()),
				2 * offset, 2 * offset,					
				paint);		
	
		for (int i = 0; i < m; i++) {
			if (i == 0) {
				paint.setColor(yellow);
			}
			if (i == 3) {
				paint.setColor(red);
			}	
			
			if (mx > 0.5f) {
				canvas.drawRoundRect(
						new RectF(cx + dx * (i + 0.5f) + offset, 
						mRect.getTop() + offset, 
						cx + dx * (i + 1 + 0.5f) - offset,  
						mRect.getBottom() - offset),
						2 * offset, 2 * offset,					
						paint);
			} else {
				canvas.drawRoundRect(
					new RectF(cx - dx * (i + 1 + 0.5f) + offset, 
					mRect.getTop()+ offset, 
					cx - dx * (i + 0.5f) - offset,  
					mRect.getBottom()- offset),
					2 * offset, 2 * offset,					
					paint);			
			}				
		}
				
				
	}	
	
	private void drawVer(Canvas canvas) {
		paint.setColor(olive);
		mRect.drawRimRounded(canvas, paint);
		paint.setStyle(Style.FILL);
		int m = Math.round(n * Math.abs(mx - 0.5f));
		float dy = mRect.getHeight() / n;
		float cy = mRect.getCenterY();
		
		canvas.drawRoundRect(
				new RectF(mRect.getLeft(),
					cy - 0.5f * dy + offset, 
					mRect.getRight(), 
					cy + 0.5f * dy - offset),
				2 * offset, 2 * offset,					
				paint);		
	
		for (int i = 0; i < m; i++) {
			if (i == 0) {
				paint.setColor(yellow);
			}
			if (i == 3) {
				paint.setColor(red);
			}	
			
			if (mx > 0.5f) {
				canvas.drawRoundRect(
						new RectF(
							mRect.getLeft() + offset,
							cy + dy * (i + 0.5f) + offset, 
							mRect.getRight() - offset, 
							cy + dy * (i + 1 + 0.5f) - offset),
						2 * offset, 2 * offset,					
						paint);
			} else {
				canvas.drawRoundRect(
					new RectF(
						mRect.getLeft() + offset,
						cy - dy * (i + 1 + 0.5f) + offset, 
						mRect.getRight() - offset, 
						cy - dy * (i + 0.5f) - offset),
					2 * offset, 2 * offset,					
					paint);			
			}				
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


