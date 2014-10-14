package com.csound.wizard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import com.csound.wizard.csound.channel.Output;
import com.csounds.CsoundObj;

public class Player {
	
	private boolean isRunning = false;	
	private CsoundObj csoundObj = new CsoundObj();
	private List<Output> outputs = new ArrayList<Output>();
	
	public Player() {}
		
	public void play(String csdFile) {
		if (!isRunning) {
			isRunning = true;
			csoundObj.setAudioInEnabled(true);
			csoundObj.setMessageLoggingEnabled(false);
			csoundObj.startCsound(new File(csdFile));
			startOutputUpdates();
		}
	}
	
	public void stop() {
		if (isRunning) {
			isRunning = false;
			stopOutputUpdates();		
			csoundObj.stopCsound();
		}
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
	public CsoundObj getCsoundObj() {
		return csoundObj;
	}

	public void addOutput(Output x) {
		outputs.add(x);				
	}

	public boolean isRunning() {
		return isRunning;
	}
	
}
