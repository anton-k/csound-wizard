package com.example.proglayout.layout.unit;

import android.view.View;

import com.example.proglayout.csound.listener.CachedOutputSlide;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;

public class OutSlider implements Unit {
	@Override
	public String getTag() {			
		return Units.OUT_SLIDER;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {		
		
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {	
				float initVal = UnitUtils.getState(id, trackState, com.example.proglayout.view.unit.Slider.defaultState());				
				com.example.proglayout.view.unit.Slider slider = new com.example.proglayout.view.unit.Slider(
						ctx.getContext(), id, initVal,
						param.getRange().getRange(),
						param.getColor(), Slider.getIsHor(param, layoutParent));
				new CachedOutputSlide(id, slider).addToCsound(ctx.getPlayer());				
				slider.setOutputOnlyMode();
				return slider;
			}			
		});		
	}	
	
	
}
