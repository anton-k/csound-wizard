package com.example.proglayout.layout.unit;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proglayout.R;
import com.example.proglayout.fragment.UiWatcherFragment;
import com.example.proglayout.layout.Json;
import com.example.proglayout.layout.Layout;
import com.example.proglayout.layout.LayoutContext;
import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.Units;
import com.example.proglayout.layout.Units.Unit;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;

public class Tabs implements Unit {
	@Override
	public String getTag() {	
		return Units.TABS;
	}
	
	@Override
	public View getView(LayoutContext ctx, Object tagValue, final Param param, final Param defaults, TrackState trackState, final LayoutParent layoutParent) {
		if (isTabs(tagValue)) {
			ActionBar bar = ((Activity) ctx.getContext()).getActionBar();
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			
			JSONArray alts = (JSONArray) tagValue;	
			
			for (Object rawEntry: alts) {
				JSONObject entry = (JSONObject) rawEntry;
				Iterator<String> it = entry.keySet().iterator();
				String key = it.next();			
				
				bar.addTab(initTab(bar, ctx, key, Layout.getView(ctx, entry.get(key), defaults, LayoutParent.NONE, trackState)));			
			}		
			return new View(ctx.getContext());
		} else {
			return Layout.errorMalformedTabs(ctx, getTag() + ": " + tagValue.toString(), defaults);
		}
		
	}
	
	private Tab initTab(ActionBar bar, LayoutContext ctx, final String name, final View view) {
		Tab res = bar.newTab();
		res.setText(name);
		res.setTabListener(new TabListener() {
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				ft.replace(R.id.container, new UiWatcherFragment() {
					@Override
					public View onCreateView(LayoutInflater inflater,
							ViewGroup container, Bundle savedInstanceState) {
						return view;
					}					
				}, name);			
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {				
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub				
			}
			
		});
		return res;
	}
	
	private boolean isTabs(Object tagValue) {
		boolean res = true;
		
		if (Json.isArray(tagValue)) {
			for (Object item: (JSONArray) tagValue) {
				if (!Json.isObject(item)) {
					return false;
				}
			}
		} else {
			return false;
		}
		
		return res;
	}
}
