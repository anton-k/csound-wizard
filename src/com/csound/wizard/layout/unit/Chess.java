package com.csound.wizard.layout.unit;

import java.util.Map.Entry;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.CachedTap2;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;

public class Chess implements Unit {
	
	@Override
	public String getTag() {		
		return Const.CHESS;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {
								
				Entry<Integer,Integer> initVal = UnitUtils.getState(id, trackState, com.csound.wizard.view.unit.Chess.defaultState());				
				int 
					x = initVal.getKey(),
					y = initVal.getValue();
				com.csound.wizard.view.unit.Chess res = new com.csound.wizard.view.unit.Chess(ctx.getContext(), id,
						param.getRange().getIntRangeX(), param.getRange().getIntRangeY(), x, y,
						param.getNames().getNameList(), 
						param.getColor(), param.getText());
				new CachedTap2(id, x, y, res).addToCsound(ctx.getCsoundObj());				
				return res;
			}			
		});		
	}

}
