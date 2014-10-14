package com.csound.wizard.layout.unit;

import java.util.Map.Entry;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.CachedOutputSlide2;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.Position;

public class OutPlane implements Unit {
	
	@Override
	public String getTag() {
		return Const.OUT_PLANE;
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

