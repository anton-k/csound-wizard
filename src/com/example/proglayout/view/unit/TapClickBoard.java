package com.example.proglayout.view.unit;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.example.proglayout.view.GraphUtils;
import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnTap2;
import com.example.proglayout.view.Listener.OnTap2Listener;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;
import com.example.proglayout.view.param.TextParam;

public class TapClickBoard extends View implements OnTap2Listener {	
	private static int
		desiredWidth = 500,
		desiredHeight = 500;
	
	private OnTap2 mTapListener = Listener.defaultOnTap2();
		
	private int 
		mnx = 4, 
		mny = 4;
		
	private Paint paint = new Paint();
	
	private GraphUtils.TapBoard mTapBoard;
	private SparseArray<PointF> mActivePointers = new SparseArray<PointF>();
	private int pressCount = 0;
	
	private List<String> mLabels = new ArrayList<String>();
	private ColorParam colors = new ColorParam();
	private int textColor = Color.BLACK;
	
	public TapClickBoard(Context ctx, int nx, int ny, List<String> labels, ColorParam colorParam, TextParam textParam) {
		super(ctx);
		ViewUtils.initPaint(paint);
		colors = colorParam;
		
		mnx = nx;
		mny = ny;
				
		paint.setTextSize(textParam.getSize());
		paint.setTextAlign(textParam.getAlign());
		textColor = textParam.getColor();
		
		if (labels != null) {
			mLabels = labels;
		}
	}

	public TapClickBoard(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		ViewUtils.initPaint(attrs, paint);
	}
	
	@Override
	public void setOnTap2Listener(OnTap2 listener) {
		mTapListener = listener;
	}
	
	@Override
	public void onDraw(Canvas c) {
		mTapBoard.getRect().setView(this);		
		mTapBoard.draw(c, paint, mActivePointers, pressCount, colors);
		if (!mLabels.isEmpty()) {
			paint.setColor(textColor);
			mTapBoard.getRect().drawChessLabels(c, mnx, mny, mLabels, paint);
		}
	}	
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		pressCount = event.getPointerCount();
		int pointerIndex = event.getActionIndex();
		int pointerId = event.getPointerId(pointerIndex);		
		int maskedAction = event.getActionMasked();
		
		Rect rect = mTapBoard.getRect();
		
		switch (maskedAction) {
			case MotionEvent.ACTION_DOWN:	
			case MotionEvent.ACTION_POINTER_DOWN: {
				PointF f = new PointF();
		        f.x = event.getX(pointerIndex);
		        f.y = event.getY(pointerIndex);
		        performClick();
		        
				if (rect.contains(f)) {
					mActivePointers.put(pointerId, f);	
		    		int selectedX = getX(f.x);
		    		int selectedY = getY(f.y);
		    		press(selectedX, selectedY, pointerId);					
				}
				break;
			}		
			default:
				break;
		}	
		return true;
	}
	
	private void press(final int x, final int y, final int pointerId) {		
		mTapListener.tap(x, y);	
		invalidate();
				
		Runnable r = new Runnable() {
		    public void run() {	
		    	mActivePointers.remove(pointerId);
				invalidate();
		    }
		};		
		Handler h = new Handler();
		h.postDelayed(r, 25);	
	}	
	
	private int getX(float x) {
		return ViewUtils.getCell(mnx, mTapBoard.getRect().relativeX(x));		
	}
	private int getY(float y) {
		return ViewUtils.getCell(mny, mTapBoard.getRect().relativeY(y));		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		ViewUtils.mkOnMeasure(widthMeasureSpec, heightMeasureSpec, desiredWidth, desiredHeight, onMeasureRunner);
	}
	
	private OnMeasure onMeasureRunner = new OnMeasure() {
		@Override
		public void apply(int width, int height) {			
			mTapBoard = new GraphUtils.TapBoard(new Rect(ViewUtils.offset, ViewUtils.offset, width - ViewUtils.offset, height - ViewUtils.offset), mnx, mny); 
			setMeasuredDimension(width, height);
		}
	};
}



