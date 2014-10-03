package com.example.proglayout.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.proglayout.view.GraphUtils.Circle;
import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnPress;
import com.example.proglayout.view.Listener.OnPressListener;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;

public class CircleButton extends View implements OnPressListener {
	
	private OnPress mListener = Listener.defaultOnPress();
	private Paint paint = new Paint();
	private boolean isPress = false;	
	private static final int desiredSize = 120;
	private Circle mCircle = new Circle();
	private Rect mRect = new Rect();
	
	private ColorParam colors;
	
	public CircleButton(Context context, ColorParam colorParam) {
		super(context);	
		ViewUtils.initPaint(paint);	
		colors = colorParam;
	}
	
	public CircleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		ViewUtils.initPaint(attrs, paint);
		colors = new ColorParam();
	}
	
	@Override
	public void setOnPressListener(OnPress listener) {
		mListener = listener;
	}
		
	@Override
	public void onDraw(Canvas canvas) {
		mRect.setView(this);
		mCircle.setCircle(mRect.getCenterX(), mRect.getCenterY(), 0.45f * mRect.getWidth());
		
		if (colors.getBkgColor() != Color.TRANSPARENT) {
			paint.setColor(colors.getBkgColor());
			mCircle.drawFill(canvas, paint);
		}
				
		if (isPress) {
			paint.setColor(colors.getFstColor());
			mCircle.drawFill(canvas, paint);
		} else {			
			paint.setColor(colors.getSndColor());
			mCircle.drawRim(canvas, paint);
		}						
	}
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:	
				if (mCircle.contains(event.getX(0), event.getY(0))) {	
					this.performClick();
					pressButton();
				}
				break;
			case MotionEvent.ACTION_UP:
				releaseButton();					
				break;			
		}	
	
		return true;
	}	
	
	private void pressButton() {
		isPress = true;
		invalidate();
		mListener.press();		
	}
	
	private void releaseButton() {
		mListener.release();
		
		Runnable r = new Runnable() {
		    public void run() {
		    	isPress = false;
		    	invalidate();
		    }
		};		
		Handler h = new Handler();
		h.postDelayed(r, 15); 
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		ViewUtils.mkOnMeasure(widthMeasureSpec, heightMeasureSpec, desiredSize, desiredSize, onMeasureRunner);
	}
	
	private OnMeasure onMeasureRunner = new OnMeasure() {
		@Override
		public void apply(int width, int height) {
			int size = Math.min(width, height);
			mCircle.setCircle(size/2, size/2, size/2 - 10);
			setMeasuredDimension(size, size);
		}
	};
}
