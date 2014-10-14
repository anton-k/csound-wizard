package com.csound.wizard.layout.unit;

import android.view.View;
import android.widget.TextView;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.CachedOutputNamesFloat;
import com.csound.wizard.layout.Layout;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;

public class ShowFloats implements Unit {
	
	@Override
	public String getTag() {	
		return Const.SHOW_FLOATS;
	}
	
	@Override
	public View getView(final LayoutContext ctx, Object tagValue,
			final Param param, Param defaultParams, TrackState trackState, final LayoutParent layoutParent) {
		
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {
				TextView tv = new TextView(ctx.getContext());
				Layout.setTextProperties(tv, param.getText());
				new CachedOutputNamesFloat(id, tv).addToCsound(ctx.getPlayer());				
				return tv;				
			}
		});		
		
	}

}
