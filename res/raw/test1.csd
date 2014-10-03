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

instr 18
ar0 chnget "amp"
arl0 init 0.0
ar1 oscil3 1.0, 440.0, 1
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

i 18 0.0 -1.0 

</CsScore>

</CsoundSynthesizer>