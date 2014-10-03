package com.example.proglayout.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SerializationUtils;

import com.example.proglayout.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Base64;

public class Model {
	private HashMap<String,TrackData> tracks = new HashMap<String,TrackData>();
	private List<Playlist> playlists; 	
	private int currentPlaylist = 0;
	private int currentTrack = 0;
	private Settings settings;
	
	private static int RECENT_MAX_SIZE = 10;
	private RecentQueue<TrackRef> recentTracks;
	private RecentQueue<String> recentPlaylists;
		
	public Model() {		
		String[]
			ar1 = { "one", "two", "three" },
			ar2 = { "beetle", "moog", "roika", "zyam", "alice" };
		
		Playlist[] ps = 
			{ new Playlist("Foo", ar1),
			  new Playlist("Bar", ar2),
			  new Playlist("Baz", ar1) };
		
		playlists = new ArrayList<Playlist>();
		for (Playlist p: ps) {
			playlists.add(p);
		}
		
		recentTracks = new RecentQueue<TrackRef>(RECENT_MAX_SIZE);
		recentPlaylists = new RecentQueue<String>(RECENT_MAX_SIZE);
		settings = new Settings();
	}
	
	private static final String TEST_DIR = "test-json";
	
	private Playlist copyTestDataToCard(Context ctx) {	
		
		File sdCard = Environment.getExternalStorageDirectory();
		String dirName = sdCard.getAbsolutePath() + "/Books/test/";	
				
		List<String> names = new ArrayList<String>();		
		try {
			for(String f : ctx.getAssets().list(TEST_DIR)){
				String fileName = dirName + f;
				FileUtils.copyInputStreamToFile(ctx.getAssets().open(TEST_DIR + "/" + f), new File(fileName));
				names.add(fileName);
				tracks.put(fileName, new TrackData());
				
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return new Playlist(TEST_DIR, names);
	}
	
	private boolean isLoadedTest = false;
	
	public void loadTestData(Context ctx) {
		if (!isLoadedTest) {
			Iterator<Playlist> it = playlists.iterator();
			while (it.hasNext()) {
				Playlist p = it.next();
				if (p.getName().equals(TEST_DIR)) {
					it.remove();
				}				
			}
			
			playlists.add(copyTestDataToCard(ctx));
			setCurrentPlaylist(TEST_DIR);
			isLoadedTest = true;
		}				
	}
	
	
	private Model(ModelSettings m) {
		tracks = m.tracks;
		playlists = m.playlists;
		currentPlaylist = m.currentPlaylist;
		currentTrack = m.currentTrack;
		
		recentTracks = m.recentTracks;
		recentPlaylists = m.recentPlaylists;	
		settings = m.settings;
	}
	
	private static class ModelSettings implements Serializable {

		private static final long serialVersionUID = 1832463543815414805L;
		
		private HashMap<String,TrackData> tracks;
		private List<Playlist> playlists; 	
		private int currentPlaylist = 0;
		private int currentTrack = 0;
		private Settings settings;
		
		private RecentQueue<TrackRef> recentTracks;
		private RecentQueue<String> recentPlaylists;
		
		public ModelSettings(Model m) {
			tracks = m.tracks;
			playlists = m.playlists;
			currentPlaylist = m.currentPlaylist;
			currentTrack = m.currentTrack;
			
			recentTracks = m.recentTracks;
			recentPlaylists = m.recentPlaylists;	
			settings = m.settings;
		}	
	}
	
	private static final String settingsFile = "csd-wizard-settings";
	private static final String modelKey = "model";
	
	public static void clear(Context ctx) {
		SharedPreferences pref = ctx.getSharedPreferences(settingsFile, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(modelKey);
		editor.commit();				
	}
	
	public void save(Context ctx) {
		SharedPreferences pref = ctx.getSharedPreferences(settingsFile, 0);
		SharedPreferences.Editor editor = pref.edit();		
		editor.putString(modelKey, Base64.encodeToString(SerializationUtils.serialize(new ModelSettings(this)), Base64.DEFAULT));
		editor.commit();
	}
	
	public static Model load(Context ctx) {
		SharedPreferences pref = ctx.getSharedPreferences(settingsFile, 0);
		String text = pref.getString(modelKey, "");
		if (text.isEmpty()) {
			return new Model();
		} else {
			return new Model ((ModelSettings) SerializationUtils.deserialize(Base64.decode(text, Base64.DEFAULT)));
		}
		
	}
	
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	
	public Playlist getCurrentPlaylist() {
		return playlists.get(currentPlaylist);
	}	
	
	public void setCurrentPlaylist(int n) {				
		if (n != currentPlaylist) {
			logPlaylistChoice(n);
			currentTrack = 0;
			currentPlaylist = n;			
		}				
	}
	
	public void addPlaylist(String name) {
		playlists.add(new Playlist(name));		
	}
	
	public Boolean trackExists(int n) {
		return getCurrentPlaylist().getTracks().size() > n;
	}
	
	public int getCurrentTrackId() {
		return currentTrack;
	}

	public int getCurrentPlaylistId() {
		return currentPlaylist;
	}
	
	public String getPlaylistName(int n) {
		return playlists.get(n).getName();
	}
	
	public Playlist getPlaylistById(int n) {
		return playlists.get(n);		
	}
	
	public String getTrackName(int n) {
		return FilenameUtils.getName(getCurrentPlaylist().getTracks().get(n));
	}
	
	public String getCurrentTrackName() {
		return getTrackName(currentTrack);
	}
	
	public void removeTrackById(Playlist p, int location) {
		TrackRef ref = p.getTrackRefs().get(location);
		recentTracks.remove(ref);
		p.removeTrackById(location);
	}
	
	public void removePlaylistById(int location) {
		String name = playlists.get(location).getName();
		recentPlaylists.remove(name);
		
		playlists.remove(location);
		
		if (location == currentPlaylist && !recentPlaylists.isEmpty()) {
			String nextName = recentPlaylists.get(0);
			int i = 0;
			while (!nextName.equals(playlists.get(i))) {
				i++;
			}
			currentPlaylist = i;
			currentTrack = 0;
		} else if (location == currentPlaylist && recentPlaylists.isEmpty()) {
			currentPlaylist = 0;
			currentTrack = 0;			
		}
	}
	
	public Track getCurrentTrack() {		
		String name = getCurrentPlaylist().getTracks().get(currentTrack);			
		return new Track(name, tracks.get(name));
	}	
	
	public void saveTrack(String path) {
		tracks.put(path, new TrackData());
		getCurrentPlaylist().addTrack(path);		
	}
	
	public void saveTracksFromDir(String dir) {
		String[] exts = {"csd"};
		Collection<File> fs = FileUtils.listFiles(new File(dir), exts, true);
		for (File f: fs) {
			saveTrack(f.getAbsolutePath());						 
		}
	}
	
	public void saveLink(String trackName) {
		getCurrentPlaylist().getTracks().add(trackName);
	}
	
	public void setCurrentTrack(int n) {
		logTrackChoice(n);
		currentTrack = n;
	}
	
	public void setCurrentTrack(TrackRef ref) {
		logTrackChoice(ref);
		currentPlaylist = playlistPosition(ref.getPlaylistName());
		currentTrack = trackPosition(currentPlaylist, ref);		
	}
	
	public void setCurrentPlaylist(String name) {		
		setCurrentPlaylist(playlistPosition(name));		
	}
	
	private int playlistPosition(String ref) {
		int i = 0;
		while (!playlists.get(i).getName().equals(ref)) {
			i++;
		}
		return i;
	}
	
	private int trackPosition(int playlistId, TrackRef ref) {
		List<String> ts = playlists.get(playlistId).getTracks();
		String trackName = ref.getTrackName();
		int i = 0;
		while (!ts.get(i).equals(trackName)) {
			i++;
		}
		return i;	
	}
	
	public void nextTrack() {
		currentTrack = withinSize(currentTrack + 1, getCurrentPlaylist().getTracks().size());
		logTrackChoice(currentTrack);
	}
	
	public void prevTrack() {
		currentTrack = withinSize(currentTrack - 1, getCurrentPlaylist().getTracks().size());
		logTrackChoice(currentTrack);
	}
	
	private int withinSize(int n, int size) {
		return (size + n) % size;
	}
	
	public List<String> getPlaylistNames() {
		List<String> res = new ArrayList<String>();
		for (Playlist p: playlists) {
			res.add(p.getName());
		}
		return res;
	}

	
	public List<String> getRecentPlaylistsNames() {
		return IteratorUtils.toList(new ReverseListIterator<String>(IteratorUtils.toList(recentPlaylists.iterator())));
	}
	
	public List<TrackRef> getRecentTracks() {		
		return IteratorUtils.toList(new ReverseListIterator<TrackRef>(IteratorUtils.toList(recentTracks.iterator())));
	}
	
	public List<String> getCurrentTrackNames() {
		List<String> res = new ArrayList<String>();
		for (String s: getCurrentPlaylist().getTracks()) {
			res.add(FilenameUtils.getName(s));
		}
		return res;
	}
	
	public void removeStubs() {
		cleanTracks();
		cleanRecentTracks();
		cleanPlaylists();		
	}
	
	private void cleanTracks() {
		Iterator<String> it = tracks.keySet().iterator();
		while (it.hasNext()) {
			if (!(new File(it.next()).exists())) {
				it.remove();
			}
		}
	}
	
	private void cleanRecentTracks() {
		Iterator<TrackRef> it = recentTracks.iterator();
		while (it.hasNext()) {
			if (!(new File(it.next().getTrackName()).exists())) {
				it.remove();				
			}
		}
	}

	private void cleanPlaylists() {
		for (Playlist p: playlists) {
			cleanPlaylist(p);
		}
	}
	
	private void cleanPlaylist(Playlist p) {
		Iterator<String> it = p.getTracks().iterator(); 
		while (it.hasNext()) {
			if (!(tracks.containsKey(it.next()))) {
				it.remove();
			}
		}
	}	

	private void logTrackChoice(int id) {
		Playlist p = getCurrentPlaylist();
		String playlistName = p.getName();
		String trackName = p.getTracks().get(id);
		recentTracks.add(new TrackRef(trackName, playlistName));		
	}
	
	private void logTrackChoice(TrackRef ref) {
		recentTracks.add(ref);		
	}

	private void logPlaylistChoice(int id) {
		recentPlaylists.add(playlists.get(id).getName());		
	}


	public Settings getSettings() {		
		return settings;
	}	
	
} 
