<CsoundSynthesizer>

<wizard>
{ "hor": [
	  { "center-meter": "amp" }
	, { "ver": [
		  { "meter": "amp" }
		, { "center-circle-meter": "amp" }
		, { "out-plane": "amp" }	
		, { "show-names": "labels", "names": ["left", "center", "right"] }			
		, { "slider": "ampCoeff", "init": 1 }
		, { "slider": "speed", "init": 0.1, "range": [0.1, 1.5] }
		]}	
], 
"set-default": {
    "margin": 10    
 }}
</wizard>


<CsOptions>

-o dac -d 
</CsOptions>

<CsInstruments>

sr = 44100
ksmps = 64
nchnls = 1
0dbfs = 1.0


instr 18
kampCoeff chnget "ampCoeff"
kspeed chnget "speed"
kcps = kspeed
ar0 oscil3 kampCoeff, kcps, 1
ar0y oscil3 kampCoeff, kcps, 1, 0.25
ar1 = 0.5 * (ar0 + 1)
ar1y = 0.5 * (ar0y + 1)
kr1 = ar1
kr1y = ar1y
krLabels = 3 * (ar0 + 1) / 2
chnset krLabels, "labels"
chnset kr1, "amp"
chnset kr1, "amp.x"
chnset kr1y, "amp.y"
out (0 * ar0)
endin

</CsInstruments>

<CsScore>

f1 0 8192 10  1.0

f0 604800.0

i 18 0.0 -1.0 

</CsScore>

</CsoundSynthesizer>