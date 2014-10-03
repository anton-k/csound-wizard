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

import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.Units.StatefulUnit;
import com.example.proglayout.view.GraphUtils;
import com.example.proglayout.view.GraphUtils.Rect;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnKeyPress2;
import com.example.proglayout.view.Listener.OnKeyPress2Listener;
import com.example.proglayout.view.ViewUtils;
import com.example.proglayout.view.ViewUtils.OnMeasure;
import com.example.proglayout.view.param.ColorParam;
import com.example.proglayout.view.param.TextParam;

public class TapToggleBoard extends View implements StatefulUnit, OnKeyPress2Listener {	
	private static int
		desiredWidth = 500,
		desiredHeight = 500;
	
	private OnKeyPress2 mTapListener = Listener.defaultOnKeyPress2();
		
	private int 
		mnx = 4, 
		mny = 4;
		
	private Paint paint = new Paint();
	
	private GraphUtils.TapBoard mTapBoard;
	private SparseArray<PointF> mActivePointers = new SparseArray<PointF>();	
	
	private List<String> mLabels = new ArrayList<String>();
	private ColorParam colors = new ColorParam();
	private int textColor = Color.BLACK;
	
	private boolean[] isOns;
	private String mid;
	
	public TapToggleBoard(Context ctx, String id, boolean[] initVal, int nx, int ny, List<String> labels, ColorParam colorParam, TextParam textParam) {
		super(ctx);
		ViewUtils.initPaint(paint);
		colors = colorParam;
		
		mnx = nx;
		mny = ny;
		isOns = initVal;
		mid = id;
				
		paint.setTextSize(textParam.getSize());
		paint.setTextAlign(textParam.getAlign());
		textColor = textParam.getColor();
		
		if (labels != null) {
			mLabels = labels;
		}
	}

	public TapToggleBoard(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		ViewUtils.initPaint(attrs, paint);
	}
	
	@Override
	public void setOnKeyPress2Listener(OnKeyPress2 listener) {
		mTapListener = listener;
	}
	
	
	public static boolean[] defaultState(int nx, int ny) {
		int n = nx * ny;
		boolean[] res = new boolean[n];
		for (int i = 0; i < n; i++) {
			res[i] = false;
		}		
		return res;
	}
	
	@Override
	public String getUnitId() {	
		return mid;
	}
	
	@Override
	public double[] getUnitState() {	
		return UnitUtils.getUnitStateArrayOfBooleans(isOns);
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
		    		int selectedX = getX(f.x);
		    		int selectedY = getY(f.y);
		    		press(selectedX, selectedY, f, pointerId);					
				}
				break;
			}		
			default:
				break;
		}	
		return true;
	}
	
	private void press(int x, int y, PointF f, 
			int pointerId) {
		int id = x * mny + y;
		if (isOns[id]) {
			mTapListener.off(x, y);
			mActivePointers.remove(pointerId);
		} else {
			mTapListener.on(x, y);	
			mActivePointers.put(pointerId, f);
		}
			
		isOns[id] = !isOns[id];
		invalidate();
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



