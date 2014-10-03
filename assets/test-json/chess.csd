<CsoundSynthesizer>

<wizard>
[{ "tap-click": 15, "range-x": 2, "range-y": 2, "names": ["Am", "Dm", "C", "G"]
, "text-color": "purple"},
{ "tap-toggle": 17, "range-x": 4, "range-y": 3 }
]
</wizard>

<CsOptions>
-o dac -d
</CsOptions>

<CsInstruments>

sr = 44100
kr = 4410
nchnls = 2
0dbfs = 1

gaout init 0
gkamp init 0.8

instr 15
event_i "i", 17.0123, 0, 2, p4, p5
endin

instr 17
ix = p4
iy = p5

kCount active 17
gkamp = 0.8 / (kCount + 1)

kamp = gkamp

iBase = 150

if (p4 == 0) then
	iX = 1
elseif (p4 == 1) then
	iX = 6.0/5
elseif (p4 == 2) then
	iX = 3.0/2
elseif (p4 == 3) then
	iX = (3.0/2) * 6.0/5
endif

if (p5 == 0) then
	iY = 1
elseif (p5 == 1) then
    iY = 9.0/8
elseif (p5 == 2) then
	iY = 6.0/5
endif

kcps = iBase * iX * iY

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
f1 0 16384 10 1 0.5 0.1

i 100 0 100000
</CsScore>

</CsoundSynthesizer>