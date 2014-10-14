package com.csound.wizard.fragment;

import java.util.List;

import com.csound.wizard.MainActivity;
import com.csound.wizard.Utils;
import com.csound.wizard.model.Model;
import com.example.proglayout.R;

import android.app.Fragment;
import android.graphics.Color;
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

public class BrowseFragment extends Fragment {
	
	private ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	    // Inflate the layout for this fragment
	    View res = inflater.inflate(R.layout.fragment_browse, container, false);
	    ListView lv = (ListView) res.findViewById(R.id.browse_list);
	    		
	    List<String> ls = Utils.getModel(this).getPlaylistNames();	    
		adapter = new ArrayAdapter<String>(getActivity(),
		      android.R.layout.simple_list_item_1, ls) {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				Model model = Utils.getModel(BrowseFragment.this);				
				View res = super.getView(position, convertView, parent);
				if (position == model.getCurrentPlaylistId()) {
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
				((MainActivity) getActivity()).goToPlaylist(item);
			}
	    	
	    });
	    
	    Button addPlaylistBtn = (Button) res.findViewById(R.id.action_new_playlist);
	    addPlaylistBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Utils.editInputDialog(getActivity(), new Utils.EditInputDialog() {
					public void apply(String text) {
						Model m = Utils.getModel(BrowseFragment.this);
						m.addPlaylist(text);
						m.setCurrentPlaylist(text);
						((MainActivity) getActivity()).goToCurrentPlaylist();
					}
				});
			}
		});

	    return res;
	}
}
