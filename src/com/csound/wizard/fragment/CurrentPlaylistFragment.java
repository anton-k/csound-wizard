package com.csound.wizard.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.csound.wizard.App;
import com.csound.wizard.DirectoryChooserDialog;
import com.csound.wizard.MainActivity;
import com.csound.wizard.Utils;
import com.csound.wizard.model.Model;
import com.example.proglayout.R;
import com.ipaulpro.afilechooser.utils.FileUtils;

public class CurrentPlaylistFragment extends Fragment {
	
	private ArrayAdapter<String> adapter;
	private static final int REQUEST_CHOOSER = 1234;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	    // Inflate the layout for this fragment
	    View res = inflater.inflate(R.layout.fragment_current_playlist, container, false);
	    ListView lv = (ListView) res.findViewById(R.id.current_playlist);	
	    	    
	    adapter = new ArrayAdapter<String>(getActivity(),
			      android.R.layout.simple_list_item_1, Utils.getModel(this).getCurrentTrackNames()) {
	    	
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				Model model = Utils.getModel(CurrentPlaylistFragment.this);				
				View res = super.getView(position, convertView, parent);
				if (position == model.getCurrentTrackId()) {
					res.setBackgroundColor(Color.parseColor("#87CEFA"));					
				} else {
					res.setBackgroundColor(Color.TRANSPARENT);					
				}
				return res;
			}	    	
	    };
	    lv.setAdapter(adapter);
	    
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int item, long arg3) {
				view.setSelected(true);				
				((MainActivity) getActivity()).goToTrack(item);
			}
	    	
	    });
	    
	    ((Button) res.findViewById(R.id.action_load)).setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {	    		
	    		Intent getContentIntent = FileUtils.createGetContentIntent();
	    		Intent intent = Intent.createChooser(getContentIntent, "Select a file");
	    		startActivityForResult(intent, REQUEST_CHOOSER);
	    	}	    	
	    });
	    
	    ((Button) res.findViewById(R.id.action_load_dir)).setOnClickListener(new OnClickListener() 
        {
            private String m_chosenDir = "";
            private boolean m_newFolderEnabled = false;

            @Override
            public void onClick(View v) 
            {
                // Create DirectoryChooserDialog and register a callback 
                DirectoryChooserDialog directoryChooserDialog = 
                new DirectoryChooserDialog(getActivity(), 
                    new DirectoryChooserDialog.ChosenDirectoryListener() 
                {
                    @Override
                    public void onChosenDir(String chosenDir) 
                    {
                        m_chosenDir = chosenDir;
                        Utils.getModel(CurrentPlaylistFragment.this).saveTracksFromDir(chosenDir);
                        ((MainActivity) getActivity()).goToCurrentPlaylist();
                    }
                }); 
                // Toggle new folder button enabling
                directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
                // Load directory chooser dialog for initial 'm_chosenDir' directory.
                // The registered callback will be called upon final directory selection.
                directoryChooserDialog.chooseDirectory(m_chosenDir);
                m_newFolderEnabled = ! m_newFolderEnabled;
            }
        });
		
	    return res;
	}
	
	@Override	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 switch (requestCode) {
	        case REQUEST_CHOOSER:   
	            if (resultCode == getActivity().RESULT_OK) {

	                final Uri uri = data.getData();

	                // Get the File path from the Uri
	                String path = FileUtils.getPath(getActivity(), uri);	
	                
	                try {
	                	Utils.getModel(this).saveTrack(path);
	                } catch (Exception e) {
	                	e.printStackTrace();	                	
	                }	                
	            }
	            break;
	    }
		((MainActivity) getActivity()).goToCurrentPlaylist();		 
	}
	
	@Override
	public void onPause() {		
		super.onPause();
		App app = (App) getActivity().getApplication();
		app.setWatchingCurrentPlaylist(false);		
	}
	
	@Override
	public void onResume() {		
		super.onResume();
		App app = (App) getActivity().getApplication();
		app.setWatchingCurrentPlaylist(true);		
	}

}
