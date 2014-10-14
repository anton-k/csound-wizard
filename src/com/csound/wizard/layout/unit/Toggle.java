package com.csound.wizard.layout.unit;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.CachedPress;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.ToggleButton;

public class Toggle implements Unit {
	private static final double EPS = 0.0001;
	
	@Override
	public String getTag() {			
		return Const.TOGGLE;
	}

	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {				
				boolean initVal = getState(id, trackState);			
				ToggleButton res = new ToggleButton(ctx.getContext(), id, initVal, param.getColor());	
				new CachedPress(id, res).addToCsound(ctx.getCsoundObj());				
				return res;
			}			
		});		
	}
	
	private static boolean getState(String id, TrackState trackState) {
		if (trackState.containsKey(id)) {
			double[] ds = trackState.get(id);
			return Math.abs(ds[0]) > EPS;			
		} else {
			return ToggleButton.defaultState();
		}		
	}
	
}
