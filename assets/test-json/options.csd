<wizard>
{ "options":	
		[ { "buttons": { "button" : "btn1" }}	
		, { "toggles": { "toggle" : "toggle1", "init": true }}
		, { "planes":  { "plane"  : "p", "init": [0.75, 0.75]}}
		, { "planes-x":  { "plane-x"  : "px", "range-x": 4, "init": [1, 0.75]}}		
		, { "planes-y":  { "plane-y"  : "py", "range-y": 7, "init": [1, 0.75]}}
		, { "chess"   :  { "chess"    : "ch", "range-x": 5, "range-y": 5, "init": [4, 1]}}
		, { "sliders": { "slider" : "sld1", "init": 0.75 }}
		, { "knobs"  : { "knob" : "k1", "init": 0.75 }}
		, { "tap"    : { "tap" : 1, "range-x": 1, "range-y": 2}}
		, { "uis"    : 	{ "ver": [
				{ "toggles": "tgl1", "names": ["one", "two", "three", "zero"] }
				{ "hor": [ { "toggle": "btn1",
			 	  			 "margin": 10, "height": "150" },
			 			   { "slider": "slider1", "orient": "hor" }]
			 	, "set-default": { "fst-color": "olive" }
			 	},
			 	{ "ver-radio": "spin1", "names": ["one", "two", "three", "fuzz"], "init": 2 }
			 	{ "spinner": "spin2", "names": ["one", "two", "three", "fuzz"], "init": 2 }
			]}}
		]	
}
</wizard>