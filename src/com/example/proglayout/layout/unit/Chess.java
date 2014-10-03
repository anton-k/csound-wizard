package com.example.proglayout.layout.unit;

import java.util.Map.Entry;

import android.view.View;

import com.example.proglayout.csound.listener.CachedTap2;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;

public class Chess implements Unit {
	
	@Override
	public String getTag() {		
		return Units.CHESS;
	}
	
	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithId() {
			@Override
			public View apply(String id) {
								
				Entry<Integer,Integer> initVal = UnitUtils.getState(id, trackState, com.example.proglayout.view.unit.Chess.defaultState());				
				int 
					x = initVal.getKey(),
					y = initVal.getValue();
				com.example.proglayout.view.unit.Chess res = new com.example.proglayout.view.unit.Chess(ctx.getContext(), id,
						param.getRange().getIntRangeX(), param.getRange().getIntRangeY(), x, y,
						param.getNames().getNameList(), 
						param.getColor(), param.getText());
				new CachedTap2(id, x, y, res).addToCsound(ctx.getCsoundObj());				
				return res;
			}			
		});		
	}

}
