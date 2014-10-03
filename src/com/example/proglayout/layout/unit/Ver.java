package com.example.proglayout.layout.unit;

import org.json.simple.JSONArray;

import android.view.View;
import android.widget.LinearLayout;

import com.example.proglayout.layout.Json;
import com.example.proglayout.layout.Layout;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;

public class Ver implements Unit {
	@Override
	public String getTag() {			
		return Units.VER;
	}
	
	@Override
	public View getView(LayoutContext ctx, Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {						
		LinearLayout ll = ver(ctx);		
		if (Json.isArray(tagValue)) {
			for (Object x: (JSONArray) tagValue) {
				ll.addView(Layout.getView(ctx, x, defaults, LayoutParent.VER, trackState));
			}
		} else {
			ll.addView(Layout.getView(ctx, tagValue, defaults, LayoutParent.VER, trackState));
		}	
		ll.setBackgroundColor(param.getColor().getBkgColor());
		return ll;						
	}	
	
	private LinearLayout ver(LayoutContext ctx) {
		LinearLayout ll = new LinearLayout(ctx.getContext());
		ll.setOrientation(LinearLayout.VERTICAL);		
		return ll;
	}
	
}
