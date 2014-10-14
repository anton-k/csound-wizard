package com.csound.wizard.view.unit;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.csound.wizard.layout.Layout;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.Units.StatefulUnit;
import com.csound.wizard.layout.param.ColorParam;
import com.csound.wizard.layout.param.TextParam;
import com.csound.wizard.view.GraphUtils.Rect;
import com.csound.wizard.view.Listener;
import com.csound.wizard.view.Listener.OnTap;
import com.csound.wizard.view.Listener.OnTapListener;

public class CsdSpinner extends Spinner implements AdapterView.OnItemSelectedListener, OnTapListener, StatefulUnit {
	
	private Rect mRect = new Rect();
	private Paint paint = new Paint();
	private OnTap mListener = Listener.defaultOnTap(); 
	
	private List<String> mNames;
	private String mid;
	private int mSelected;

	public CsdSpinner(Context context) {
		super(context);
	}
	
	public CsdSpinner(Context context, String id, int initVal, List<String> names, ColorParam color, final TextParam textParam) {
		super(context);
		mid = id;
		mSelected = initVal;
		mNames = names;
		paint.setColor(color.getSndColor());
		paint.setAlpha(80);
			
		ArrayAdapter<String>  adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mNames) {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return applyTextParam(super.getView(position, convertView, parent));				
			}
			
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				return applyTextParam(super.getDropDownView(position, convertView, parent));
			}
			
			private View applyTextParam(View x) {
				TextView res = (TextView) x;
				Layout.setTextProperties(res, textParam);
				return res;				
			}		
		};
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.setAdapter(adapter);		
		this.setSelection(initVal);		
		this.setOnItemSelectedListener(this);		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		mRect.setView(this);	
		mRect.draw(canvas, paint);
		super.onDraw(canvas);
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
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, 
	            int pos, long id) {
		mSelected = pos;
		mListener.tap(mSelected);		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {	       
	}

	public static Integer defaultState() {		
		return 0;
	}
	
}
