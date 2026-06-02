#!/bin/bash

set -e

echo "Cleaning old files..."
find . -name "*.class" -delete
rm -f out/*.kc
rm -f out/*_restored.bmp

echo "Creating out folder..."
mkdir -p out

echo "Compiling..."
javac -d out \
    src/Huffman/*.java \
    src/Image/*.java \
    Testing/*.java

echo "Running bitmap tests..."
java -cp out Testing.TestImages


