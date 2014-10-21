<CsoundSynthesizer>

<wizard>
{ "mtouch": 11 }
</wizard>

<CsOptions>
-o dac -d -b512 -B2048
</CsOptions>

<CsInstruments>

sr = 44100
kr = 4410
nchnls = 2
0dbfs = 1

opcode Touch, kk, 0
S_xName sprintf "touch.%d.%d.x", int(p1), p4
S_yName sprintf "touch.%d.%d.y", int(p1), p4
kx chnget S_xName
ky chnget S_yName
xout kx, ky
endop 

gaout init 0

instr 11
i_FingerIndex = p4

kx, ky Touch

kamp = kx * 0.25
kcps = 220 * pow(8, 1 - ky)

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