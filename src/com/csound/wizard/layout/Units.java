package com.csound.wizard.layout;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.csound.wizard.Const;
import com.csound.wizard.layout.SetLayoutParam.LayoutParent;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.layout.unit.Button;
import com.csound.wizard.layout.unit.Chess;
import com.csound.wizard.layout.unit.Empty;
import com.csound.wizard.layout.unit.Hor;
import com.csound.wizard.layout.unit.HorRadio;
import com.csound.wizard.layout.unit.HorScroll;
import com.csound.wizard.layout.unit.Ints;
import com.csound.wizard.layout.unit.Knob;
import com.csound.wizard.layout.unit.Line;
import com.csound.wizard.layout.unit.Multitouch;
import com.csound.wizard.layout.unit.MultitouchChess;
import com.csound.wizard.layout.unit.MultitouchX;
import com.csound.wizard.layout.unit.MultitouchY;
import com.csound.wizard.layout.unit.Names;
import com.csound.wizard.layout.unit.Options;
import com.csound.wizard.layout.unit.OutKnob;
import com.csound.wizard.layout.unit.OutMeter;
import com.csound.wizard.layout.unit.OutMeterCenter;
import com.csound.wizard.layout.unit.OutMeterDial;
import com.csound.wizard.layout.unit.OutMeterDialCenter;
import com.csound.wizard.layout.unit.OutPlane;
import com.csound.wizard.layout.unit.OutRainbowCircle;
import com.csound.wizard.layout.unit.OutSlider;
import com.csound.wizard.layout.unit.Plane;
import com.csound.wizard.layout.unit.PlaneX;
import com.csound.wizard.layout.unit.PlaneY;
import com.csound.wizard.layout.unit.ShowFloats;
import com.csound.wizard.layout.unit.ShowInts;
import com.csound.wizard.layout.unit.ShowNames;
import com.csound.wizard.layout.unit.Slider;
import com.csound.wizard.layout.unit.SpinnerUnit;
import com.csound.wizard.layout.unit.Table;
import com.csound.wizard.layout.unit.Tabs;
import com.csound.wizard.layout.unit.Tap;
import com.csound.wizard.layout.unit.TapClick;
import com.csound.wizard.layout.unit.TapToggle;
import com.csound.wizard.layout.unit.Toggle;
import com.csound.wizard.layout.unit.Ver;
import com.csound.wizard.layout.unit.VerRadio;
import com.csound.wizard.layout.unit.VerScroll;
import com.csound.wizard.model.TrackState;

public class Units {
	
	public interface Unit {
		public String getTag();		
		public View getView(LayoutContext ctx, Object tagValue, Param currentParams, Param defaultParams, 
					TrackState trackState, LayoutParent layoutParent);		
	}
	
	public interface StatefulUnit {
		public String getUnitId();
		public double[] getUnitState();
	}
	
	public static final String[] arrayUnits = {
		Const.HOR, Const.VER, Const.HOR_SCROLL, Const.VER_SCROLL, Const.TABLE
	};
	
	public static final String[] objectArrayUnits = {
		Const.OPTIONS, Const.TABS
	};
	
	public static final String[] singletonArrayUnits = {
		Const.HOR, Const.VER, Const.HOR_SCROLL, Const.VER_SCROLL
	};	
		
	public static List<Unit> units;
	
	static {
		units = new ArrayList<Unit>();		
		units.add(new Button());
		units.add(new Chess());		
		units.add(new Knob());
		
		units.add(new Plane());
		units.add(new PlaneX());
		units.add(new PlaneY());
		units.add(new HorRadio());
		units.add(new VerRadio());
		units.add(new Slider());
		units.add(new Multitouch());
		units.add(new MultitouchX());
		units.add(new MultitouchY());
		units.add(new MultitouchChess());
		units.add(new Tap());		
		units.add(new TapClick());
		units.add(new TapToggle());
		units.add(new Toggle());
		units.add(new SpinnerUnit());
		units.add(new Ints());
		units.add(new Names());
		
		// groups
		
		units.add(new Table());
		units.add(new Tabs());
		units.add(new Empty());
		units.add(new Options());
		units.add(new Hor());
		units.add(new Ver());		
		units.add(new HorScroll());		
		units.add(new VerScroll());
		units.add(new Line());
		
		// Outputs
		
		units.add(new OutRainbowCircle());
		units.add(new OutMeter());
		units.add(new OutMeterCenter());
		units.add(new OutMeterDial());
		units.add(new OutMeterDialCenter());
		units.add(new ShowNames());
		units.add(new ShowInts());
		units.add(new ShowFloats());
		units.add(new OutSlider());
		units.add(new OutKnob());
		units.add(new OutPlane());					
	}		
}