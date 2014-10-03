package com.example.proglayout.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.example.proglayout.MainActivity;
import com.example.proglayout.R;
import com.example.proglayout.Utils;
import com.example.proglayout.model.Model;
import com.example.proglayout.model.Playlist;

public class ChooserFragment extends Fragment {
	
	private static final String
		
		TAG_TRACKS = "CHOOSE_TRACKS_FOR_";		
	
	private FragmentManager fm;	
	private HashMap<Integer,PlaylistSelection> selectedItems;	
	private ChooserAction action;
	
	public ChooserFragment() {		
	}
	
	public ChooserFragment(ChooserAction a) {
		action = a;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		
		View res = inflater.inflate(R.layout.fragment_chooser_main, container, false);
		fm = getFragmentManager();
		fm.beginTransaction()
			.replace(R.id.chooser_container, new ChooserPlaylistFragment())			
			.commit();
		
		selectedItems = new HashMap<Integer,PlaylistSelection>();
		
		Button btnOk = (Button) res.findViewById(R.id.action_ok);
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				action.act(selectedItems);
				clearBackFragments();
				((MainActivity) getActivity()).goToCurrentPlaylist();											
			}
		});
		
		
		Button btnCancel = (Button) res.findViewById(R.id.action_cancel);
		
		btnCancel.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) { 
				clearBackFragments();
				((MainActivity) getActivity()).goToCurrentPlaylist();
			}
		});	
		
		this.setRetainInstance(true);
		
		return res;
	}	
	
	private void clearBackFragments() {		
		fm.popBackStack();
	}
	
	public static class Add extends ChooserFragment {
		public Add(final Context ctx) {
			super(new ChooserFragment.ChooserAction() {
				@Override
				public void act(HashMap<Integer, PlaylistSelection> selectedItems) {
					Model m = Utils.getModel((Activity) ctx);
					
					for (Entry<Integer,PlaylistSelection> entry: selectedItems.entrySet()) {
						Integer playlistId = entry.getKey();
						PlaylistSelection selection = entry.getValue();
						
						Playlist p = m.getPlaylistById(playlistId);
						if (selection.getIsPlaylistSelected()) {
							List<String> tracks = p.getTracks();
							int n = tracks.size();
							for (int i = 0; i < n; i++) {
								String trackName = tracks.get(i);
								m.saveLink(trackName);
							}
						} else {
							for (Integer location: selection.getSelectedTracks()) {
								m.saveLink(p.getTracks().get(location));
							}
						}
					}
				}				
			});
		}		
	}
	
	public static class Delete extends ChooserFragment {
		public Delete(final Context ctx) {
			super(new ChooserFragment.ChooserAction() {
				@Override
				public void act(HashMap<Integer, PlaylistSelection> selectedItems) {
					Model m = Utils.getModel((Activity) ctx);
					
					for (Entry<Integer,PlaylistSelection> entry: selectedItems.entrySet()) {
						Integer playlistId = entry.getKey();
						PlaylistSelection selection = entry.getValue();
						
						if (selection.getIsPlaylistSelected()) {
							m.removePlaylistById(playlistId);
						} else {
							Playlist p = m.getPlaylistById(playlistId);
							int shift = 0;
							for (Integer location: selection.getSelectedTracks()) {
								m.removeTrackById(p, location - shift);
								shift++;
							}
						}
					}
										
				}				
			});
		}
	}

	
	public static interface ChooserAction {
		void act(HashMap<Integer,PlaylistSelection> selectedItems);
	}
	
	public class ChooserPlaylistFragment extends Fragment {	
		public ChooserPlaylistFragment() {	
			super();
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		    Bundle savedInstanceState) {		
					
			View res = inflater.inflate(R.layout.fragment_chooser_playlists, container, false);
			ListView lv = (ListView) res.findViewById(R.id.browse_list);
			List<String> ls = Utils.getModel(this).getPlaylistNames();
			
			lv.setAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, ls)					
			);    
			    
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
					view.setSelected(true);
					Integer item = (Integer) position;
					Playlist p = Utils.getModel(ChooserFragment.this).getPlaylists().get(item);
					if (!selectedItems.containsKey(item)) {
						selectedItems.put(item, new PlaylistSelection());
					}
					fm.beginTransaction()
						.replace(R.id.chooser_container, new ChooserTracksFragment(p, selectedItems.get(item)))
						.addToBackStack(TAG_TRACKS)
						.commit();
										
				}		    	
		    });	
			
			this.setRetainInstance(true);
					
			return res;		
		}		
	}

	public class ChooserTracksFragment extends Fragment {		
				
		private Playlist playlist = new Playlist();
		private PlaylistSelection selection = new PlaylistSelection();
		
		public ChooserTracksFragment() {	
			super();
		}
		
		public ChooserTracksFragment(Playlist p, PlaylistSelection s) {
			super();
			playlist = p;			
			selection = s;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		    Bundle savedInstanceState) {
		    // Inflate the layout for this fragment
		    View res = inflater.inflate(R.layout.fragment_chooser, container, false);
		    final ListView lv = (ListView) res.findViewById(R.id.browse_list);
		    		
		    List<String> ls = playlist.getTracks();
			  
		    lv.setAdapter(
		    		new ArrayAdapter<String>(getActivity(),
		  			      android.R.layout.simple_list_item_multiple_choice, ls)		    		
		    );
		    lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		    lv.setFocusableInTouchMode(false);
		    
		    lv.setOnItemClickListener(new OnItemClickListener() {@Override
			    public void onItemClick(AdapterView<?> arg0, View view,
			    		int item, long arg3) {
		    		CheckedTextView chk = (CheckedTextView) view;
		    		if (chk.isChecked()) {
		    			selection.getSelectedTracks().add(item);
		    		} else {
		    			selection.getSelectedTracks().remove((Integer) item);		    			
		    		}
		    		
			    }
			});
		    
		    for (Integer i: selection.getSelectedTracks()) {
		    	lv.setItemChecked(i, true);
		    }
		    		    	    
		    OnClickListener clickListener = new OnClickListener() {
		    	 
	            @Override
	            public void onClick(View v) {
	                CheckBox chk = (CheckBox) v;
	                int itemCount = lv.getCount();
	                for(int i=0 ; i < itemCount ; i++){
	                    lv.setItemChecked(i, chk.isChecked());
	                }	                
	                
	                if (chk.isChecked()) {
	                	selection.setAllTracksSelected(playlist.getTracks().size());
	                } else {
	                	selection.setNoneSelected();
	                }	                
	            }
	        };		    
		    
		    CheckBox selectAll = (CheckBox) res.findViewById(R.id.select_all);		    
		    selectAll.setChecked(selection.getIsAllSelected());	    
		    selectAll.setOnClickListener(clickListener);
		    
		    CheckBox selectPlaylist = (CheckBox) res.findViewById(R.id.select_playlist);
		    selectPlaylist.setChecked(selection.getIsPlaylistSelected());
		    selectPlaylist.setOnClickListener(new OnClickListener() {
		    	@Override
		    	public void onClick(View v) {
		    		selection.toggleSelectPlaylist();		    			    		
		    	}		    	
		    });
	
		    this.setRetainInstance(true);
		    return res;
		}	
		
	}	
	
	public static class PlaylistSelection {
		private Boolean isPlaylistSelected, isAllSelected;
		private List<Integer> selectedTracks;	
		
		public PlaylistSelection() {
			isPlaylistSelected = false;
			isAllSelected = false;
			selectedTracks = new ArrayList<Integer>();			
		}
		
		public Boolean getIsAllSelected() {
			return isAllSelected;
		}
		
		public Boolean getIsPlaylistSelected() {
			return isPlaylistSelected;
		}
		
		public List<Integer> getSelectedTracks() {
			return selectedTracks;
		}	
		
		public void setAllTracksSelected(int n) {
			selectedTracks = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) {
				selectedTracks.add(i);
			}
			isAllSelected = true;
		}
		
		public void setNoneSelected() {
			selectedTracks = new ArrayList<Integer>();
			isAllSelected = false;
		}
		
		public void toggleSelectPlaylist() {
			isPlaylistSelected = !isPlaylistSelected;			
		}		
	}
}
