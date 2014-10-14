package com.csound.wizard.view.unit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.widget.Button;

import com.csound.wizard.layout.param.ColorParam;
import com.csound.wizard.layout.param.TextParam;
import com.csound.wizard.view.ViewUtils;
import com.csound.wizard.view.GraphUtils.Rect;

public class RectButton extends Button {
	
	private Rect mRect = new Rect();
	private Paint paint = new Paint();
	private ColorParam colors;
	private int textColor;
	
	public RectButton(Context context, ColorParam colorParam, TextParam textParam) {
		super(context);
		ViewUtils.initPaint(paint);
		paint.setStrokeWidth(5);
		colors = colorParam;
		
		textColor = textParam.getColor();
		paint.setTextSize(textParam.getSize());
		paint.setTextAlign(textParam.getAlign());
		setBackgroundColor(Color.TRANSPARENT);		
		paint.setTextAlign(Align.CENTER);
	}
	
	public RectButton(Context context, AttributeSet attrs) {
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
		mRect.drawText(c, this.getText().toString(), paint);			
	}
}
