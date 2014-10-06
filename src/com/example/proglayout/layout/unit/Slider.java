package com.example.proglayout.layout.unit;

import android.view.View;

import com.example.proglayout.csound.listener.CachedSlide;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;

public class Slider implements Unit {
	@Override
	public String getTag() {			
		return Units.SLIDER;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {		
		
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {	
				float initVal = UnitUtils.getState(id, trackState, com.example.proglayout.view.unit.Slider.defaultState());
				boolean isHor = getIsHor(param, layoutParent); 
				com.example.proglayout.view.unit.Slider slider = new com.example.proglayout.view.unit.Slider(
						ctx.getContext(), id, initVal, 
						param.getRange().getRange(), 
						param.getColor(), isHor);
				if (ctx.needsConnection()) {
					new CachedSlide(id, initVal, slider).addToCsound(ctx.getCsoundObj());
				}
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
