package com.example.proglayout.layout.unit;

import android.view.View;

import com.example.proglayout.csound.listener.CachedPress;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.unit.CircleButton;

public class Button implements Unit {
	
	@Override
	public String getTag() { return Units.BUTTON; }
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {		
			
			@Override
			public View apply(String id) {
				CircleButton res = new CircleButton(ctx.getContext(), param.getColor());
				if (ctx.needsConnection()) {
					new CachedPress(id, res).addToCsound(ctx.getCsoundObj());
				}				
				return res;
			}			
		});		
	}	
}
