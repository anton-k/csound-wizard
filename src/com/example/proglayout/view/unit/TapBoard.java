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
import com.example.proglayout.view.Listener.OnKeyPress2;
import com.example.proglayout.view.Listener.OnKeyPress2Listener;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;
import com.example.proglayout.view.param.TextParam;

public class TapBoard extends View implements OnKeyPress2Listener {	
	private static int
		desiredWidth = 500,
		desiredHeight = 500;
	
	private OnKeyPress2 mKeyPressListener = Listener.defaultOnKeyPress2();
		
	private int pressCount = 0;
	
	private int 
		mnx = 4, 
		mny = 4;		
	private Paint paint = new Paint();
	
	private GraphUtils.TapBoard mTapBoard;
	
	private List<String> mLabels = new ArrayList<String>();
	private ColorParam colors = new ColorParam();
	private int textColor = Color.BLACK;
	private boolean isOns[];
	
	private SparseArray<PointF> mActivePointers = new SparseArray<PointF>();
	
	public TapBoard(Context ctx, int nx, int ny, List<String> labels, ColorParam colorParam, TextParam textParam) {
		super(ctx);
		ViewUtils.initPaint(paint);
		colors = colorParam;
		
		mnx = nx;
		mny = ny;	
		int n = nx * ny;
		isOns = new boolean[n];
		for (int i = 0; i < n; i++) {
			isOns[i] = false;
		}
		
		paint.setTextSize(textParam.getSize());
		paint.setTextAlign(textParam.getAlign());
		textColor = textParam.getColor();
		
		if (labels != null) {
			mLabels = labels;
		}			
	}

	public TapBoard(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		ViewUtils.initPaint(attrs, paint);
	}
	
	@Override
	public void setOnKeyPress2Listener(OnKeyPress2 listener) {
		mKeyPressListener = listener;
	}
	
	@Override
	public void onDraw(Canvas c) {
		mTapBoard.getRect().setView(this);		
		mTapBoard.draw(c, paint, isOns, colors);
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
	    	performClick();
	    	
	        PointF f = new PointF();
	        f.x = event.getX(pointerIndex);
	        f.y = event.getY(pointerIndex);
	        
	        if (rect.contains(f)) {
	        	mActivePointers.put(pointerId, f);	
	    		int selectedX = getX(f.x);
	    		int selectedY = getY(f.y);	    		
	    		press(selectedX, selectedY);	    			    		
	        }
	    } 
	    case MotionEvent.ACTION_MOVE: { // a pointer was moved
	        for (int size = event.getPointerCount(), i = 0; i < size; i++) {
	          PointF point = mActivePointers.get(event.getPointerId(i));
	          pointerId = event.getPointerId(i);
	          
	          if (point != null) {
	        	int x1, x2, y1, y2;
	        	
	        	x1 = getX(point.x);
	        	y1 = getY(point.y);		
	        	  
	            point.x = event.getX(i);
	            point.y = event.getY(i);
	            
	            if (mTapBoard.getRect().contains(point)) {
		        	x2 = getX(point.x);
		        	y2 = getY(point.y);
		        	
		        	if (x1 != x2 || y1 != y2) {
		        		release(x1, y1, event.getPointerId(i));		        		
		        		press(x2, y2);
		        	}            	
	            } else {
	            	release(x1, y1, event.getPointerId(i));	
	            	mActivePointers.remove(event.getPointerId(i));	            	
	            }
	            
	          }
	        }
	        break;
	      }
	    case MotionEvent.ACTION_UP:
	    case MotionEvent.ACTION_POINTER_UP:
	    case MotionEvent.ACTION_CANCEL: {
	    	PointF f = new PointF();
	        f.x = event.getX(pointerIndex);
	        f.y = event.getY(pointerIndex);
	        
	        if (rect.contains(f)) {
	        	int selectedX = getX(f.x);
	    		int selectedY = getY(f.y);
	    		release(selectedX, selectedY, pointerId);
	    		mActivePointers.remove(pointerId);
	        }	        
	        break;
	    }		
		}
		
		return true;
	}
		
	private void press(int x, int y) {		
		mKeyPressListener.on(x, y);	
		isOns[x * mny + y] = true;
		invalidate();
	}

	private void release(final int x, final int y, final int pointerId) {
		isOns[x * mny + y] = false;
		mKeyPressListener.off(x, y);	
		
		Runnable r = new Runnable() {
		    public void run() {	    	
				invalidate();
		    }
		};		
		Handler h = new Handler();
		h.postDelayed(r, 15);				
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



