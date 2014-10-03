package com.example.proglayout.layout;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.example.proglayout.layout.SetLayoutParam.LayoutParent;
import com.example.proglayout.layout.unit.Button;
import com.example.proglayout.layout.unit.Chess;
import com.example.proglayout.layout.unit.Empty;
import com.example.proglayout.layout.unit.Hor;
import com.example.proglayout.layout.unit.HorScroll;
import com.example.proglayout.layout.unit.Knob;
import com.example.proglayout.layout.unit.Multitouch;
import com.example.proglayout.layout.unit.MultitouchChess;
import com.example.proglayout.layout.unit.MultitouchX;
import com.example.proglayout.layout.unit.MultitouchY;
import com.example.proglayout.layout.unit.Options;
import com.example.proglayout.layout.unit.OutMeter;
import com.example.proglayout.layout.unit.OutMeterCenter;
import com.example.proglayout.layout.unit.OutMeterDial;
import com.example.proglayout.layout.unit.OutMeterDialCenter;
import com.example.proglayout.layout.unit.OutRainbowCircle;
import com.example.proglayout.layout.unit.Plane;
import com.example.proglayout.layout.unit.PlaneX;
import com.example.proglayout.layout.unit.PlaneY;
import com.example.proglayout.layout.unit.HorRadio;
import com.example.proglayout.layout.unit.ShowNames;
import com.example.proglayout.layout.unit.ShowInts;
import com.example.proglayout.layout.unit.ShowFloats;
import com.example.proglayout.layout.unit.Slider;
import com.example.proglayout.layout.unit.SpinnerUnit;
import com.example.proglayout.layout.unit.Table;
import com.example.proglayout.layout.unit.Tabs;
import com.example.proglayout.layout.unit.Tap;
import com.example.proglayout.layout.unit.TapClick;
import com.example.proglayout.layout.unit.TapToggle;
import com.example.proglayout.layout.unit.Toggle;
import com.example.proglayout.layout.unit.Ver;
import com.example.proglayout.layout.unit.VerRadio;
import com.example.proglayout.layout.unit.VerScroll;
import com.example.proglayout.layout.unit.OutSlider;
import com.example.proglayout.layout.unit.OutKnob;
import com.example.proglayout.layout.unit.OutPlane;
import com.example.proglayout.model.TrackState;
import com.example.proglayout.view.param.Param;

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
	
	
	public static final String
		HOR = "hor",
		VER = "ver",
		HOR_SCROLL = "hor-scroll",
		VER_SCROLL = "ver-scroll",
		TABLE = "table",
		OPTIONS = "options",
		TABS = "tabs",
		EMPTY = "empty",
		
		COMMENT = "commnet",
		BUTTON = "button",
		TOGGLE = "toggle",
		SLIDER = "slider",
		CHESS = "chess",
		KNOB = "knob",
		PLANE = "plane",
		PLANE_X = "plane-x",
		PLANE_Y = "plane-y",
		MULITOUCH = "multitouch-plane",
		MULITOUCH_X = "multitouch-plane-x",
		MULITOUCH_Y = "multitouch-plane-y",
		MULITOUCH_CHESS = "multitouch-chess",
		HOR_RADIO = "hor-radio",
		VER_RADIO = "ver-radio",
		TAP = "tap",
		TAP_CLICK = "tap-click",
		TAP_TOGGLE = "tap-toggle",
		SPINNER = "spinner",
		
		// outputs
		
		OUT_SLIDER = "out-slider",
		OUT_KNOB = "out-knob",
		OUT_PLANE = "out-plane",
		RAINBOW_CIRCLE = "rainbow-circle",
		METER = "meter",
		CENTER_METER = "center-meter",
		CIRCLE_METER = "circle-meter",
		CENTER_CIRCLE_METER = "center-circle-meter",
		SHOW_NAMES = "show-names",
		SHOW_INTS = "show-ints",
		SHOW_FLOATS = "show-floats",
		
		INIT = "init";
	
	public static final String[] arrayUnits = {
		HOR, VER, HOR_SCROLL, VER_SCROLL, TABLE
	};
	
	public static final String[] objectArrayUnits = {
		OPTIONS, TABS
	};
	
	public static final String[] singletonArrayUnits = {
		HOR, VER, HOR_SCROLL, VER_SCROLL
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
				
		units.add(new Table());
		units.add(new Tabs());
		units.add(new Empty());
		units.add(new Options());
		units.add(new Hor());
		units.add(new Ver());		
		units.add(new HorScroll());		
		units.add(new VerScroll());
		
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