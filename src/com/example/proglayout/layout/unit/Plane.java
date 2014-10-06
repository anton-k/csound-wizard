package com.example.proglayout.layout.unit;

import java.util.Map.Entry;

import android.view.View;

import com.example.proglayout.csound.listener.CachedSlide2;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.param.RangeParam;
import com.example.proglayout.view.unit.Position;

public class Plane implements Unit {
	
	@Override
	public String getTag() {		
		return Units.PLANE;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {
				Entry<Float,Float> initVal = UnitUtils.getStateFloatPair(id, trackState, Position.defaultState());
				float 
					x = initVal.getKey(), 
					y = initVal.getValue();
				RangeParam r = param.getRange();
				Position res = new Position(ctx.getContext(), id, x, y,
						r.getRangeX(), r.getRangeY(),
						param.getColor());
				if (ctx.needsConnection()) {
					new CachedSlide2(id, x, y, res).addToCsound(ctx.getCsoundObj());
				}
				return res;
			}			
		});		
	}
	
}
