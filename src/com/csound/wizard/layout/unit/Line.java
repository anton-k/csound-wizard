package com.csound.wizard.layout.unit;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.LineView;

public class Line implements Unit {
	@Override
	public String getTag() {			
		return Const.LINE;
	}
	
	@Override		
	public View getView(LayoutContext ctx, Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {				
		boolean isHor = Slider.getIsHor(param, layoutParent);	
		return new LineView(ctx.getContext(), param.getColor().getFstColor(), isHor);		
	}
}