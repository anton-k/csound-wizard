package com.csound.wizard.layout.unit;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.CachedSlide;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.Dial;

public class Knob implements Unit {
	
	@Override
	public String getTag() {
		return Const.KNOB;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {
				float initVal = UnitUtils.getState(id, trackState, Dial.defaultState());				
				Dial res = new Dial(ctx.getContext(), id, initVal, param.getRange().getRange(), param.getColor());
				new CachedSlide(id, initVal, res).addToCsound(ctx.getCsoundObj());								
				return res;
			}			
		});		
	}
	
}
