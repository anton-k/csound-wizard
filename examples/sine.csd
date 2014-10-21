<CsoundSynthesizer>

<wizard>
{ "ver": [		
		{ "slider": "amp" },
		{ "slider": "cps" }		
]}
</wizard>

<CsOptions>
-o dac -d
</CsOptions>

<CsInstruments>

sr = 44100
ksmps = 64
nchnls = 1
0dbfs = 1

instr 1
kamp chnget "amp"
kcps chnget "cps"

ares oscil3 kamp, 220 * pow(2, kcps), 1
out ares
endin

</CsInstruments>

<CsScore>
f0 604800.0
f1 0 8192 10  1.0

i1 0.0 -1.0 

</CsScore>
</CsoundSynthesizer>