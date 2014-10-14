package com.csound.wizard.layout.unit;

import java.util.Map.Entry;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.CachedSlide2;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.layout.param.RangeParam;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.Position;

public class Plane implements Unit {
	
	@Override
	public String getTag() {		
		return Const.PLANE;
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
				new CachedSlide2(id, x, y, res).addToCsound(ctx.getCsoundObj());				
				return res;
			}			
		});		
	}
	
}
