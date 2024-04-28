#!/bin/bash
# Install: https://github.com/RazrFalcon/svgcleaner-gui/releases
# Set path to svgcleaner
svgcleaner="/path/to/svgcleaner"

# Set directory to search
dir="."

# Loop through SVG files
for file in "$dir"/*.svg; do

  # Call svgcleaner using full path
  "$svgcleaner" --multipass "$file" "$file"

done
