package com.csound.wizard.layout.unit;

import android.view.View;
import android.widget.HorizontalScrollView;

import com.csound.wizard.Const;
import com.csound.wizard.layout.Json;
import com.csound.wizard.layout.Layout;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;

public class HorScroll implements Unit {
	
	@Override
	public String getTag() {	
		return Const.HOR_SCROLL;
	}
	
	@Override
	public View getView(LayoutContext ctx, Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		HorizontalScrollView res = new HorizontalScrollView(ctx.getContext());		
		if (Json.isArray(tagValue)) {
			res.addView(new Hor().getView(ctx, tagValue, param, defaults, trackState, LayoutParent.HOR));
		} else {
			res.addView(Layout.getView(ctx, tagValue, defaults, LayoutParent.HOR, trackState));
		}	
		return res;
	}
	
}
