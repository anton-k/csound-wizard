package com.example.proglayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.csounds.CsoundObj;
import com.example.proglayout.csound.channel.Output;
import com.example.proglayout.model.CacheState;
import com.example.proglayout.model.Model;
import com.example.proglayout.model.TrackState;

public class App extends Application {	
	
	private CsoundObj csoundObj = new CsoundObj();	
	private Model model = null;
	private Boolean isPlay = false;
	private CacheState cache = new CacheState();
	private boolean isWatchingCurrentPlaylist = false;	
	private List<Output> outputs = new ArrayList<Output>();
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private Settings settings = new Settings();;
	
	public CsoundObj getCsoundObj() {
		return csoundObj;
	}
	
	private void startCsound(File file) {
		csoundObj.startCsound(file);
						
	}

	private void stopCsound() {
		csoundObj.stopCsound();		
	}
	
	public void play(File file) {
		if (isPlay) {			
			stop();						
		}
				
		startCsound(file);	
		startOutputUpdates();
		isPlay = true;
	}
		
	public void stop() {
		if (isPlay) {
			stopOutputUpdates();
			stopCsound();			
			isPlay = false;
		}		
	}
	
	public Model getModel() {
		return model;
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public void loadModel(Context ctx) {
		if (model == null) {
			model = Model.load(ctx);
		}	
	}
	
	public TrackState loadCurrentState(String track) {
		if (cache.containsKey(track)) {
			return cache.get(track);
		} else {
			TrackState st;
			try {
				st = TrackState.load(getCurrentStateFileName(track));
			} catch (IOException e) {
				st = TrackState.readDefaultState(track);
			}
			
			cache.put(track, st);					
			return st;
		}
	}
	
	public static String getCurrentStateDir(String trackName) {
		return FilenameUtils.concat(FilenameUtils.getFullPath(trackName), FilenameUtils.getBaseName(trackName) + "-state");		
	}
	
	public static String getCurrentStateFileName(String trackName) {
		return FilenameUtils.concat(getCurrentStateDir(trackName), "current.json");		
	}
	
	public void saveCurrentState(String track, TrackState value) {
		cache.put(track, value);		
	}
	
	public void saveCacheToDisk() {
		cache.saveToDisk();						
	}
	
	public void saveStateFromView(String trackName, View rootView) {		
		TrackState st;
		if (cache.containsKey(trackName)) {
			st = cache.get(trackName);
			st.saveFromView(rootView);
		} else {
			st = new TrackState();
			st.saveFromView(rootView);
			cache.put(trackName, st);
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Model.clear(this);
	}

	public void clearCurrentTrack() {
		String track = model.getCurrentTrack().getName();
		File dir = new File(getCurrentStateDir(track));
		if (dir.exists()) {
			try {
				FileUtils.deleteDirectory(dir);
			} catch (IOException e) {				
			}			
		}
		cache.remove(track);
	}	
	
	public void setWatchingCurrentPlaylist(boolean val) {
		isWatchingCurrentPlaylist = val;				
	}

	public boolean getIsWatchingCurrentPlaylist() {
		return isWatchingCurrentPlaylist;
	}


	public void addOutput(Output x) {
		outputs.add(x);
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
	
	private void startOutputUpdates() {
		if (!outputs.isEmpty()) {
			handler.postDelayed(runnable, 100);
		} 
	}

	private void stopOutputUpdates() {
		handler.removeCallbacks(runnable);			
	}

	public void setFreshCsoundState() {
		stopOutputUpdates();
		outputs = new ArrayList<Output>();	
	}

}
