package com.example.proglayout.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csounds.CsoundObj;
import com.example.proglayout.App;
import com.example.proglayout.Player;
import com.example.proglayout.R;
import com.example.proglayout.layout.Layout;
import com.example.proglayout.model.TrackState;

public class UiCsdFragment extends UiWatcherFragment {
	
	private String mUiText;
	private TrackState trackState;
	private Player player = new Player();

	public UiCsdFragment(String track, String uiText, TrackState state) {
		mUiText = uiText;
		trackState = state;	
	}
	
	public UiCsdFragment() {
		mUiText = "";
		trackState = new TrackState();		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		
		View res = inflater.inflate(R.layout.fragment_ui_csd, container, false);		
		View body = res.findViewById(R.id.ui_container);
		ViewGroup parent = (ViewGroup) body.getParent();
		int index = parent.indexOfChild(body);
		parent.removeView(body);
						
		Activity ctx = getActivity();
		App app = (App) ctx.getApplication();		
		View ui;
		if (mUiText.isEmpty()) {
			TextView tv = new TextView(getActivity());
			tv.setText(R.string.no_ui_csd);
			ui = tv;
		} else {
			ui = Layout.init(ctx, mUiText, csoundObj, trackState);					
		}		
		
		parent.addView(ui, index);	
		res.setBackgroundColor(app.getModel().getSettings().getParam().getColor().getBkgColor());
		return res;
	}	
}