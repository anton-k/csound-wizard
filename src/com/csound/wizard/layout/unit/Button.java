package com.csound.wizard.layout.unit;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.CachedPress;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.CircleButton;

public class Button implements Unit {
	
	@Override
	public String getTag() { return Const.BUTTON; }
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {		
			
			@Override
			public View apply(String id) {
				CircleButton res = new CircleButton(ctx.getContext(), param.getColor());
				new CachedPress(id, res).addToCsound(ctx.getCsoundObj());								
				return res;
			}			
		});		
	}	
}
