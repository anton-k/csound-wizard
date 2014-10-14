package com.csound.wizard.layout.unit;

import java.util.Map.Entry;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.Utils;
import com.csound.wizard.csound.listener.CachedSlide;
import com.csound.wizard.csound.listener.CachedTap;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.PositionDiscreteY;

public class PlaneY implements Unit {
	
	@Override
	public String getTag() {		
		return Const.PLANE_Y;
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
				
				new CachedSlide(Utils.addSuffix(id, "x"), x, res).addToCsound(ctx.getCsoundObj());
				new CachedTap(Utils.addSuffix(id, "y"), y, res).addToCsound(ctx.getCsoundObj());								
				return res;
			}			
		});		
	}
	
}
