# Custom Image Format

A Java image compression project that converts bitmap images into a custom `.kc` file format using Huffman coding.

The program reads BMP images, compresses the pixel data, writes a `.kc` file, decompresses it, and restores the image back to a BMP.

## Features

- BMP to `.kc` compression
- `.kc` to BMP decompression
- Huffman coding based compression
- RGB bitmap pixel support
- Batch testing for every BMP in `./data`
- Writes generated `.kc` files and restored BMP files to `./out`
- No external libraries

## Project Structure

| Directory | What's inside |
|---|---|
| `./src/Huffman` | Huffman node, tree, and coding utilities |
| `./src/Image` | Custom image file handling and bitmap reading/writing |
| `./Testing` | Test files for Huffman coding and image compression |
| `./data` | Input bitmap images |
| `./out` | Compiled classes, `.kc` files, and restored bitmap images |

## Requirements

- Java JDK
- Linux or WSL
- BMP images inside `./data`

## Quick Start

From the project root:

```bash
chmod +x run_tests.sh
./run_tests.sh
```

This compiles the project, compresses every `.bmp` file in `./data`, decompresses each one, and checks that the restored image matches the original image.

## Manual Compile

From the project root:

```bash
mkdir -p out

javac -d out \
    src/Huffman/*.java \
    src/Image/*.java \
    Testing/*.java
```

Run the bitmap tests:

```bash
java -cp out Testing.TestImages
```

Expected output:

```text
Testing: data/number.bmp
  Passed
Testing: data/heart.bmp
  Passed
Testing: data/controller.bmp
  Passed
Testing: data/gear.bmp
  Passed
Testing: data/dog.bmp
  Passed

=== Test Summary ===
Tests run: 5
Tests passed: 5
Tests failed: 0
```

## How It Works

The program reads a BMP file into an `ImageData` object.

`ImageData` stores:

- Width
- Height
- Pixel array

Each pixel is stored as a packed RGB integer.

## Compression

The compression step:

1. Reads bitmap pixels
2. Counts pixel frequencies
3. Builds a Huffman tree
4. Encodes the pixel array as a bit string
5. Writes the custom `.kc` file

## Decompression

The decompression step:

1. Reads the `.kc` file
2. Rebuilds the Huffman tree from the frequency table
3. Reads the encoded bit stream
4. Decodes the original pixel array
5. Writes the restored BMP image

## File Format

The `.kc` file stores:

- Magic header
- Image width
- Image height
- Number of unique pixel values
- Pixel frequency table
- Encoded bit length
- Compressed Huffman bit data

The file starts with:

```text
IMAGE
```

After that, binary values are written using Java `DataOutputStream`.

## Testing

The main image test is:

```text
Testing/TestImages.java
```

It scans:

```text
./data
```

For every `.bmp` file, it creates:

```text
./out/<name>.kc
./out/<name>_restored.bmp
```

Then it compares the original image data against the restored image data.

## Run Tests

Use the test runner:

```bash
./run_tests.sh
```

Or run manually:

```bash
javac -d out \
    src/Huffman/*.java \
    src/Image/*.java \
    Testing/*.java

java -cp out Testing.TestImages
```

## Cleaning

To remove generated class files and output images:

```bash
find . -name "*.class" -delete
rm -f out/*.kc
rm -f out/*_restored.bmp
```

## Limitations

- Only tested with BMP files
- Alpha channel is ignored
- Image metadata not preserved
- Compression depends on repeated colours
- Large images use a lot of memory

## Known Issues

If restored images look greyscale or colour-shifted, check `BitmapImage.java`.

It must store and write packed RGB values directly.

Correct:

```java
int rgb = image.pixels[index] & 0x00FFFFFF;
```

Incorrect:

```java
int rgb = (value << 16) | (value << 8) | value;
```


