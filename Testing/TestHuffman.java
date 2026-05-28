package Testing;
import java.util.Map;
import Huffman.HuffmanCoding;
import Huffman.HuffmanTree;

public class TestHuffman {

    public static void main(String[] args) {

        String text = "hello I am john";

        System.out.println("Original:");
        System.out.println(text);

        //Count frequencies
        Map<Character, Integer> frequencies = HuffmanCoding.countFrequencies(text);

        System.out.println("\nFrequencies:");

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {

            System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
        }


        HuffmanTree tree = new HuffmanTree(frequencies);

        String encoded = tree.encode(text);

        System.out.println("\nEncoded:");
        System.out.println(encoded);
        String decoded = tree.decode(encoded);

        System.out.println("\nDecoded:");
        System.out.println(decoded);
        System.out.println("\nCodes:");

        for (Map.Entry<Character, String> entry : tree.getAllCodes().entrySet()) {

            System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
        }


        if (text.equals(decoded)) {
            System.out.println("\nPASS");

        } else {
            System.out.println("\nFAIL");
        }
    }
}