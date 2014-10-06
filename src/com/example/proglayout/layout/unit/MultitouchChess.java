package com.example.proglayout.layout.unit;

import android.view.View;

import com.example.proglayout.csound.listener.Key2;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.UnitUtils;
import com.example.proglayout.layout.UnitUtils.WithInstrId;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;


public class MultitouchChess implements Unit {
	
	@Override
	public String getTag() {	
		return Units.MULITOUCH_CHESS;
	}
	
	@Override
	public View getView(final LayoutContext ctx, Object tagValue,
			final Param param, Param defaultParams, TrackState trackState, final LayoutParent layoutParent) {
		
		return UnitUtils.run(this, ctx, tagValue, new WithInstrId() {
			@Override
			public View apply(Integer id) {
				Integer maxTouch = param.getTouch().getTocuhLimit();
				
				com.example.proglayout.view.unit.MultitouchChess res = new com.example.proglayout.view.unit.MultitouchChess(ctx.getContext(), 
						maxTouch, param.getRange().getIntRangeX(), param.getRange().getIntRangeY(), param.getNames().getNameList(), 
						param.getColor(), param.getText());
				new Key2(id, maxTouch, res).addToCsound(ctx.getCsoundObj());								
				return res;
			}
		});		
	}
	
}