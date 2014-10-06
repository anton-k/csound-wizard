package com.example.proglayout.fragment;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proglayout.App;
import com.example.proglayout.Player;
import com.example.proglayout.R;
import com.example.proglayout.Utils;
import com.example.proglayout.layout.Layout;
import com.example.proglayout.model.TrackState;

public class UiCsdFragment extends UiWatcherFragment {
	
	private String mUiText;
	private String csdFile = "";
	private TrackState trackState;
	private Player player = new Player();
	
	private boolean 
		useCache,
		uiIsEmpty;

	private String trackPath;

	public static UiCsdFragment newInstance(String trackPath, boolean useCache) {
		UiCsdFragment res = new UiCsdFragment();
		res.trackPath = trackPath; 
		res.useCache = useCache;
		
		String uiText;
		try {
			InputStream is = FileUtils.openInputStream(new File(trackPath));
			res.csdFile = FileUtils.readFileToString(new File(trackPath));
			uiText = Utils.getUi(is);		
		} catch (Exception e) {
			uiText = ""; 
		}
		if (uiText.isEmpty()) {
			res.uiIsEmpty = true;										
		} else {
			res.uiIsEmpty = false;
			res.mUiText = uiText;			
		}
		
		return res;		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		
		if (uiIsEmpty) {			
			return noUiView(inflater, container);			
		} else {
			return setupUi(inflater, container, savedInstanceState);			
		}
		
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {	
		super.onSaveInstanceState(outState);
	}
	
	private View setupUi(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Activity ctx = getActivity();
		App app = (App) ctx.getApplication();
		boolean skipCsoundInit = (savedInstanceState != null);	
		
		if (useCache) {
			trackState = app.loadCurrentState(trackPath); 
		} else {
			trackState = TrackState.readDefaultState(trackPath);
		}		
		
		View res = inflater.inflate(R.layout.fragment_ui_csd, container, false);		
		View body = res.findViewById(R.id.ui_container);
		ViewGroup parent = (ViewGroup) body.getParent();
		int index = parent.indexOfChild(body);
		parent.removeView(body);
			
		View ui;
		if (mUiText.isEmpty()) {
			TextView tv = new TextView(getActivity());
			tv.setText(R.string.no_ui_csd);
			Layout.setTextProperties(tv, app.getModel().getSettings().getParam().getText());
			ui = tv;
		} else {
			ui = Layout.init(ctx, mUiText, player, trackState, skipCsoundInit);					
		}		
		
		parent.addView(ui, index);	
		res.setBackgroundColor(app.getModel().getSettings().getParam().getColor().getBkgColor());
		return res;
	}


	private View noUiView(LayoutInflater inflater, ViewGroup container) {
		App app = (App) getActivity().getApplication();				
		View res = inflater.inflate(R.layout.fragment_no_ui_csd, container, false);
		res.setBackgroundColor(app.getModel().getSettings().getParam().getColor().getBkgColor());
		return res;
	}
	
	public void play() {
		player.play(getActivity(), csdFile);				
	}
	
	public void stop() {
		player.stop();		
	}
}