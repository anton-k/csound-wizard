package com.csound.wizard.layout.unit;

import org.json.simple.JSONArray;

import android.content.Context;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.csound.wizard.Const;
import com.csound.wizard.layout.Json;
import com.csound.wizard.layout.Layout;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;

public class Table implements Unit {
	@Override
	public String getTag() {
		return Const.TABLE;
	}
	
	@Override
	public View getView(LayoutContext ctx, Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		Context context = ctx.getContext();
		TableLayout table = new TableLayout(context);
		
		if (Json.isArray(tagValue)) {
			
			for (Object rawRow: (JSONArray) tagValue) {				
				table.addView(mkTableRow(ctx, rawRow, defaults, trackState));			
			}		
						
		} else {
			table.addView(Layout.getView(ctx, tagValue, defaults, LayoutParent.TABLE, trackState));			
		}
		
		return table;
	}
	
	private TableRow mkTableRow(LayoutContext ctx, Object obj, Param defaults, TrackState trackState) {
		TableRow res = new TableRow(ctx.getContext());
		
		if (Json.isArray(obj)) {			
			for (Object rawRow: (JSONArray) obj) {
				res.addView(Layout.getView(ctx, rawRow, defaults, LayoutParent.TABLE_ROW, trackState));			
			}		
		} else {
			res.addView(Layout.getView(ctx, obj, defaults, LayoutParent.TABLE_ROW, trackState));			
		}
		
		return res;
	}
}
