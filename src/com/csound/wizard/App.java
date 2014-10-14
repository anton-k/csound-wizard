package com.csound.wizard;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.csound.wizard.model.CacheState;
import com.csound.wizard.model.Model;
import com.csound.wizard.model.TrackState;

public class App extends Application {	
	
	private Player player = new Player();
	private String playerTrack = "";
	private Model model = null;	
	private CacheState cache = new CacheState();
	private boolean isWatchingCurrentPlaylist = false;	
		
	private Settings settings = new Settings();;
	
		
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

		
	public void setupNewPlayer() {
		player.stop();
		player = new Player();
	}
		
	public void play(String trackPath) {
		player.play(trackPath);							
	}
	
	public void stop() {
		player.stop();						
	}

	public Player getPlayer() {		
		return player;		
	}

	public void setupPlayerFor(String trackPath) {
		if (!playerTrack.equals(trackPath)) {
			setupNewPlayer();
			playerTrack = trackPath;
		}		
	}
	
	public boolean isSameTrack(String trackPath) {
		return playerTrack.equals(trackPath);						
	}

	public void clearPlayerTrack() {
		playerTrack = "";		
	}
}
