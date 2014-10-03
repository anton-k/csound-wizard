<CsoundSynthesizer>

<wizard>
{ "ver": [		
		{ "slider": "", "id": "amp" },
		{ "slider": "", "id": "cps" }		
	]
}
</wizard>

<CsOptions>

-o dac -d

</CsOptions>

<CsInstruments>

sr = 44100
ksmps = 64
nchnls = 1
0dbfs = 1.0
giPort init 1
opcode FreePort, i, 0
xout giPort
giPort = giPort + 1
endop



instr 21

endin

instr 20
 event_i "i", 19, 604800.0, 1.0e-2
endin

instr 19
 turnoff2 18, 0.0, 0.0
 exitnow 
endin

instr 18
kr0 chnget "amp"
ar0 upsamp kr0
kr0 chnget "cps"
ar1 upsamp kr0
arl0 init 0.0
ar2 = (440.0 * ar1)
ar1 oscil3 1.0, ar2, 1
ar2 = (ar0 * ar1)
ar0 clip ar2, 0.0, 0dbfs
ar1 = (ar0 * 0.5)
arl0 = ar1
ar0 = arl0
 out ar0
endin

</CsInstruments>

<CsScore>

f1 0 8192 10  1.0

f0 604800.0

i 21 0.0 -1.0 
i 20 0.0 -1.0 
i 18 0.0 -1.0 

</CsScore>

</CsoundSynthesizer>