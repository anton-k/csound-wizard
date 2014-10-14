package com.csound.wizard.layout.unit;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.Key2;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithInstrId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.MultitouchPosition;

public class Multitouch implements Unit {
	
	@Override
	public String getTag() {		
		return Const.MULITOUCH;
	}

	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithInstrId() {
			@Override
			public View apply(Integer instrId) {		
				Integer maxTouch = param.getTouch().getTocuhLimit();
				MultitouchPosition res = new MultitouchPosition(ctx.getContext(), maxTouch, 
						param.getRange().getRangeX(), param.getRange().getRangeY(), param.getColor());
				new Key2(instrId, maxTouch, res).addToCsound(ctx.getCsoundObj());								
				return res;
			}			
		});		
	}
	
}