package com.csound.wizard.layout;


import java.io.InputStream;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint.Align;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.csound.wizard.Player;
import com.csound.wizard.Utils;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.layout.param.TextParam;
import com.csound.wizard.layout.unit.Hor;
import com.csound.wizard.layout.unit.Ver;
import com.csound.wizard.model.TrackState;

public class Layout {		
	public static View init(Context ctx, InputStream file, Player csd, TrackState trackState) {
		String text = Utils.readFile(file);
		return init(ctx, text, csd, trackState);		
	}

	public static View init(Context ctx, String text, Player csd, TrackState trackState) {		
		Object obj = JSONValue.parse(text);		
		LayoutContext context = new LayoutContext(ctx, csd);
		Param defaultParam = context.getApp().getModel().getSettings().getParam();
		
		if (obj == null) {
			return errorMalformedJson(context, text, new Param());
		} else {
			return getView(context, obj, defaultParam, LayoutParent.NONE, trackState);			
		}	
	}	
	
	public static View getView(LayoutContext ctx, Object rawObj, Param defaultParams, LayoutParent layoutParent, TrackState trackState) {
		View res = getViewNoBackground(ctx, rawObj, defaultParams, layoutParent, trackState);
		res.setBackgroundColor(defaultParams.getColor().getBkgColor());
		return res;		
	}
	
	public static View getViewNoBackground(LayoutContext ctx, Object rawObj, Param defaultParams, LayoutParent layoutParent, TrackState trackState) {
		JSONObject newDefaults = Json.getObject("set-default", rawObj);
		if (newDefaults != null) {
			defaultParams = Param.merge(Param.parse(newDefaults), defaultParams);
		}		
		
		Param currentParams;
		
		currentParams = Param.merge(Param.parse(rawObj), defaultParams);
		
		if (Json.isObject(rawObj)) {
			JSONObject obj = (JSONObject) rawObj;
			Iterator<Unit> it = Units.units.iterator();
			
			while (it.hasNext()) {
				Unit u = it.next();
				if (obj.containsKey(u.getTag())) {
					return setBkgColor(SetLayoutParam.setParams(
							u.getView(ctx, obj.get(u.getTag()), currentParams, defaultParams, trackState, layoutParent), 
							currentParams.getLayout(), layoutParent), currentParams.getColor().getBkgColor());
				}
			}	
					
			return defaultView(ctx, obj, currentParams);
		} else if (Json.isString(rawObj)) {
			return textView(ctx, (String) rawObj, currentParams);
		} else if (Json.isArray(rawObj)) {
			if (((Activity) ctx.getContext()).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				return new Ver().getView(ctx, rawObj, currentParams, defaultParams, trackState, layoutParent);
			} else {
				return new Hor().getView(ctx, rawObj, currentParams, defaultParams, trackState, layoutParent);
			}						
		} else {
			return textView(ctx, rawObj.toString(), currentParams);
		} 
	}	
	
	private static View setBkgColor(View x, Integer bkgColor) {
		x.setBackgroundColor(bkgColor);
		return x;
	}

	public static View defaultView(LayoutContext ctx, JSONObject obj, Param param) {		
		return textView(ctx, obj.toJSONString(), param);
	}
	
	public static View errorMalformedJson(LayoutContext ctx, String text, Param param) {
		return textView(ctx, "Error: Malformed JSON:\n\n" + text, param);
	}
	
	public static View errorMalformedUnitId(LayoutContext ctx, String text, Param param) {
		return textView(ctx, "Error: Malformed Unit Id:\n\n" + text, param);
	}
	
	public static View errorMalformedUnitRange(LayoutContext ctx, String text, Param param) {
		return textView(ctx, "Error: Malformed Unit Range:\n\n" + text, param);
	}

	public static View errorMalformedTabs(LayoutContext ctx, String text, Param param) {
		return textView(ctx, "Error: Malformed Tabs:\n\n" + text, param);
	}
	
	public static View textView(LayoutContext ctx, String text, Param param) {
		TextView res = new TextView(ctx.getContext());
		res.setText(text);
		
		setTextProperties(res, param.getText());		
		return res;		
	}	
	
	public static void setTextProperties(TextView tv, TextParam param) {
		// text parameters
		tv.setTextSize((int) (0.7f * param.getScale() * param.getSize()));
		tv.setTextColor(param.getColor());
		Align align = param.getAlign();
		if (align.equals(Align.CENTER)) {
			tv.setGravity(Gravity.CENTER);
		}
		if (align.equals(Align.LEFT)) {
			tv.setGravity(Gravity.LEFT);
		}
		if (align.equals(Align.RIGHT)) {
			tv.setGravity(Gravity.RIGHT);
		}	
		
	}
}

