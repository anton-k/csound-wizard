package com.csound.wizard.layout.unit;

import org.json.simple.JSONArray;

import android.view.View;
import android.widget.LinearLayout;

import com.csound.wizard.Const;
import com.csound.wizard.layout.Json;
import com.csound.wizard.layout.Layout;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;

public class Hor implements Unit {
	@Override
	public String getTag() {			
		return Const.HOR;
	}
				
	@Override
	public View getView(LayoutContext ctx, Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {						
		LinearLayout ll = hor(ctx);		
		if (Json.isArray(tagValue)) {
			for (Object x: (JSONArray) tagValue) {
				ll.addView(Layout.getView(ctx, x, defaults, LayoutParent.HOR, trackState));				
			}
		} else {
			ll.addView(Layout.getView(ctx, tagValue, defaults, LayoutParent.HOR, trackState));
		}		
		ll.setBackgroundColor(param.getColor().getBkgColor());
		return ll;						
	}	
	
	private LinearLayout hor(LayoutContext ctx) {
		LinearLayout ll = new LinearLayout(ctx.getContext());
		ll.setOrientation(LinearLayout.HORIZONTAL);		
		return ll;
	}
}
