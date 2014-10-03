package com.example.proglayout.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.Units.StatefulUnit;
import com.example.proglayout.view.GraphUtils.Circle;
import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnPress;
import com.example.proglayout.view.Listener.OnSlide;
import com.example.proglayout.view.Listener.OnSlideListener;
import com.example.proglayout.view.Listener.SetSlide;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;
import com.example.proglayout.view.param.Types.Range;

public class Dial extends View implements StatefulUnit, OnSlideListener, SetSlide {
	
	private static final float
		coeffX = 0.007f,
		coeffY = 0.007f;
	
	private String mid;
		
	private float 
		mx = 0,
		my = 0;
	
	private OnSlide mSlideListener = Listener.defaultOnSlide();	
	private OnPress mPressListener = Listener.defaultOnPress();
	
	private Paint paint = new Paint();
	
	private static final int desiredSize = 120;
	private Circle mCircle = new Circle();
	private Rect mRect = new Rect();
	
	private float value = 0.5f;
	
	private ColorParam colors = new ColorParam();
	private boolean isOutputOnlyMode = false;
	
	private Range mRange;
	
	public static float defaultState() {
		return 0.5f;
	}
	
	public Dial(Context context, String id, float initVal, Range range, ColorParam colorParam) {
		super(context);
		ViewUtils.initPaint(paint);
		colors = colorParam;
		mid = id;
		value = initVal; 
		mRange = range;
	}
	
	public Dial(Context context, AttributeSet attrs) {
		super(context, attrs);
		ViewUtils.initPaint(attrs, paint);
	}
	
	@Override 
	public String getUnitId() {
		return mid;
	}
	
	@Override 
	public double[] getUnitState() {
		return UnitUtils.getUnitStateFloat(value);
	}

	@Override
	public void setOnSlideListener(OnSlide listener) {
		mSlideListener = listener;
	}

	public void setOnPressListener(OnPress listener) {
		mPressListener = listener;
	}
	
	@Override
	public void setSlide(float x) {
		value = ViewUtils.withinBounds(mRange.toRelative(x));
		invalidate();
	}
	
	public void setOutputOnlyMode() {
		isOutputOnlyMode = true;		
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		mRect.setView(this);
		mCircle.setCircle(mRect.getCenterX(), mRect.getCenterY(), 0.45f * mRect.getWidth());
				
		if (colors.getBkgColor() != Color.TRANSPARENT) {
			paint.setColor(colors.getBkgColor());
			mCircle.drawFill(canvas, paint);
		}
		
		float angle = value;		
		paint.setColor(colors.getSndColor());
		mCircle.drawRim(canvas, paint);
		paint.setStyle(Style.FILL);
		
		paint.setColor(colors.getFstColor());
		paint.setAlpha(80);
		mCircle.drawPie(canvas, 0.25f, angle, paint);
		paint.setAlpha(255);		
		mCircle.drawDial(canvas, angle + 0.25f, paint);
		paint.setStyle(Style.STROKE);
		mCircle.drawArc(canvas, 0.25f, angle, paint);
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
		
		switch(event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:	
				if (mCircle.contains(event.getX(0), event.getY(0))) {	
					this.performClick();
					initCursor(event.getX(0), event.getY(0));
					pressButton();
				}
				break;
			case MotionEvent.ACTION_UP:
				releaseButton();					
				break;	
				
			case MotionEvent.ACTION_MOVE:
				updateValue(event.getX(0), event.getY(0));
				break;
		}
	
		return true;
	}
	
	private void initCursor(float x, float y) {
		mx = x; my = y;		
	}
	
	private void updateValue(float x, float y) {		
		float newValue = updateOnDiff(x - mx, y - my);
		if (Math.abs(value - newValue) > ViewUtils.EPS) {
			mSlideListener.slide(mRange.fromRelative(newValue));
			invalidate();
		}
		value = newValue;
		initCursor(x, y);
	}
	
	private float updateOnDiff(float dx, float dy) {
		if (Math.abs(dx) > Math.abs(dy)) {
			return ViewUtils.withinBounds(value + coeffX * dx); 
		} else {
			return ViewUtils.withinBounds(value - coeffY * dy);			
		}
	}
	
	private void pressButton() {		
		mPressListener.press();		
	}
	
	private void releaseButton() {
		mPressListener.release();
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
