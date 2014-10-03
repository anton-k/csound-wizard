package com.example.proglayout.layout.unit;

import android.view.View;
import android.widget.LinearLayout;

import com.example.proglayout.layout.Json;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;

public class Empty implements Unit {
	@Override
	public String getTag() {			
		return Units.EMPTY;
	}
	
	@Override		
	public View getView(LayoutContext ctx, Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {				
		int size = 0;
		if (Json.isNumber(tagValue)) {
			size = Json.getInt(tagValue);
		}
		return emptyView(ctx, size);		
	}
	
	private View emptyView(LayoutContext ctx, int size) {
		View v = new View(ctx.getContext());
		v.setLayoutParams(new LinearLayout.LayoutParams(size, size));
		return v;		
	}
}
