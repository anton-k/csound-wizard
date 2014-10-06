package com.example.proglayout;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;

import com.csounds.CsoundObj;
import com.example.proglayout.csound.channel.GetCsoundValue;
import com.example.proglayout.csound.channel.Output;

public class Player {
	
	public interface OutputCsoundValue extends Output, GetCsoundValue {}

	private CsoundObj csoundObj = new CsoundObj();
	private List<Output> outputs = new ArrayList<Output>();
	
	public Player() {}
	
	public void addValue(GetCsoundValue val) {
		csoundObj.addValueCacheable(val.getCsoundValue());
	}
	
	public void addValue(OutputCsoundValue val) {
		outputs.add(val);
		addValue(val);
	}
	
	public void play(Context ctx, String csdFile) {
		csoundObj.startCsound(Utils.createTempFile(ctx, csdFile));
		startOutputUpdates();
	}
	
	public void stop() {
		stopOutputUpdates();		
		csoundObj.stopCsound();		
	}
	
	// -----------------------------------------------------------
	
	private void startOutputUpdates() {
		if (!outputs.isEmpty()) {
			handler.postDelayed(runnable, 100);
		} 
	}

	private void stopOutputUpdates() {
		handler.removeCallbacks(runnable);			
	}
	
	private Handler handler = new Handler();
	
	private void updateOutputs() {
		for (Output x: outputs) {
			x.update();
		}
	}
	
	private final Runnable runnable = new Runnable() {
		public void run() {
			updateOutputs();
			handler.postDelayed(this, 100);
		}	
	};
	
}
