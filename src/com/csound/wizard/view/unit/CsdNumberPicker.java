package com.csound.wizard.view.unit;

import android.content.Context;
import android.widget.NumberPicker;

import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.Units.StatefulUnit;
import com.csound.wizard.layout.param.TextParam;
import com.csound.wizard.layout.param.Types.Range;
import com.csound.wizard.view.Listener;
import com.csound.wizard.view.Listener.OnTap;
import com.csound.wizard.view.Listener.OnTapListener;

public class CsdNumberPicker extends NumberPicker implements OnTapListener, StatefulUnit {
	
	private OnTap mListener = Listener.defaultOnTap(); 
		
	private String mid;	
	private int mSelected;

	public CsdNumberPicker(Context context) {
		super(context);
	}
	
	public CsdNumberPicker(Context context, String id, int initVal, Range range, final TextParam textParam) {
		super(context);
		mid = id;
		mSelected = initVal;		
		this.setMinValue((int) range.getMin());
		this.setMaxValue((int) range.getMax());
		this.setValue(initVal);
		this.setWrapSelectorWheel(false);
		this.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				mSelected = newVal;
				mListener.tap(newVal);				
			}			
		});		
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

	public static int defaultState(Range r) {		
		return (int) Math.round(0.5 * (r.getMin() + r.getMax()));
	}
	
	
	
}
