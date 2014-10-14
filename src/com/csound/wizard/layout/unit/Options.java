package com.csound.wizard.layout.unit;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.csound.wizard.Const;
import com.csound.wizard.fragment.UiWatcherFragment;
import com.csound.wizard.layout.Json;
import com.csound.wizard.layout.Layout;
import com.csound.wizard.layout.LayoutContext;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.Units.StatefulUnit;
import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.unit.RectButton;
import com.example.proglayout.R;

public class Options implements Unit {
	
	@Override
	public String getTag() {	
		return Const.OPTIONS;
	}
	
	@Override
	public View getView(LayoutContext ctx, Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		if (Json.isArray(tagValue)) {
			JSONArray alts = (JSONArray) tagValue;	
			
			String id = ctx.getFreshId();
			LinearLayout ll = new LinearLayout(ctx.getContext());
			ll.setOrientation(LinearLayout.VERTICAL);		
			
			for (Object rawEntry: alts) {
				JSONObject entry = (JSONObject) rawEntry;
				Iterator<String> it = entry.keySet().iterator();
				String key = it.next();				
				ll.addView(optionItem(ctx, key, entry.get(key), defaults, trackState));
			}
			
			return ll;
		} else {
			return Layout.errorMalformedJson(ctx, getTag() + ": " + tagValue.toString(), defaults);
		}
	}
	
	private View optionItem(final LayoutContext ctx, final String name, final Object screen, final Param defaults, TrackState trackState) {
		RectButton b = new RectButton(ctx.getContext(), defaults.getColor(), defaults.getText());
		final View v = Layout.getView(ctx, screen, defaults, LayoutParent.NONE, trackState);
		
		final Fragment f = new UiWatcherFragment() {
			@Override
			public View onCreateView(LayoutInflater inflater,
					ViewGroup container, Bundle savedInstanceState) {
				v.setBackgroundColor(defaults.getColor().getBkgColor());
				setRetainInstance(true);
				return v;
			}
			
			@Override
			public void onDetach() {
				super.onDetach();
				
								
			}
		};
		
		b.setText(name);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				FragmentManager fm = ((Activity) ctx.getContext()).getFragmentManager();
				fm.beginTransaction()
					.replace(R.id.container, f, name)
					.addToBackStack(name)
					.commit();								
			}			
		});		
		return b;
	}
	
	private static class Buttons extends LinearLayout implements StatefulUnit {
		private String mid;
		private int choice = -1;

		public Buttons(Context context, String id) {
			super(context);		
		}
		
		@Override
		public String getUnitId() {			
			return mid;
		}
		
		@Override
		public double[] getUnitState() {		
			return UnitUtils.getUnitStateFloat(choice);
		}
		
	}

}
