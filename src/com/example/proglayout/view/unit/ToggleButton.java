package com.example.proglayout.view.unit;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.proglayout.layout.Units.StatefulUnit;
import com.example.proglayout.view.GraphUtils.Circle;
import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnPress;
import com.example.proglayout.view.Listener.OnPressListener;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;

public class ToggleButton extends View implements StatefulUnit, OnPressListener {
	
	private OnPress mListener = Listener.defaultOnPress();
	private Paint paint = new Paint();	
	
	private String mid;
	private boolean isOn = false;
	
	private static final int desiredSize = 120;
	private Circle mCircle = new Circle();
	private Rect mRect = new Rect();
	
	private static final int MAX_CLICK_DURATION = 200;
	private long startClickTime;	
	 
	private ColorParam colors = new ColorParam();

	public ToggleButton(Context context, String id, boolean initVal, ColorParam colorParam) {
		super(context);
		ViewUtils.initPaint(paint);
		colors = colorParam;
		isOn = initVal;
		mid = id;
	}
	
	public ToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		ViewUtils.initPaint(attrs, paint);	
	}
	
	@Override
	public void setOnPressListener(OnPress listener) {
		mListener = listener;
	}
	
	@Override
	public String getUnitId() {
		return mid;
	}
	
	@Override
	public double[] getUnitState() {
		double[] ds = new double[1]; 	
		ds[0] = isOn ? 1 : 0;
		return ds;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		mRect.setView(this);
		mCircle.setCircle(mRect.getCenterX(), mRect.getCenterY(), 0.45f * mRect.getWidth() );
		
		if (colors.getBkgColor() != Color.TRANSPARENT) {
			paint.setColor(colors.getBkgColor());
			mCircle.drawFill(canvas, paint);
		}			
		
		if (isOn) {
			paint.setColor(colors.getFstColor());
			mCircle.drawRim(canvas, paint);						
			paint.setStyle(Style.FILL);
			mCircle.drawPlayButton(canvas, paint);
			
		} else {
			paint.setColor(colors.getSndColor());
			mCircle.drawRim(canvas, paint);
			paint.setStyle(Style.FILL);			
			mCircle.drawStopButton(canvas, paint);
		}						
	}
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	   switch (event.getAction()) {
           case MotionEvent.ACTION_DOWN: {
        	   if (mCircle.contains(event.getX(0), event.getY(0))) {
        		   startClickTime = Calendar.getInstance().getTimeInMillis();
        		   performClick();
        	   }        	   
               break;
           }
           case MotionEvent.ACTION_UP: {
               long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
               if(clickDuration < MAX_CLICK_DURATION) {
            	   toggleButton();
               }
           }
       }
       return true;
	}
	
	private void toggleButton() {
		isOn = !isOn;
		invalidate();
		if (isOn) {
			mListener.press();		
		} else {
			mListener.release();
		}
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
	
	public static boolean defaultState() {
		return false;		
	}
}
