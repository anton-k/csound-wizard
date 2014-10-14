package com.csound.wizard.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.ViewUtils.OnMeasure;
import com.csound.wizard.view.ViewUtils;


public class LineView extends View {
	private Paint paint=  new Paint();
	private Rect mRect = new Rect();
	private boolean mIsHor;
	private int
		desiredWidth = Const.desiredWidth,
		desiredHeight = Const.desiredLineHeight;
		
	
	public LineView(Context context, int color, boolean isHor) {
		super(context);
		ViewUtils.initPaint(paint);
		paint.setColor(color);
		mIsHor = isHor;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		mRect.setView(this);
		
		if (mRect.getWidth() > mRect.getHeight()) {			
			canvas.drawLine(mRect.getLeft(), mRect.getCenterY(), mRect.getRight(), mRect.getCenterY(), paint);
		} else {
			canvas.drawLine(mRect.getCenterX(), mRect.getTop(), mRect.getCenterX(), mRect.getBottom(), paint);
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
			mRect.setRect(ViewUtils.offset, ViewUtils.offset, width - ViewUtils.offset, height - ViewUtils.offset);
			setMeasuredDimension(width, height);
		}
	};

	public static float defaultState() {		
		return 0.5f;
	}
}