package com.example.proglayout.view.unit;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.proglayout.layout.Layout;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.Units.StatefulUnit;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnTap;
import com.example.proglayout.view.Listener.OnTapListener;
import com.example.proglayout.view.param.TextParam;

public class Radio extends RadioGroup implements StatefulUnit, OnTapListener, OnClickListener {
	
	private OnTap mListener = Listener.defaultOnTap(); 
	
	private String mid;
	private int mSelected;
	
	public Radio(Context context) {
		super(context);
	}
	
	public Radio(Context context, String id, int initVal, List<String> names, boolean isHor, TextParam textParam) {		
		super(context);
		mid = id;
		mSelected = initVal;				
		setOrientation(isHor);
		addButtons(initVal, context, names, textParam);				
	}
	
	private void setOrientation(boolean isHor) { 
		if (isHor) {
			this.setOrientation(HORIZONTAL);
		} else {
			this.setOrientation(VERTICAL);
		}		
	}
	
	private void addButtons(int initVal, Context ctx, List<String> names, TextParam textParam) {
		int k = 0;
		for (String x: names) {
			RadioButton btn = new RadioButton(ctx);
			btn.setText(x);
			btn.setId(k);	
			btn.setOnClickListener(this);
			Layout.setTextProperties(btn, textParam);
			this.addView(btn);
			if (k == initVal) {
				btn.setChecked(true);
			}
			k++;
		}
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
	public void onClick(View v) {
		RadioButton rb = (RadioButton)v;
		int pos = rb.getId();
		mListener.tap(pos);
		mSelected = pos;
	}

	public static int defaultState() {		
		return 0;
	}	

}
