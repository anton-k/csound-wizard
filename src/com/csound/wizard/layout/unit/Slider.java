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

public class Slider implements Unit {
	@Override
	public String getTag() {			
		return Const.SLIDER;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {		
		
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {	
				float initVal = UnitUtils.getState(id, trackState, com.csound.wizard.view.unit.Slider.defaultState());
				boolean isHor = getIsHor(param, layoutParent); 
				com.csound.wizard.view.unit.Slider slider = new com.csound.wizard.view.unit.Slider(
						ctx.getContext(), id, initVal, 
						param.getRange().getRange(), 
						param.getColor(), isHor);
				new CachedSlide(id, initVal, slider).addToCsound(ctx.getCsoundObj());				
				return slider;
			}			
		});		
	}	
	
	public static boolean getIsHor(Param param, LayoutParent layoutParent) {
		if (param.getLayout() != null && param.getLayout().getOrient() != null) {
			return param.getLayout().getOrient();
		} else {
			return layoutParent == LayoutParent.VER;
		}
	}	
	
}
