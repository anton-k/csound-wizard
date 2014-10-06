package com.example.proglayout.layout.unit;

import android.view.View;

import com.example.proglayout.csound.listener.Key2;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils.WithInstrId;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.unit.MultitouchPosition;

public class Multitouch implements Unit {
	
	@Override
	public String getTag() {		
		return Units.MULITOUCH;
	}

	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithInstrId() {
			@Override
			public View apply(Integer instrId) {		
				Integer maxTouch = param.getTouch().getTocuhLimit();
				MultitouchPosition res = new MultitouchPosition(ctx.getContext(), maxTouch, 
						param.getRange().getRangeX(), param.getRange().getRangeY(), param.getColor());
				if (ctx.needsConnection()) {
					new Key2(instrId, maxTouch, res).addToCsound(ctx.getCsoundObj());
				}				
				return res;
			}			
		});		
	}
	
}