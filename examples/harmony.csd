<CsoundSynthesizer>

<wizard>
{ "ver": [
	, { "toggles": "isJust", "names": ["just", "equal"] }
	, { "hor": ["mode:" 
			, { "spinner": "scaleType",  "names": [ "major", "dorian", "phrygian", "lydian", "mixolydian", "minor", "locrian" ],
				"width": "fill", "height": 120 }]}	
	, { "hor": ["base note:"
			, { "spinner": "baseNote",  "names": [ "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "H" ],
				"width": "fill", "height": 120 }]}
	, "notes"
	, { "tap-toggle": 17, "range-x": 7, "range-y": 3 }
]}
</wizard>

<CsOptions>
-o dac -d -b512 -B2048
</CsOptions>

<CsInstruments>

ksmps=64
sr = 22050
nchnls = 2
0dbfs = 1

opcode Touch, kk, 0
S_xName sprintf "touch.%d.%d.x", int(p1), p4
S_yName sprintf "touch.%d.%d.y", int(p1), p4
kx chnget S_xName
ky chnget S_yName
xout kx, ky
endop 

giC 			init (261.63 / 2)
gkIsJust    	init 0
gkScaleType 	init 0
gkBaseNote  	init 0

gaout init 0
gkamp init 0.8


instr 1
gkIsJust 	 chnget "isJust"
gkScaleType  chnget "scaleType"
gkBaseNote   chnget "baseNote"
endin 


instr 17
ix = p4
iy = p5

kBaseNoteCoeff tablei gkBaseNote, 11
kBaseNoteFreq = giC * kBaseNoteCoeff

kIndex tablekt ix, (gkScaleType + 20) 
kCpsX  tablekt kIndex, (gkIsJust + 10)

kCpsY pow 2, iy
kcps = kBaseNoteFreq * kCpsY * kCpsX 

kCount active 17
gkamp = 0.8 / (kCount + 1)

kamp = 0.5 * gkamp

ares oscil kamp, kcps, 1
aenv linsegr 0, 0.5, 1, 0.2, 1, 0.5, 0
gaout = gaout + ares * aenv
endin

instr 100
a1 = gaout
aL, aR reverbsc a1, a1, .72, 5000
out aL, aR
gaout = 0
endin 

</CsInstruments>

<CsScore>
f1 0 16384 10 1 0.5 0.1 0 0.05

f10 0 16 -2 1.0000 1.0417  1.1250  1.2000  1.2500  1.3333  1.4063  1.5000  1.6000  1.6667  1.8000  1.8750  2.0000
f11 0 16 -2 1.0000 1.05946 1.12246 1.18921 1.25992 1.33483 1.41421 1.49831 1.58740 1.68179 1.78180 1.88775 2.0000

; major
f20 0 8  -2  0  2  4  5  7  9 11 12
; dorian
f21 0 8  -2  0  2  3  5  7  9 10 12
; phrygian
f22 0 8  -2  0  1  3  5  7  8 10 12
; lydian
f23 0 8  -2  0  2  4  6  7  9 11 12
; mixolydian
f24 0 8  -2  0  2  4  5  7  9 10 12
; minor
f25 0 8  -2  0  2  3  5  7  8 10 12
; locrian
f26 0 8  -2  0  1  3  5  6  8 10 12

i 1   0 100000
i 100 0 100000
</CsScore>

</CsoundSynthesizer>