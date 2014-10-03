package com.example.proglayout.view.unit;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnKey2;
import com.example.proglayout.view.Listener.OnKey2Listener;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;
import com.example.proglayout.view.param.TextParam;

public class MultitouchChess extends View implements OnKey2Listener {	
	private static int
		desiredWidth = 500,
		desiredHeight = 500;
	
	private OnKey2 mListener = Listener.defaultOnKey2();
		
	private int pressCount = 0;
	private int mMaxTouch = 10;
	
	private Rect mRect;	
	private Paint paint = new Paint();	
	private ColorParam colors = new ColorParam();	
	private SparseArray<PointF> mActivePointers = new SparseArray<PointF>();
	private int mnx = 4, mny = 4;	
	private int textColor = Color.BLACK;
	private List<String> mLabels = new ArrayList<String>();
	
	private static final int[] palette;	
	
	static {
		int n = 10;
		palette = new int[n];
		String[] clrs = { 
				"#B10DC9", "#0074D9", "#3D9970", "#FFDC00", "#FF851B", 
				"#FF4136", "#7FDBFF", "#001F3F", "#F012BE", "#01FF70"};
		
		for (int i = 0; i < n; i++) {
			palette[i] = Color.parseColor(clrs[i]);
		}
	}
	
	public MultitouchChess(Context ctx, int maxTouch, int nx, int ny, List<String> labels, ColorParam colorParam, TextParam textParam) {
		super(ctx);
		ViewUtils.initPaint(paint);
		colors = colorParam;
		mMaxTouch = maxTouch;
		mnx = nx;
		mny = ny;
		
		paint.setTextSize(textParam.getSize());
		paint.setTextAlign(textParam.getAlign());
		textColor = textParam.getColor();

		if (labels != null) {
			mLabels = labels;
		}				
	}	

	public MultitouchChess(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		ViewUtils.initPaint(attrs, paint);
	}
	
	@Override
	public void setOnKey2Listener(OnKey2 listener) {
		mListener = listener;
	}
	
	@Override
	public void onDraw(Canvas c) {
		mRect.setView(this);		
		if (colors.getBkgColor() != Color.TRANSPARENT) {
			paint.setColor(colors.getBkgColor());
			mRect.drawRounded(c, paint);
		}
		paint.setColor(colors.getSndColor());
		mRect.drawRimRounded(c, paint);		
		mRect.drawChess(c, mnx, mny, paint);
		
		for (int i = 0; i < pressCount; i++) {
			PointF f = mActivePointers.get(i);
			if (f != null) {
				paint.setColor(colors.getFstColor());
				mRect.drawCross(c, f.x, f.y, paint);
				
				paint.setColor(colors.getSndColor());
				int mSelectedX = ViewUtils.getCell(mnx, f.x);
				int mSelectedY = ViewUtils.getCell(mny, f.y);
				mRect.drawCell(c, mnx, mny, mSelectedX, mSelectedY, paint);
			}
		}	
		
		if (!mLabels.isEmpty()) {
			paint.setColor(textColor);
			mRect.drawChessLabels(c, mnx, mny, mLabels, paint);			
		}			
	}	
	
	@Override
	public boolean performClick() {
		return super.performClick();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		int pointerIndex = event.getActionIndex();
		int pointerId = event.getPointerId(pointerIndex);		
		int maskedAction = event.getActionMasked();
		
		switch (maskedAction) {
		case MotionEvent.ACTION_DOWN:
	    case MotionEvent.ACTION_POINTER_DOWN: {
	    	performClick();
	    	
	    	if (event.getPointerCount() < mMaxTouch) {
		    	pressCount = event.getPointerCount();
		    	
		        PointF f = new PointF();
		        f.x = event.getX(pointerIndex);
		        f.y = event.getY(pointerIndex);
		        		        
		        if (mRect.contains(f)) {
		        	normalizePoint(f);		        	
		        	mActivePointers.put(pointerId, f);
		        	mListener.on(pointerId, ViewUtils.getCell(mnx, f.x), ViewUtils.getCell(mny, f.y));
		        }    		
	    	}	    	
	    } 
	    case MotionEvent.ACTION_MOVE: { // a pointer was moved
	        for (int size = event.getPointerCount(), i = 0; i < size; i++) {
	          pointerId = event.getPointerId(i);
	          PointF point = mActivePointers.get(pointerId);
	          if (point != null) {        	
	            point.x = event.getX(i);
	            point.y = event.getY(i);            
	            	            
	            if (mRect.contains(point)) {
	            	normalizePoint(point);
	            	mListener.move(pointerId, ViewUtils.getCell(mnx, point.x), ViewUtils.getCell(mny, point.y));	            			        	            	
	            } else {	            		
	            	mActivePointers.remove(pointerId);
	            	mListener.off(pointerId);
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
	        
	        if (mRect.contains(f)) {	        	
	    		mActivePointers.remove(pointerId);
	    		mListener.off(pointerId);	    		
	        }	        
	        break;
	    }		
		}
		
		invalidate();
		return true;
	}
	
	private void normalizePoint(PointF f) {
		f.x = (f.x - getLeft()) / mRect.getWidth();
		f.y = (f.y - getTop()) / mRect.getHeight();		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		ViewUtils.mkOnMeasure(widthMeasureSpec, heightMeasureSpec, desiredWidth, desiredHeight, onMeasureRunner);
	}
	
	private OnMeasure onMeasureRunner = new OnMeasure() {
		@Override
		public void apply(int width, int height) {
			mRect = new Rect(ViewUtils.offset, ViewUtils.offset, width - ViewUtils.offset, height - ViewUtils.offset); 
			setMeasuredDimension(width, height);			
		}
	};
}


