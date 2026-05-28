package Huffman;

import java.util.HashMap;
import java.util.Map;

/**
 * @brief Utility methods used for Huffman compression
 */
public class HuffmanCoding {

    /**
     * @brief Count frequency of each character in a string
     *
     * @param text :: Input text
     *
     * @return Map<Character, Integer> :: Character frequency map
     */
    public static Map<Character, Integer> countFrequencies(String text) {

        if (text == null || text.isEmpty()) {
            
            return new HashMap<>();
        }

        Map<Character, Integer> frequencies = new HashMap<>();

        //Count each character 
        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }

        return frequencies;
    }

    /**
     * @brief Calculate compression ratio
     *
     * @param original :: Original text
     * @param encoded :: Encoded binary string
     *
     * @return double :: Compression ratio
     */
    public static double calculateCompressionRatio(String original, String encoded) {

        if (original == null || original.isEmpty()) {
            return 0.0;
        }

        int originalBits = original.length() * 8; //ASCII uses 8 bits
        int compressedBits = encoded.length();

        return (double) compressedBits / originalBits;
    }

    /**
     * @brief Print compression statistics and Huffman codes
     *
     * @param original :: Original text
     * @param encoded :: Encoded binary string
     * @param codes :: Huffman code map
     *
     * @return void :: None
     */
    public static void printStatistics(
            String original,
            String encoded,
            Map<Character, String> codes) {

        System.out.println("=== Huffman Compression Statistics ===");
        System.out.println("Original text length: " + original.length() + " characters");
        System.out.println("Original size: " + (original.length() * 8) + " bits");
        System.out.println("Compressed size: " + encoded.length() + " bits");

        double ratio = calculateCompressionRatio(original, encoded);
        double savings = (1 - ratio) * 100;

        System.out.printf("Compression ratio: %.2f%%\n", ratio * 100);
        System.out.printf("Space savings: %.2f%%\n", savings);

        System.out.println("\nHuffman Codes:");

        for (Map.Entry<Character, String> entry : codes.entrySet()) {

            char c = entry.getKey();
            String code = entry.getValue();

            if (c == '\n') {
                System.out.println("  '\\n' : " + code);

            } else if (c == ' ') {
                System.out.println("  space: " + code);

            } else {
                System.out.println("  '" + c + "'   : " + code);
            }
        }
    }
}






