package com.csound.wizard.layout.unit;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.CachedOutputSlide;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.RainbowCircle;

public class OutRainbowCircle implements Unit {
	@Override
	public String getTag() {			
		return Const.RAINBOW_CIRCLE;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {		
		
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {								
				RainbowCircle res = new RainbowCircle(ctx.getContext(), param.getRange().getRange());
				new CachedOutputSlide(id, res).addToCsound(ctx.getPlayer());				
				return res;
			}			
		});		
	}	
	
	
}

