package com.example.proglayout.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFragment extends Fragment { 
	private String mText;
	private int mTextSize;
	private int mColor;
		
	public TextFragment(int color, int textSize, String text) {
		mText = text;
		mColor = color;
		mTextSize = textSize;
	}
	
	@Override	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(getActivity());
		tv.setTextColor(mColor);
		tv.setText(mText);
		tv.setTextSize(mTextSize);
		return tv;
	}
	
}
