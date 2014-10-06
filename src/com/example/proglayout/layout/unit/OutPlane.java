package com.example.proglayout.layout.unit;

import java.util.Map.Entry;

import android.view.View;

import com.example.proglayout.csound.listener.CachedOutputSlide2;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.unit.Position;

public class OutPlane implements Unit {
	
	@Override
	public String getTag() {
		return Units.OUT_PLANE;
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
				Position res = new Position(ctx.getContext(), id, x, y, 
					param.getRange().getRangeX(), param.getRange().getRangeY(),
					param.getColor());
				new CachedOutputSlide2(id, res).addToCsound(ctx.getPlayer());				
				res.setOutputOnlyMode();				
				return res;
			}			
		});		
	}
	
	
}

