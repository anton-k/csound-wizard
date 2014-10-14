package com.csound.wizard.view.unit;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.Units.StatefulUnit;
import com.csound.wizard.layout.param.ColorParam;
import com.csound.wizard.layout.param.TextParam;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.Listener;
import com.csound.wizard.view.Listener.OnTap;
import com.csound.wizard.view.Listener.OnTapListener;
import com.csound.wizard.view.ViewUtils;

public class CsdNames extends Button implements OnTapListener, StatefulUnit {
	
	private OnTap mListener = Listener.defaultOnTap(); 
	
	private List<String> mNames;
	private String mid;
	private int mSelected;
	
	private Rect mRect = new Rect();
	private Paint paint = new Paint();
	private ColorParam colors;
	private int textColor;
	private int mn;
	
	
	public CsdNames(Context context, String id, int initVal, List<String> names, ColorParam colorParam, TextParam textParam) {
		super(context);
		mid = id;
		
		if (initVal < 0 || initVal >= names.size()) {
			mSelected = 0;
		} else {
			mSelected = initVal;
		}
		mNames = names;
		mn = names.size();
		
		ViewUtils.initPaint(paint);
		paint.setStrokeWidth(5);
		colors = colorParam;
		
		textColor = textParam.getColor();
		paint.setTextSize(textParam.getSize());
		paint.setTextAlign(textParam.getAlign());
		setBackgroundColor(Color.TRANSPARENT);		
		paint.setTextAlign(Align.CENTER);
		
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSelected++;
				if (mSelected >= mn) {
					mSelected = 0;
				}				
				mListener.tap(mSelected);
				invalidate();
			}
			
		});
	}

	
	public CsdNames(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}
	
	@Override
	protected void onDraw(Canvas c) {		
		mRect.setRect(5, 5, getWidth() - 10, getHeight() - 10);				
		paint.setColor(colors.getFstColor());		
		mRect.drawRounded(c, paint);
		paint.setColor(colors.getSndColor());
		mRect.drawRimRounded(c, paint);
		paint.setColor(textColor);
		mRect.drawText(c, mNames.get(mSelected), paint);			
	}
	
	@Override
	public String getUnitId() {	
		return mid;
	}
	
	@Override
	public double[] getUnitState() {		
		return UnitUtils.getUnitStateFloat(mSelected);
	}	
	
	@Override
	public void setOnTapListener(OnTap listener) {
		mListener = listener;		
	}
	

	public static int defaultState() {		
		return 0;
	}
	
}

