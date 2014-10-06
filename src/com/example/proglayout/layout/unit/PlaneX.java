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
import com.example.proglayout.view.unit.PositionDiscreteX;

public class PlaneX implements Unit {
	
	@Override
	public String getTag() {		
		return Units.PLANE_X;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {
				Entry<Integer,Float> initVal = UnitUtils.getStateIntegerFloatPair(id, trackState, PositionDiscreteX.defaultState());
				int x = initVal.getKey();
				float y = initVal.getValue();				
				
				PositionDiscreteX res = new PositionDiscreteX(ctx.getContext(), id,	x, y,
						param.getRange().getIntRangeX(), param.getRange().getRangeY(), param.getNames().getNameList(), 
						param.getColor(), param.getText());
				
				if (ctx.needsConnection()) {
					new CachedTap(Utils.addSuffix(id, "x"), x, res).addToCsound(ctx.getCsoundObj());
					new CachedSlide(Utils.addSuffix(id, "y"), y, res).addToCsound(ctx.getCsoundObj());
				}				
				return res;
			}			
		});		
	}
	
}

