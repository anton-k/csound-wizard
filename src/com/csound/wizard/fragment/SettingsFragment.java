package com.csound.wizard.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.csound.wizard.Const;
import com.csound.wizard.Settings;
import com.csound.wizard.Utils;
import com.csound.wizard.layout.Layout;
import com.example.proglayout.R;

public class SettingsFragment extends Fragment {
	
	private Settings settings;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		settings = Utils.getSettings(getActivity());
		final View res = inflater.inflate(R.layout.fragment_settings, container, false);
		
		// Main menu	
		
		int[] allIds = { R.id.set_text, R.id.set_color, R.id.set_layout };
		int[] allIdUnits = { R.id.set_text_container, R.id.set_color_container, R.id.set_layout_container };
		setPrimaryToggleMenu(res, allIds, allIdUnits);
					
		// Text menu
			
		int[] allTextIds = { R.id.set_text_size, R.id.set_text_scale, R.id.set_text_color, R.id.set_text_align };
		int[] allTextIdUnits = { R.id.set_text_size_unit, R.id.set_text_scale_unit, R.id.set_text_color_unit, R.id.set_text_align_unit };
		setSecondaryToggleMenu(res, allTextIds, allTextIdUnits);
		
		setupTextSize(res);
		setupTextScale(res);
		setupTextColor(res);
		setupTextAlign(res);
					
		// Color menu
		
		int[] allColorIds = { R.id.set_fst_color, R.id.set_snd_color, R.id.set_bkg_color };
		int[] allColorIdUnits = { R.id.set_fst_color_unit, R.id.set_snd_color_unit, R.id.set_bkg_color_unit };
		setSecondaryToggleMenu(res, allColorIds, allColorIdUnits);
		
		setupFstColor(res);
		setupSndColor(res);
		setupBkgColor(res);
				
		// Layout menu
		
		int[] allLayoutIds = { R.id.set_layout_margin };
		int[] allLayoutIdUnits = { R.id.set_layout_margin_unit };
		setSecondaryToggleMenu(res, allLayoutIds, allLayoutIdUnits);	
				
		setupMarginLeft(res);
		setupMarginRight(res);
		setupMarginTop(res);
		setupMarginBottom(res);
		
		TextView tv = (TextView) res.findViewById(R.id.set_text_scale_show_value);
		Layout.setTextProperties(tv, settings.getParam().getText());
		
		return res;	
	}		

	
	private void setPrimaryToggleMenu(final View res, final int[] allIds, final int[] allIdUnits) {

		int highlightColor = Color.parseColor("#7FDBFF");
		int bkgColor = Color.parseColor("#DDDDDD");

		setToggleMenu(res, allIds, allIdUnits, highlightColor, bkgColor);		
	}
	
	private void setSecondaryToggleMenu(final View res, final int[] allIds, final int[] allIdUnits) {

		int highlightColor = Color.parseColor("#3D9970");
		int bkgColor = Color.TRANSPARENT;

		setToggleMenu(res, allIds, allIdUnits, highlightColor, bkgColor);		
	}
	
	private void setToggleMenu(final View res, final int[] allIds, final int[] allIdUnits, final int color, final int bkgColor) {
		for (int i = 0; i < allIds.length; i++) {
			setToggle(res, allIds[i], allIdUnits[i], allIds, allIdUnits, color, bkgColor);						
		}	
		
		for (int i: allIds) {
			res.findViewById(i).setBackgroundColor(bkgColor);
		}
	}
	
	
	private void setToggle(final View res, final int id, final int idUnit, final int[] allIds, final int[] allIdUnits, final int color, final int bkgColor) {
		View tv = res.findViewById(id);		
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				View v = res.findViewById(idUnit);
				if (v.getVisibility() != View.VISIBLE) {
					for (int i: allIds) {
						res.findViewById(i).setBackgroundColor(bkgColor);						
					}
					for (int i: allIdUnits) {
						res.findViewById(i).setVisibility(View.GONE);						
					}
					v.setVisibility(View.VISIBLE);
					res.findViewById(id).setBackgroundColor(color);
				} else {
					v.setVisibility(View.GONE);
					res.findViewById(id).setBackgroundColor(bkgColor);
				}								
			}
		});
	}
	
	// Setup fields
	
	// -----------------------------------------------------
	// Text
	
	private void setupTextSize(View root) {
		setupNumberPicker((NumberPicker) root.findViewById(R.id.set_text_size_unit), 
			12, 40, settings.getTextSize(), new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				settings.setTextSize(newVal);
			}
		});		
	}
	
	private void setupTextScale(final View root) {
		final TextView tv = (TextView) root.findViewById(R.id.set_text_scale_show_value);
		tv.setText(Float.toString(settings.getTextScale()));
		
		setupSeekBar((SeekBar) root.findViewById(R.id.set_text_scale_unit_value), Const.minTextScale, Const.maxTextScale, settings.getTextScale(), new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				float val = Const.minTextScale + (Const.maxTextScale - Const.minTextScale) * (float) progress / 100f;
				settings.setTextScale(val);				
				tv.setText(Float.toString(val));
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});	
	}
	
	private void setupTextColor(View root) {
		setupColor((Spinner) root.findViewById(R.id.set_text_color_unit), getColorPosition(settings.getTextColor()), new OnSetColor() {
			
			@Override
			public void apply(int n) {
				settings.setTextColor(n);
			}
		});
	}	
	
	private void setupTextAlign(View root) {				
		List<String> alignNameList = new ArrayList<String>(Arrays.asList(alignNames));				
				
 		setupSpinner((Spinner) root.findViewById(R.id.set_text_align_unit),
 				alignNameList, getAlignPosition(settings.getTextAlign()), new OnItemSelectedListener() {
 			
 				@Override
 				public void onItemSelected(AdapterView<?> arg0, View v,
 						int position, long arg3) {
 					settings.setTextAlign(positionToAlign(position)); 					 					
 				}
 				
 				@Override
 				public void onNothingSelected(AdapterView<?> arg0) {
 				}
			});		
	}

	// -----------------------------------------------------
	// Color
	
	private void setupFstColor(View root) {
		setupColor((Spinner) root.findViewById(R.id.set_fst_color_unit), getColorPosition(settings.getFstColor()), new OnSetColor() {
			
			@Override
			public void apply(int n) {
				settings.setFstColor(n);
			}
		});
	}	

	private void setupSndColor(View root) {
		setupColor((Spinner) root.findViewById(R.id.set_snd_color_unit), getColorPosition(settings.getSndColor()), new OnSetColor() {
			
			@Override
			public void apply(int n) {
				settings.setSndColor(n);
			}
		});
	}	
	
	private void setupBkgColor(View root) {
		setupColor((Spinner) root.findViewById(R.id.set_bkg_color_unit), getColorPosition(settings.getBkgColor()), new OnSetColor() {
			
			@Override
			public void apply(int n) {
				settings.setBkgColor(n);
			}
		});
	}	

	// -----------------------------------------------------
	// Layout
	
	private void setupMargin(NumberPicker np, int val, OnValueChangeListener listener) {
		setupNumberPicker(np, 0, 50, val, listener);		
	}
	
	private void setupMarginLeft(View root) {
		setupMargin((NumberPicker) root.findViewById(R.id.set_margin_left_unit), settings.getMarginLeft(), new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				settings.setMarginLeft(newVal);
			}
		});		
	}

	private void setupMarginRight(View root) {
		setupMargin((NumberPicker) root.findViewById(R.id.set_margin_right_unit), settings.getMarginRight(), new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				settings.setMarginRight(newVal);
			}
		});				
	}

	private void setupMarginTop(View root) {
		setupMargin((NumberPicker) root.findViewById(R.id.set_margin_top_unit), settings.getMarginTop(), new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				settings.setMarginTop(newVal);
			}
		});				
	}

	private void setupMarginBottom(View root) {
		setupMargin((NumberPicker) root.findViewById(R.id.set_margin_bottom_unit), settings.getMarginBottom(), new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				settings.setMarginBottom(newVal);
			}
		});				
	}
	
	// -----------------------------------------------------
	// Utils
	
	private void setupColor(Spinner sp, int pos, OnSetColor setColor) {		
		setupColorSpinner(sp, pos, setColor);
	}
	
	private interface OnSetColor {
		public void apply(int n);
	}
	
	private int faintColor(int col) {
		return Color.argb(100, Color.red(col), Color.green(col), Color.blue(col));
	}
	
	private void setupColorSpinner(Spinner x, int pos, final OnSetColor setColor) {	
		final List<String> names = Const.colorNameList;
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {			
				return applyColor(super.getView(position, convertView, parent));				
			}
			
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				return applyColor(applyTextParam(super.getDropDownView(position, convertView, parent)));
			}
			
			private View applyTextParam(View x) {
				TextView res = (TextView) x;
				Layout.setTextProperties(res, settings.getParam().getText());
				return res;				
			}
			
			private View applyColor(View v) {
				TextView res = (TextView) v;
				int col = Const.getColor(res.getText().toString());
				res.setBackgroundColor(faintColor(col));				
				return res;
			}
			
		};
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		x.setAdapter(adapter);
		x.setSelection(pos);
		x.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				setColor.apply(Const.getColor(names.get(position)));								
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {			
			}
		});
	}
	
	private void setupSpinner(Spinner x, List<String> names, int pos, OnItemSelectedListener listener) {		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names) {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return applyTextParam(super.getView(position, convertView, parent));				
			}
			
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				return applyTextParam(super.getDropDownView(position, convertView, parent));
			}
			
			private View applyTextParam(View x) {
				TextView res = (TextView) x;
				Layout.setTextProperties(res, settings.getParam().getText());
				return res;				
			}			
			
		};
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		x.setAdapter(adapter);
		x.setSelection(pos);
		x.setOnItemSelectedListener(listener);
	}
	
	private int getColorPosition(int color) {		
		return Const.getColorPosition(color);
	}
	
	
	private static final String[] alignNames = { "left", "center", "right" }; 
 	
	private int getAlignPosition(Align n) {
		if (n.equals(Align.LEFT)) {
			return 0;
		}
		
		if (n.equals(Align.CENTER)) {
			return 1;
		}
		
		if (n.equals(Align.RIGHT)) {
			return 2;
		}
				
		return 0;
	}
	
	private Align positionToAlign(int n) {
		switch (n) {
			case 0:
				return Align.LEFT;
			case 1:
				return Align.CENTER;
			case 2: 
				return Align.RIGHT;
			default:
				return Align.CENTER;
		}
	}
	
	private void setupSeekBar(SeekBar x, float minVal, float maxVal, float val, OnSeekBarChangeListener listener) {
		x.setProgress(Math.round(100 * (val - minVal) / (maxVal - minVal)));
		x.setOnSeekBarChangeListener(listener);
	}
	
	private void setupNumberPicker(NumberPicker np, int minVal, int maxVal, int val, OnValueChangeListener listener) {
		np.setMinValue(minVal);
		np.setMaxValue(maxVal);
		np.setValue(val);
		np.setWrapSelectorWheel(false);
		np.setOnValueChangedListener(listener);
	}	
}
