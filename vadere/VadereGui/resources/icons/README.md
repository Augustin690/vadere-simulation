# Designing Icons

Icons play a vital role in understanding the intend of a implemented action. Therefore, it is a important topic to discuss.
Many of the icons used in Vadere are still rasterized files (like png,jpeg,tiff), which is problematic considering different screen resolutions.

The alternative approach to this are scalable vector graphics (svg) which provide drawing instructions instead of pixel data.
SVGs will only be rasterized on instantiation, therefore they can be scaled without loosing information, so images wont get blurry or pixelated.

## Editing SVGs
To design new icons or change existing onse you can use open source tool like [Inkscape](https://inkscape.org).
The common viewport size used for editing and saving svg icons should be 32x32 pixels.

**_NOTE:_** The color RGB(100,100,100) is reserved as a template color. Which will be replaced by Vadere on loading the file. This is done in `org.vadere.gui.components.utils.Resources` method `public Icon getIconSVG(final String name, final int iconWidth, final int iconHeight, Color newColor)` where `newColor`is the color that replaces RGB(100,100,100). This may also be usefull for a dark theme support in the future.

## Cleaning up SVGs

Editors like Inkscape often store some metadata into SVG files which is only usefull for that application. To remove this you can use a tool like 'svgcleaner'.

Install it from here: https://github.com/RazrFalcon/svgcleaner-gui/releases

A script is available in this directory `clean_svgs.sh` which does automatically clean up all .svg files in this directory. Before runnin it be sure to change the path of svgcleaner in the script to your install location.
