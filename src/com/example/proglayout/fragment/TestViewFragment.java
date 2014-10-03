package com.example.proglayout.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proglayout.R;
import com.example.proglayout.view.Listener.OnPress;
import com.example.proglayout.view.unit.CircleButton;

public class TestViewFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View res = inflater.inflate(R.layout.fragment_views, container, false);
		
		CircleButton btn = (CircleButton) res.findViewById(R.id.btn1);
		btn.setOnPressListener(new OnPress() {
			public void press() {
				Toast.makeText(getActivity(), "press", Toast.LENGTH_SHORT).show();		
				
			}
			
			public void release() {
				Toast.makeText(getActivity(), "release", Toast.LENGTH_SHORT).show();		
								
			}

		});
		
		
		
		return res ;
	}

}
