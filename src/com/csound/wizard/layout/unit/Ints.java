package com.csound.wizard.layout.unit;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.CachedTap;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.layout.param.Types.Range;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.CsdNumberPicker;

public class Ints implements Unit {
	
	@Override
	public String getTag() {		
		return Const.INTS;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {	
				Range r = param.getRange().getRange();
				int initVal = UnitUtils.getState(id, trackState, CsdNumberPicker.defaultState(r));				
				CsdNumberPicker res = new CsdNumberPicker(ctx.getContext(), id, initVal, r, param.getText());
				new CachedTap(id, initVal, res).addToCsound(ctx.getCsoundObj());				
				return res;				
			}
		});					
	}	
}


