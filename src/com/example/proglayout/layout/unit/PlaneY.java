package com.example.proglayout.layout.unit;

import java.util.Map.Entry;

import android.view.View;

import com.example.proglayout.Utils;
import com.example.proglayout.csound.listener.CachedSlide;
import com.example.proglayout.csound.listener.CachedTap;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.unit.PositionDiscreteY;

public class PlaneY implements Unit {
	
	@Override
	public String getTag() {		
		return Units.PLANE_Y;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {
				Entry<Integer,Float> initVal = UnitUtils.getStateIntegerFloatPair(id, trackState, PositionDiscreteY.defaultState());
				int y = initVal.getKey();
				float x = initVal.getValue();
				PositionDiscreteY res = new PositionDiscreteY(ctx.getContext(), id, y, x,				
						param.getRange().getRangeX(), param.getRange().getIntRangeY(), param.getNames().getNameList(), 
						param.getColor(), param.getText());
				
				if (ctx.needsConnection()) {
					new CachedSlide(Utils.addSuffix(id, "x"), x, res).addToCsound(ctx.getCsoundObj());
					new CachedTap(Utils.addSuffix(id, "y"), y, res).addToCsound(ctx.getCsoundObj());
				}				
				return res;
			}			
		});		
	}
	
}
