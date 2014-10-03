package com.example.proglayout.layout.unit;

import java.util.List;

import android.view.View;

import com.example.proglayout.csound.listener.CachedTap;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.unit.Radio;

public class HorRadio implements Unit {
	
	@Override
	public String getTag() {		
		return Units.HOR_RADIO;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {
				List<String> names = param.getNames().getNameList();		
				int initVal = UnitUtils.getState(id, trackState, Radio.defaultState());
				boolean isHor = true;
				Radio res = new Radio(ctx.getContext(), id, initVal, names, isHor, param.getText());
				new CachedTap(id, initVal, res).addToCsound(ctx.getCsoundObj());
				return res;
			}				
		});
	}
	
}


