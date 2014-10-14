package com.csound.wizard.layout.unit;

import android.view.View;
import android.widget.LinearLayout;

import com.csound.wizard.Const;
import com.csound.wizard.layout.Json;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;

public class Empty implements Unit {
	@Override
	public String getTag() {			
		return Const.EMPTY;
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
