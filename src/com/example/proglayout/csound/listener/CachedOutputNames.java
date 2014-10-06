package com.example.proglayout.csound.listener;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;

import com.example.proglayout.Player;
import com.example.proglayout.csound.channel.IntegerOutput;
import com.example.proglayout.csound.channel.Output;

public class CachedOutputNames implements Output {
	
	private List<String> mNames = new ArrayList<String>();
	private IntegerOutput mChn;
	private TextView mUnit;
	
	public CachedOutputNames(String id, TextView unit, List<String> names) {
		mChn = new IntegerOutput(id);
		mNames = names;
		mUnit = unit;		
	}
	
	@Override
	public void update() {
		
		if (mChn.hasNext()) {
			int i = mChn.getValue();
			i = Math.min(Math.max(0, i), mNames.size() - 1);
			mUnit.setText(mNames.get(i));		
		}				
	}
	
	public void addToCsound(Player player) {
		player.getCsoundObj().addValueCacheable(mChn);
		player.addOutput(this);
	}
}
