package com.example.proglayout.layout.unit;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.proglayout.R;
import com.example.proglayout.fragment.UiWatcherFragment;
import com.example.proglayout.layout.Json;
import com.example.proglayout.layout.Layout;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;
import com.example.proglayout.view.unit.RectButton;

public class Options implements Unit {
	
	@Override
	public String getTag() {	
		return Units.OPTIONS;
	}
	
	@Override
	public View getView(LayoutContext ctx, Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		if (Json.isArray(tagValue)) {
			JSONArray alts = (JSONArray) tagValue;	
			
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

}
