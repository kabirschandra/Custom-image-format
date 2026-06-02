package Image;

import Huffman.HuffmanCoding;
import Huffman.HuffmanTree;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @brief Handles reading and writing custom image files
 */
public class Image {

    /**
     * @brief Stores image data
     */
    public static class ImageData {

        public int width;
        public int height;
        public int[] pixels;

        /**
         * @brief Construct image data
         *
         * @param width :: Image width
         * @param height :: Image height
         * @param pixels :: Image pixels
         */
        public ImageData(int width, int height, int[] pixels) {

            this.width = width;
            this.height = height;
            this.pixels = pixels;
        }
    }

    /**
     * @brief Writes bits to a file
     */
    private static class BitWriter {

        private OutputStream output;
        private int currentByte;
        private int bitCount;

        /**
         * @brief Construct bit writer
         *
         * @param output :: Output stream
         */
        public BitWriter(OutputStream output) {

            this.output = output;
            this.currentByte = 0;
            this.bitCount = 0;
        }

        /**
         * @brief Write one bit
         *
         * @param bit :: Bit to write
         *
         * @return void :: None
         */
        public void writeBit(int bit) {

            try {

                currentByte = currentByte << 1;
                currentByte = currentByte | (bit & 1);

                bitCount++;

                if (bitCount == 8) {

                    output.write(currentByte);

                    currentByte = 0;
                    bitCount = 0;
                }

            } catch (IOException e) {

                System.out.println("Error writing bit");
            }
        }

        /**
         * @brief Write many bits
         *
         * @param bits :: Bit string
         *
         * @return void :: None
         */
        public void writeBits(String bits) {

            for (int i = 0; i < bits.length(); i++) {

                if (bits.charAt(i) == '1') {
                    writeBit(1);

                } else {
                    writeBit(0);
                }
            }
        }

        /**
         * @brief Flush remaining bits
         *
         * @return void :: None
         */
        public void flush() {

            try {

                if (bitCount > 0) {

                    currentByte = currentByte << (8 - bitCount);

                    output.write(currentByte);
                }

                output.flush();

            } catch (IOException e) {

                System.out.println("Error flushing bits");
            }
        }
    }

    /**
     * @brief Reads bits from a file
     */
    private static class BitReader {

        private InputStream input;
        private int currentByte;
        private int bitsRemaining;

        /**
         * @brief Construct bit reader
         *
         * @param input :: Input stream
         */
        public BitReader(InputStream input) {

            this.input = input;
            this.currentByte = 0;
            this.bitsRemaining = 0;
        }

        /**
         * @brief Read one bit
         *
         * @return int :: Bit value
         */
        public int readBit() {

            try {

                if (bitsRemaining == 0) {

                    currentByte = input.read();

                    if (currentByte == -1) {
                        return -1;
                    }

                    bitsRemaining = 8;
                }

                bitsRemaining--;

                return (currentByte >> bitsRemaining) & 1;

            } catch (IOException e) {

                System.out.println("Error reading bit");
                return -1;
            }
        }
    }

    /**
     * @brief Write image to custom file
     *
     * @param image :: Image data
     * @param filename :: Output filename
     *
     * @return boolean :: True if file was written
     */
    public static boolean write(ImageData image, String filename) {

        try {

            Map<Integer, Integer> frequencies = HuffmanCoding.countFrequencies(image.pixels);

            HuffmanTree tree = new HuffmanTree(frequencies);

            String encoded = tree.encode(image.pixels);

            DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));

            out.writeBytes("IMAGE");

            out.writeInt(image.width);
            out.writeInt(image.height);

            out.writeInt(frequencies.size());

            for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()) {

                out.writeInt(entry.getKey());
                out.writeInt(entry.getValue());
            }

            out.writeInt(encoded.length());

            BitWriter writer = new BitWriter(out);

            writer.writeBits(encoded);
            writer.flush();

            out.close();

            return true;

        } catch (Exception e) {

            System.out.println("Error writing image file");
            return false;
        }
    }

    /**
     * @brief Read image from custom file
     *
     * @param filename :: Input filename
     *
     * @return ImageData :: Image data
     */
    public static ImageData read(String filename) {

        try {

            DataInputStream in = new DataInputStream(new FileInputStream(filename));

            byte[] magic = new byte[5];
            in.readFully(magic);

            String magicString = new String(magic);

            if (!magicString.equals("IMAGE")) {

                System.out.println("Not a valid image file");
                in.close();
                return null;
            }

            int width = in.readInt();
            int height = in.readInt();

            int frequencyCount = in.readInt();

            Map<Integer, Integer> frequencies = new HashMap<>();

            for (int i = 0; i < frequencyCount; i++) {

                int symbol = in.readInt();
                int frequency = in.readInt();

                frequencies.put(symbol, frequency);
            }

            int bitCount = in.readInt();

            HuffmanTree tree = new HuffmanTree(frequencies);

            BitReader reader = new BitReader(in);

            String encoded = "";

            for (int i = 0; i < bitCount; i++) {

                int bit = reader.readBit();

                if (bit == 1) {
                    encoded = encoded + "1";

                } else {
                    encoded = encoded + "0";
                }
            }

            int[] pixels = tree.decode(encoded);

            in.close();

            return new ImageData(width, height, pixels);

        } catch (Exception e) {

            System.out.println("Error reading image file");
            return null;
        }
    }
}



