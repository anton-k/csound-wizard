package com.example.proglayout.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.proglayout.MainActivity;
import com.example.proglayout.R;
import com.example.proglayout.Utils;
import com.example.proglayout.model.Model;
import com.example.proglayout.model.TrackRef;

public class RecentFragment extends Fragment {
	
	@Override		
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		
		View res = inflater.inflate(R.layout.fragment_recent, container, false);
		
		Model model = Utils.getModel(this); 
		ListView lvTracks = (ListView) res.findViewById(R.id.recent_tracks);
		ArrayAdapter<TrackRef> tracksAdapter = new ArrayAdapter<TrackRef>(getActivity(),
			      android.R.layout.simple_list_item_1, model.getRecentTracks());
		lvTracks.setAdapter(tracksAdapter);
		
		lvTracks.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int item,
					long arg3) {
				ListView lv = (ListView) parent;
				TrackRef trackRef = (TrackRef) lv.getItemAtPosition(item);
				((MainActivity) getActivity()).goToTrack(trackRef);			
			}
		});
		
		ListView lvPlaylists = (ListView) res.findViewById(R.id.recent_playlists);
		ArrayAdapter<String> playlistsAdapter = new ArrayAdapter<String>(getActivity(),
			      android.R.layout.simple_list_item_1, model.getRecentPlaylistsNames());
		lvPlaylists.setAdapter(playlistsAdapter);
		
		lvPlaylists.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int item,
					long arg3) {
				String psName = (String) ((ListView) parent).getItemAtPosition(item);				
				((MainActivity) getActivity()).goToPlaylist(psName);			
			}
		});	
		
		return res;
	}

}
