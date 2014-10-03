package com.example.proglayout.fragment;

import com.example.proglayout.App;
import com.example.proglayout.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoUiCsdFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		App app = (App) getActivity().getApplication();				
		View res = inflater.inflate(R.layout.fragment_no_ui_csd, container, false);
		res.setBackgroundColor(app.getModel().getSettings().getParam().getColor().getBkgColor());
		return res;
	}
	
}
