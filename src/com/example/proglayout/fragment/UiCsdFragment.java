package com.example.proglayout.fragment;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.app.FragmentManager;
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
	private TrackState trackState;
				
	private boolean 
		useCache = true,
		uiIsEmpty,
		isPlay;

	private String trackPath;

	public static UiCsdFragment newInstance(String trackPath, boolean useCache, boolean isPlay) {
		UiCsdFragment res = new UiCsdFragment();
		res.trackPath = trackPath; 
		res.useCache = useCache;
		res.isPlay = isPlay;
		
		String uiText;
		try {
			InputStream is = FileUtils.openInputStream(new File(trackPath));			
			uiText = Utils.getUi(is);
			is.close();
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
	
	public String getTrackPath() {
		return trackPath;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		
		View res;
		App app = (App) getActivity().getApplication();
				
		if (savedInstanceState != null) {
			uiIsEmpty = savedInstanceState.getBoolean("uiIsEmpty");
			trackPath = savedInstanceState.getString("trackPath");
			mUiText = savedInstanceState.getString("uiText");			
		}		
		
		if (uiIsEmpty) {			
			res = noUiView(inflater, container);			
		} else {
			res = setupUi(inflater, container, savedInstanceState);			
		}
		
		if (savedInstanceState == null) {
			if (isPlay) {
				app.play(trackPath);
			} else {
				app.stop();
			}			
		}
		
		return res;			
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {	
		super.onSaveInstanceState(outState);
		outState.putBoolean("uiIsEmpty", uiIsEmpty);
		outState.putString("trackPath", trackPath);
		outState.putString("uiText", mUiText);		
	}
	
	private View setupUi(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Activity ctx = getActivity();
		App app = (App) ctx.getApplication();		
		
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
		Player player = app.getPlayer();
		if (mUiText.isEmpty()) {
			TextView tv = new TextView(getActivity());
			tv.setText(R.string.no_ui_csd);
			Layout.setTextProperties(tv, app.getModel().getSettings().getParam().getText());
			ui = tv;
		} else {
			ui = Layout.init(ctx, mUiText, player, trackState);			
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

	
	public static void set(FragmentManager fm, UiCsdFragment x) {
		fm.beginTransaction().replace(R.id.container, x).commit();	
	}	
}