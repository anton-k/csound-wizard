package com.csound.wizard.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.layout.param.Types.Range;
import com.csound.wizard.view.ViewUtils;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.Listener.SetSlide;
import com.csound.wizard.view.ViewUtils.OnMeasure;

public class RainbowCircle extends View implements SetSlide {
	
	private float mx = 0.5f;
	private Paint paint = new Paint();
	private Rect mRect = new Rect();
	private static final int desiredSize = Const.desiredCircleSize;
	private Range mRange;
	
	public RainbowCircle(Context context, Range range) {
		super(context);	
		ViewUtils.initPaint(paint);	
		mRange = range;
	}
		
	@Override
	public void setSlide(float x) {
		mx = ViewUtils.withinBounds(mRange.toRelative(x));
		invalidate();		
	}
	
	protected void onDraw(Canvas canvas) {
		mRect.setView(this);
		float rad = 0.45f * Math.min(mRect.getHeight(), mRect.getWidth());
		float cx = mRect.getCenterX();
		float cy = mRect.getCenterY();
		paint.setColor(ViewUtils.getRainbowColor(mx));
		canvas.drawCircle(cx, cy, rad, paint);				
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

}
