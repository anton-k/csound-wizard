package com.example.proglayout.fragment;

import com.example.proglayout.Utils;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CurrentTrackFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		    Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText(Utils.getModel(this).getCurrentTrack().getName());
		return tv;		
	}
	
}
