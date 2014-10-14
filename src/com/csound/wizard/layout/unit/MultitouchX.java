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
import com.csound.wizard.view.unit.MultitouchPositionX;

public class MultitouchX implements Unit {
	
	@Override
	public String getTag() {	
		return Const.MULITOUCH_X;
	}
	
	@Override
	public View getView(final LayoutContext ctx, Object tagValue,
			final Param param, Param defaultParams, TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithInstrId() {
			@Override
			public View apply(Integer instrId) {	
				int maxTouch = param.getTouch().getTocuhLimit();				
				MultitouchPositionX res = new MultitouchPositionX(ctx.getContext(), maxTouch, 
						param.getRange().getIntRangeX(), param.getRange().getRangeY(), 
						param.getNames().getNameList(), 
						param.getColor(), param.getText());
				new Key2(instrId, maxTouch, res).addToCsound(ctx.getCsoundObj());
				return res;				
			}
		});		
	}
	
}
