package com.example.proglayout.layout.unit;

import org.json.simple.JSONArray;

import android.content.Context;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.proglayout.layout.Json;
import com.example.proglayout.layout.Layout;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;

public class Table implements Unit {
	@Override
	public String getTag() {
		return Units.TABLE;
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
