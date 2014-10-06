package com.example.proglayout.layout.unit;

import android.view.View;

import com.example.proglayout.csound.listener.CachedPress;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.unit.ToggleButton;

public class Toggle implements Unit {
	private static final double EPS = 0.0001;
	
	@Override
	public String getTag() {			
		return Units.TOGGLE;
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
