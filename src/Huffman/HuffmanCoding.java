package Huffman;

import java.util.HashMap;
import java.util.Map;

/**
 * @brief Utility methods used for Huffman compression
 */
public class HuffmanCoding {

    /**
     * @brief Count frequency of each symbol in an array
     *
     * @param data :: Input data
     *
     * @return Map<Integer, Integer> :: Symbol frequency map
     */
    public static Map<Integer, Integer> countFrequencies(int[] data) {

        if (data == null || data.length == 0) {
            
            return new HashMap<>();
        }

        Map<Integer, Integer> frequencies = new HashMap<>();

        //Count each symbol 
        for (int i = 0; i < data.length; i++) {

            int symbol = data[i];

            frequencies.put(symbol, frequencies.getOrDefault(symbol, 0) + 1);
        }

        return frequencies;
    }

    /**
     * @brief Calculate compression ratio
     *
     * @param original :: Original data
     * @param encoded :: Encoded binary string
     *
     * @return double :: Compression ratio
     */
    public static double calculateCompressionRatio(int[] original, String encoded) {

        if (original == null || original.length == 0) {
            return 0.0;
        }

        int originalBits = original.length * 8; //One image symbol uses 8 bits
        int compressedBits = encoded.length();

        return (double) compressedBits / originalBits;
    }

    /**
     * @brief Print compression statistics and Huffman codes
     *
     * @param original :: Original data
     * @param encoded :: Encoded binary string
     * @param codes :: Huffman code map
     *
     * @return void :: None
     */
    public static void printStatistics(
            int[] original,
            String encoded,
            Map<Integer, String> codes) {

        System.out.println("=== Huffman Compression Statistics ===");
        System.out.println("Original data length: " + original.length + " symbols");
        System.out.println("Original size: " + (original.length * 8) + " bits");
        System.out.println("Compressed size: " + encoded.length() + " bits");

        double ratio = calculateCompressionRatio(original, encoded);
        double savings = (1 - ratio) * 100;

        System.out.printf("Compression ratio: %.2f%%\n", ratio * 100);
        System.out.printf("Space savings: %.2f%%\n", savings);

        System.out.println("\nHuffman Codes:");

        for (Map.Entry<Integer, String> entry : codes.entrySet()) {

            int symbol = entry.getKey();
            String code = entry.getValue();

            System.out.println("  " + symbol + " : " + code);
        }
    }
}


