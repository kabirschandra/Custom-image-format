package Testing;

import java.util.Map;

import Huffman.HuffmanCoding;
import Huffman.HuffmanTree;

public class TestHuffman {

    public static void main(String[] args) {

        int[] data = {104, 101, 108, 108, 111, 32, 73, 32, 97, 109, 32, 106, 111, 104, 110};
        String text = "hello I am john";

        System.out.println("Original:");
        System.out.println(text);

        //Count frequencies
        Map<Integer, Integer> frequencies = HuffmanCoding.countFrequencies(data);

        System.out.println("\nFrequencies:");

        for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()) {

            int symbol = entry.getKey();

            System.out.println("'" + (char) symbol + "' (" + symbol + ") : " + entry.getValue());
        }

        HuffmanTree tree = new HuffmanTree(frequencies);

        String encoded = tree.encode(data);

        System.out.println("\nEncoded:");
        System.out.println(encoded);

        int[] decoded = tree.decode(encoded);

        System.out.println("\nDecoded:");

        for (int symbol : decoded) {
            System.out.print((char) symbol);
        }

        System.out.println();

        System.out.println("\nCodes:");

        for (Map.Entry<Integer, String> entry : tree.getAllCodes().entrySet()) {

            int symbol = entry.getKey();

            System.out.println("'" + (char) symbol + "' (" + symbol + ") : " + entry.getValue());
        }

        boolean pass = true;

        if (data.length != decoded.length) {
            pass = false;

        } else {

            for (int i = 0; i < data.length; i++) {

                if (data[i] != decoded[i]) {
                    pass = false;
                    break;
                }
            }
        }

        if (pass) {
            System.out.println("\nPASS");

        } else {
            System.out.println("\nFAIL");
        }
    }
}

