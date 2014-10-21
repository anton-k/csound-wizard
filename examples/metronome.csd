<CsoundSynthesizer>

<wizard>
{ "ver": [ 
  { "hor": [ "bpm", {"show-ints": "showBpm", "height": 100 } ]}
, { "slider": "bpm", "range": [20, 250], "init": 100 }
, { "hor": [ "beats", {"show-ints": "showBeatNum", "height": 110 }, "/" , {"show-ints": "showBeats", "height": 110, "text-color": "orange" } ]}
, { "slider": "beatNum", "range": [2, 12], "init": 4  }
, { "hor": [ "needs accent",{ "toggles": "isAccent", "names": [ "no", "yes" ], "init": 1, "width": "fill" } ]}
, { "line": null }
, { "hor": [ "volume", { "slider": "vol", "width": "fill" } ]}
],
"set-default": {
	"margin": 7
}}
</wizard>

<CsOptions>
-o dac -+rtmidi=null -d -i adc
</CsOptions>
<CsInstruments>

nchnls=2
0dbfs=1
ksmps=64
sr = 44100

instr 2
kbeat init 0

kBeatNum chnget "beatNum"
kBpm  chnget "bpm"
kVol  chnget "vol"

kIsAccent chnget "isAccent"
kBeatNum = int(kBeatNum)
kBpm = int(kBpm)
kcps = kBpm / 60

ktrig metro kcps

if (ktrig == 1) then
	chnset (kbeat + 1), "showBeats"
	
	if (kbeat == 0 && kIsAccent == 1) then 
		kAccent = 1		  
	else 
		kAccent = 0
	endif
		
	event "i", 1, 0, 0.2, kAccent, kVol 
	
	kbeat = (kbeat + 1) % kBeatNum
endif


chnset kBeatNum, "showBeatNum"
chnset kBpm, "showBpm"
endin


instr 1
iBase = 220
icps = iBase * (1 + p4) 

asig oscil p5, icps, 1 
kenv linseg 0, 0.07, 1, p3 - 0.07, 0
asig = asig * kenv
outs asig, asig
endin



</CsInstruments>
<CsScore>
f0 100000

f1 0 16384 10 1

i2 0 -1

</CsScore>
</CsoundSynthesizer>
