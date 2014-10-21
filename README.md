
The Csound wizard
=============================


The Csound Wizard is an android player for csound files. 
The UI is specified with JSON and sound engine is defined in the csound file.
We can organize csd-files in groups (playlists) save the state
of the UI. 

To include the UI-defninition in CSD-file we have to define
an xml element with the tag "wizard":

![](pic/wiz.jpg)

~~~
<CsoundSynthesizer>

<wizard>
	JSON-object
</wizard>

<CsOptions>
...
</CsOptions>

<CsoundSynthesizer>
~~~

The UI is defined in [JSON-format](http://json.org/).
We can define sliders, buttons, toggle-buttons, radio-buttons,
2D-sliders, knobs, tap-pads. We can group these elements 
in different kinds of layouts.


JSON refresher
--------------------------------

The JSON is very simple format for data specification. 
If you don't know anything about it don't worry. It's super easy. 

The JSON is

* a value null: `null`

* number: `1`, `2.5`, `38`

* string: `"Hello"`, `"My name is alice"`, `"pid-1234"`

* boolean: `true` or `false`

* array of JSON's: `[1, true, 3, "Hello", "World!"]`

* object or list of key-value pairs: `{ "first": 1, "second": 2, "default": "Let it be."}`

So we've got three types of primitives: numbers, strings and booleans
and there are two type of composites: arrays (`[object, object, object]`)
and objects (`{ "key1": value1, "key2": value2, etc}`). 

That's it. You know the JSON. 


An example
------------------------------

A little teaser of what can be done.

~~~
<CsoundSynthesizer>

<wizard>
[ { "slider": "amp" }
, { "slider": "cps" } ]
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
~~~

We can see how it's easy to define the UI.

UI primitives
-------------------------------

To define an element we write a JSON-object
with the type of the element as one of the values:

~~~
{ "type_of_the_object": "channel_name"
, "propertyA": "valueA"
, "propertyB": "valueB" }
~~~

The `"channel_name"` binds a widget with csound channel.
We can read or write values in the csound with opcodes `chnget/chnset`.
The other fields of the JSON-object can describe the properties of 
the widget. It can be text size, primary or background colors, 
ranges for values etc.

### Static text

The simplest element is a static text. It just shows a string:

~~~
"Hello, I'm just a text"
~~~

A little bit more verbose:

~~~
{ "text": "I'm a text too" }
~~~

The latter form can be useful for setting the text properties.

### Button

The button sends the 1's to csound when it's pressed and 0's otherwise.

~~~
{ "button": "btnId" }
~~~

We read the signal in csound by channel with given name.
It's `"btnId"` in the example above.

### Toggle button

The toggle button sends 0's after every even click
and 1's after every odd click.

~~~
{ "toggle": "channelName" }
~~~

### Toggle names, spinners and radio buttons

The toggle button provides just two states. It can be zero or one.
The `toggles` is a more generic solution. With it we can toggle 
between many states:

~~~
{ "toggles": "channelName"
, "names": ["zero", "one", "two", "three"] }
~~~

It toggles between as many integer values as the field `"names"` contain.

There are three more widgets with the same functionality but with 
different appereance.

~~~
{ "spinner": "channelName", 	"names": [...] }
{ "hor-radio": "channelName", 	"names": [...] }
{ "ver-radio": "channelName", 	"names": [...] }
~~~
The `spinner` is good old drop down list. The `hor` and `ver` radios are 
groups of radio buttons with single choice.


### Sliders and knobs

A slider sends values in the given interval.
The actual value depends on the user finger motion:

The most simple form is just:

~~~
{ "slider": "slider1" }
~~~

It defines the slider which ranges over interval `[0, 1]`
with `0.5` as initial value.

With optional fields we can specify range and initial value:

~~~
{ "slider": "slider1"
, "range": [-10, 10]
, "init": 0 }
~~~

By default all ranges are in the interval `[0, 1]`.

The knob is a circular slider. It's specified just as slider
but we should write `knob` as the element's type:

~~~
{ "knob": "channelName" }
~~~

### 2D-slider

The 2D slider is a rectangular plane. It defines a pair of signals.

~~~
{ "plane": "plane1"}

~~~

We can specify ranges and initial values:

~~~
{ "plane": "plane1"
, "range-x": [0, 10]
, "range-y": [0, 10]
, "init": [5, 5] }
~~~

In the example we can read the signals in csound code 
by two ids: "plane1.x" and "plane1.y".

#### Half integer planes

We can specify the mixed integer conituous planes: `plane-x` and `plane-y`
One of the signals (x or y) is set to integer values.

It's the same as simple `plain` but we should specify `plane-x` or `plane-y`
in the elemet's type. There is a integer 2D slider. It's called `chess`.

~~~
{ "plane-x": "channelName"
, "range-x": 6
, "init": [0, 0.7] }

{ "plane-y": "channelName"
, "range-y": 3
, "init": [1, 0.7] }
~~~

The number of descrete values equals to the integer value  that goes right after the 
plane's identifier. In the given example we have X-integer 
plane with eleven defferent values [0 to 5].

There is a plane that produces integer values in the both directions:

~~~
{ "chess": "channelName"
, "range-x": 8
, "range-y": 8 }
~~~

With optional field `names` we can label the cells of the plane:

~~~
{ "plane-x": "channelName"
, "range-x": 4
, "names": ["Am", "C", "F", "G"] }
~~~

### Tap pad

With tap pad we can emulate a drum pad or keyboard. 
There is a table of buttons. Each can trigger an instrument.

~~~
{ "tap": 23, 
, "range-x": 3
, "range-y": 4
, "names": ["a", "b", "c", ...]}
~~~

It triggers the instrument with the given integer id (here it's 23). 
The note contains two arguments which describe the pressed button: 

~~~
ix = p4
iy = p5
~~~

We can specify the names of the buttons with optional value list of names.
There two variations on the `tap` theme. The `tap-toggle` have buttons wich
toggle between two states. The `tap-click` has instant click buttons instead
of pressable keys. 

### Multitouch

We can specify multitouch with one of the ways:

~~~
{ "mtouch": 3
, "touch-limit" }
~~~

The argument describes the integer id of the triggered instrument. 
The second optional argument specifies the maximum number of simultaneous touches. 
It equals to 10 if the parameter is omitted.

The instrument is triggered with the only one p-field:

~~~
instr 3
iFingerIndex = p4
...
endin
~~~

The multitouch passes two parameters to the instrument. They are normalized x and y
coordinates. Please do use UDO to read the values:

~~~
opcode Touch, kk, 0
S_xName sprintf "touch.%d.%d.x", int(p1), p4
S_yName sprintf "touch.%d.%d.y", int(p1), p4
kx chnget S_xName
ky chnget S_yName
xout kx, ky
endop 
~~~

Just copy it in your `CsInstruments` part of the csd file. Within the 
instrument it can be used just like this:

~~~
kx, ky Touch
~~~

A short example for multitouch:

~~~
<CsoundSynthesizer>

<wizard>
{ "mtouch": 11 }
</wizard>

<CsOptions>
-o dac -d
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
kx, ky Touch

kamp = (1 - ky) * 0.25
kcps = 100 + 1000 * kx

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
~~~

We trigger the instrument 11. The instrument 100 collects the reluslts 
and sends them to speakers.

There are similiar elements: `"mtouch-x"`, `"mtouch-y"`, `"mtouch-chess"`.
They are just like `plane-x"`, `"plane-y"` and `"chess"` but with support for multitouch.

### Integer input

We can set the integers:

~~~
{ "int": "channelName"
, "range": [0, 100] }
~~~

### Show names, integers or floats

We can show the csound value with widgets:

~~~
{ "show-names": "channelName"
, "names": ["left", "right", "bottom"] }

{ "show-ints": "channelName" }
{ "show-floats": "channelName" }
~~~ 

The `show-names` reads integer values and mapps them to the
elements of the given array.


### Reading values with sliders, knobs and planes

There are variants of the slide, knob and plane that read the value from the channel:

~~~
{ "out-slider": "chnnelName" }
{ "out-knob": "chnnelName" }
{ "out-plane": "chnnelName" }
~~~

### Indicators

We can look at the csound values with indicators:

~~~
{ "meter": "channelName" }
{ "circle-meter": "channelName" }
{ "rainbow-circle": "channelName" }
{ "center-meter": "channelName" }
{ "center-circle-meter": "channelName" }
~~~

They all read the value in the range `[0, 1]`. The only thing that is
different is the look of the widget.

### Empty space

Empty space can be used for layout.

~~~
{ "empty": 10 }
~~~

The value defines the size of an empty space.


### Line

The line draws a static line. It's usefule to draw the borders between
the groups of widgets.

~~~
{ "line": null }
~~~

We can set the color:

~~~
{ "line": null
, "fst-color": "black" }
~~~

UI-groups
----------------------------

We can group UIs to layouts.

### Horizontal and vertical layout

The simplest groups are horizontal

~~~
{ "hor": [x1, x2, x3, ...] }
~~~

and vertical layouts:

~~~
{ "ver": [x1, x2, x3, ...] }
~~~

### Tables

We can organize elements in tables:

~~~
{ "table": [
	[x11, x12], 
	[x21, x23],
	[x31, x32]]}
~~~

### Tabs

We can organize elements with tabs:

~~~
{ "tabs" : [	
	  { "tab1": {...}}
	, { "tab2": {...}}
	, { "tab3": {...}}
]}
~~~

### Option list

We can organize elements as option lists. It's a list of buttons. 
Each button leeds to the specific screen:

~~~
{ "options" : [	
	  { "item1": {...}}
	, { "item2": {...}}
	, { "item3": {...}}
]}
~~~

### Scrolls

If some elements are not fit to the one screen we can add scrolls:

~~~
{ "hor-scroll": {...}}

{ "ver-scroll": {...}}
~~~

UI-parameters
----------------------------

What about sizes and colors for UI-elements? 
We can specify them in the object of the UI-element.
We set them with an optional object field:

~~~
{ "button": "btn1"
, "propertyName" : value }
~~~

Let's look at the specific parameters.

### Layout

We can set relative and absolute sizes (width, height, weight and gravity),
padding and margins.

We can ommit any parameter to use the defaults.

~~~
{ "button": "btn1"

, "width"   : "wrap"
, "height"  : 50
, "weight"  : 1
, "gravity" : "right"
, "padding" : {
	  "left"  : 5
	, "right" : 5 }
, "margin"  : {
	  "left"   : 10
	, "right"  : 10
	, "top"    : 15
	, "bottom" : 15	}			
}
~~~

#### Sizes

Width and height specify the amout of space given to the unit.
We can use float or integer values. Also we can use two special
constants: `"wrap"` and `"fill"`. With `"wrap"` elements takes 
only as much space as it needs by default. With `"fill"` elemnts
takes all space wich is available inside the parent container 
(`hor`, `ver`, `table`).

The `weight` sepcifies the size relative to the neighbours in 
the layout container. If we have three elements in the horizontal 
container and want one of them to be twice bigger then any other
we should give the gravity 2 to the element and 1 to all others in the 
container.

The `gravity` is where container should stick to if there is
more space then the element needs. It's for the alignment of the
elements. We can use string constants: `"top"`, `"bottom"`, `"center"`,
`"right"`, `"left"`, `"center-hor"`, `"center-ver"`.

#### Padding and margins

The padding specifies an offset within the element. 
We can specify padding and margin from four sides: 
`"left"`, `"right"`, `"top"`, `"bottom"`. We should sepcify 
the values with object:

~~~
{ "margin" : {
  	  "left"   : 10
	, "right"  : 10
	, "top"    : 15
	, "bottom" : 15	}
}
~~~

If all values are the same we can use a number:

~~~
{ "margin": 10 }
~~~

If values for left and right sides are the same and
at the same time the values for top and bottom are the same
we can use a pair of numbers:

~~~
{ "margin": [10, 15] }
~~~

Where the first value 10 is for left/right and the second one 
is for botom/top.

### Text

The text contains just three parameters: size, color and alignment:

~~~
{ "button": "btn1"

, "text-size": 22
, "text-color": "#00FF00"
, "text-align": "center"
}
~~~

Colors are specified as hash-codes. Alignment can be: center, left, right.

### Color

We can specify three colors: primary and secondary colors and background color:

~~~
{ "button": "btn1"
, "fst-color" : "#7FDBFF"
, "snd-color" : "gray"
, "bkg-color" : "gray" }
~~~

We can use hash-codes or special names for colors:

~~~
"navy", "blue", "aqua", "teal", "olive", "green" "lime", "yellow", "orange",
"red", "maroon", "fuchsia", "purple", "black", "gray", "silver".
~~~

Redefining the defaults
-------------------------

If any parameter is ommited the actual value is taken from defaults.
We can alter the defults for all childrens of a given parent:

~~~
{ "hor": [
		...
		long deep tree of widgets
		...
	]
  , "set-default": {
  		"text-size": 20, "text-color": "purple" }  	
}
~~~

That's how we can alter text size and color for all elements
in the hierarchy.

Csound player
-----------------------

We can play the csound files and organize files in playlists.
There are functions:

* Playlist -- view the current playlist and load tracks

* Browse   -- view all playlists.

* Recent   -- view recent tracks and playlists

* Remove stubs -- removes all tracks tha point to non existent files

* Add tracks  -- adds tracks to the current playlist

* Delete -- delete tracks/playlists

* Settings -- sets specific settings.

--------------------------------------------------------

The cool picture of the magician was found [here](http://www.people-clipart.com/people_clipart_images/people_cartoon_wizard_in_sorcerers_robe_and_a_magic_wand_0521-1010-2714-0148.html).


## Developers guide to code 

### Code roadmap

* `wizard` -- top level things: main activity, application object, constants: 

* `csound.channel` -- wrappers for csound value updaters

* `csound.listener` -- abstraction on top of `csound.channel`. The combinations of channels.

* `fragment` -- android fragments to show the user interface of the app.

* `layout` -- top level package to read the layout from the JSON.

* `layout.unit` -- reads the widget from JSON and binds it to csound.

* `layout.param` -- specs for widget's parameters (parsing the parameters).

* `model` -- app's state (manages playlists and settings).

* `view` -- helper utils to draw the widgets and listen for the events.

* `view.unit` -- views for widgets.

### Hints

To save the state of the widget we should extend from `StatefulUnit`. 
Then we define how to parse the state in the class `TrackState`. 
The method `getStateFromJson` does the thing.


Examples of the working files can be found in the directory `examples`.