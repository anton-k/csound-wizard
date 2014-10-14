package com.csound.wizard.model;

import java.util.HashMap;
import java.util.List;

public class ModelSettings {
	private HashMap<String,String> tracks;
	private List<Playlist> playlists; 	
	private int currentPlaylist = 0;
	private int currentTrack = 0;
	
	private RecentQueue<TrackRef> recentTracks;
	private RecentQueue<String> recentPlaylists;

}
