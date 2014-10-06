package com.example.proglayout.layout.unit;

import android.view.View;

import com.example.proglayout.csound.listener.Tap2;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithInstrId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.unit.TapClickBoard;

public class TapClick implements Unit {
	
	@Override
	public String getTag() {		
		return Units.TAP_CLICK;
	}

	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithInstrId() {
			@Override
			public View apply(Integer id) {				
				TapClickBoard res = new TapClickBoard(ctx.getContext(), 
						param.getRange().getIntRangeX(), param.getRange().getIntRangeY(), param.getNames().getNameList(),						
						param.getColor(), param.getText());
				if (ctx.needsConnection()) {
					new Tap2(id, res).addToCsound(ctx.getCsoundObj());
				}
				return res;
			}			
		});		
	}	
}
