package com.csound.wizard.layout.unit;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.csound.listener.KeyPress2;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.UnitUtils.WithInstrId;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.TapToggleBoard;


public class TapToggle implements Unit {
	
	@Override
	public String getTag() {		
		return Const.TAP_TOGGLE;
	}

	@Override
	public View getView(final LayoutContext ctx, final Object tagValue, final Param param, final Param defaults, final TrackState trackState, final LayoutParent layoutParent) {
		return UnitUtils.run(this, ctx, tagValue, new WithInstrId() {
			@Override
			public View apply(Integer id) {	
				int nx = param.getRange().getIntRangeX();				
				int ny = param.getRange().getIntRangeY();
				String strId = Integer.toString(id);
				boolean initVal[] = UnitUtils.getState(strId, nx, ny, trackState, TapToggleBoard.defaultState(nx, ny));
				TapToggleBoard res = new TapToggleBoard(ctx.getContext(), strId, initVal, nx, ny, 
						param.getNames().getNameList(), param.getColor(), param.getText());
				new KeyPress2(id, nx, ny, initVal, res).addToCsound(ctx.getCsoundObj());
				return res;
			}			
		});		
	}
	
}