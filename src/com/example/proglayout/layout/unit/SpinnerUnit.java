package com.example.proglayout.layout.unit;

import android.view.View;

import com.example.proglayout.csound.listener.CachedTap;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.unit.CsdSpinner;

public class SpinnerUnit implements Unit {
	
	@Override
	public String getTag() {		
		return Units.SPINNER;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {	
				int initVal = UnitUtils.getState(id, trackState, CsdSpinner.defaultState());				
				CsdSpinner res = new CsdSpinner(ctx.getContext(), id, initVal, param.getNames().getNameList(), param.getText());
				new CachedTap(id, initVal, res).addToCsound(ctx.getCsoundObj());				
				return res;				
			}
		});
		
		
					
	}
	
}


