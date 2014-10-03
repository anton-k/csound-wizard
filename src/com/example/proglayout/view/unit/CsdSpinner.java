package com.example.proglayout.view.unit;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.proglayout.layout.Layout;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.Units.StatefulUnit;
import com.example.proglayout.view.Listener;
import com.example.proglayout.view.Listener.OnTap;
import com.example.proglayout.view.Listener.OnTapListener;
import com.example.proglayout.view.param.TextParam;

public class CsdSpinner extends Spinner implements AdapterView.OnItemSelectedListener, OnTapListener, StatefulUnit {
	
	private OnTap mListener = Listener.defaultOnTap(); 
	
	private List<String> mNames;
	private String mid;
	private int mSelected;

	public CsdSpinner(Context context) {
		super(context);
	}
	
	public CsdSpinner(Context context, String id, int initVal, List<String> names, final TextParam textParam) {
		super(context);
		mid = id;
		mSelected = initVal;
		mNames = names;
			
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
