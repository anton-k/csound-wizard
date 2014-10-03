package com.example.proglayout.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener.SetSlide;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.Types.Range;

public class MeterDial extends View implements SetSlide {
	
	private float mx = 0.5f;
	private Paint paint = new Paint();
	private Rect mRect = new Rect();
	private static final int desiredSize = 200;
	private int 
		olive = Color.parseColor("#3D9970"),
		green = Color.parseColor("#2ECC40"),
		navi = Color.parseColor("#001F3F");
	
	private RectF arrowRectF = new RectF();
	private boolean mIsCenter = false;
	private Range mRange;
	
	private RectF mArcRectF = new RectF();
	
	public MeterDial(Context context, Range range, boolean isCenter) {
		super(context);	
		ViewUtils.initPaint(paint);
		mIsCenter = isCenter;
		mRange = range;
	}
		
	@Override
	public void setSlide(float x) {
		mx = ViewUtils.withinBounds(mRange.toRelative(x));
		invalidate();
	}
	
	protected void onDraw(Canvas canvas) {
		paint.setStrokeWidth(14);
		mRect.setView(this);				
		float rad = 0.45f * Math.min(mRect.getHeight(), mRect.getWidth());
		float cx = mRect.getCenterX();
		float cy = mRect.getCenterY();
					
		paint.setColor(getMainColor(mx));
			
		paint.setStyle(Style.STROKE);
		canvas.drawCircle(cx, cy, rad, paint);				
		paint.setStyle(Style.FILL);
		
		float 
			cx1 = cx, 
			cy1 = cy + rad * 0.7f;		
		
		canvas.drawCircle(cx1, cy1, 0.25f * rad, paint);
		
		mArcRectF.set(cx - rad, cy - rad, cx + rad, cy + rad);
		canvas.drawArc(mArcRectF, 40, 100, false, paint);	
		
		float 
			arrRimRad = rad * 1.2f,
			arrRad = rad * 1.1f,
			segmentWidth = 1.0f / 9,
			phi = (float) ((0.75 - segmentWidth + 2 * segmentWidth * mx) * 2 * Math.PI),  
			arrX = (float) (cx1 + arrRad * Math.cos(phi)),
			arrY = (float) (cy1 + arrRad * Math.sin(phi));
		
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);
		paint.setStrokeWidth(10);
		canvas.drawLine(cx1, cy1, arrX, arrY, paint);
		
		paint.setColor(navi);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(8);
		arrowRectF.set(cx1 - arrRimRad, cy1 - arrRimRad, cx1 + arrRimRad, cy1 + arrRimRad);		
		canvas.drawArc(arrowRectF, 230, 80, false, paint);
		paint.setStrokeWidth(10);
		if (mIsCenter) {
			paint.setColor(green);						
			canvas.drawArc(arrowRectF, 263, 14, false, paint);
		} else {
			paint.setColor(Color.RED);
			canvas.drawArc(arrowRectF, 300, 10, false, paint);
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
			setMeasuredDimension(size, size);
		}
	};
	
	private int getMainColor(float x) {		
		if (mIsCenter) {
			float lim = 0.13f;
			if (Math.abs(x - 0.5f) < lim) {
				return olive;
			} else {
				if (x > 0.5f) {
					float rx = (x - 0.5f - lim) / (1 - 0.5f - lim);								
					return ViewUtils.getRainbowColor(0.5f + lim + (0.5f - lim) * rx);					
				} else {
					float rx = x / (0.5f - lim);
					return ViewUtils.getRainbowColor(rx * (0.5f - lim));					
				}
			}			
		} else {
			float lim = 0.8f;
			if (mx > lim) {			
				return ViewUtils.getRainbowColor(0.4f + 0.4f * (mx - lim)/(1 - lim));			
			} else {
				return olive;
			}			
		}
	}

}
