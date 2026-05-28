package Huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @brief Builds and manages a Huffman tree
 */
public class HuffmanTree {

    private HuffmanNode root;
    private Map<Character, String> huffmanCodes;

    /**
     * @brief Construct Huffman tree from character frequencies
     *
     * @param frequencies :: Character frequency map
     */
    public HuffmanTree(Map<Character, Integer> frequencies) {

        if (frequencies == null || frequencies.isEmpty()) {
            throw new IllegalArgumentException("Frequencies map cannot be null or empty");
        }

        this.huffmanCodes = new HashMap<>();

        this.root = buildTree(frequencies);

        //Generate binary codes once tree is built
        generateCodes(root, "");
    }

    /**
     * @brief Build Huffman tree using a priority queue
     *
     * @param frequencies :: Character frequencies
     *
     * @return HuffmanNode :: Root node
     */
    private HuffmanNode buildTree(Map<Character, Integer> frequencies) {

        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();

        //Create leaf nodes
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {

            char character = entry.getKey();
            int frequency = entry.getValue();

            if (frequency > 0) {

                HuffmanNode node = new HuffmanNode(character, frequency);

                priorityQueue.add(node);
            }
        }

        //Special case: one unique character
        if (priorityQueue.size() == 1) {

            HuffmanNode singleNode = priorityQueue.poll();

            return new HuffmanNode(singleNode.getFrequency(), singleNode, null);
        }

        //Combine smallest nodes repeatedly
        while (priorityQueue.size() > 1) {

            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            int combinedFrequency = left.getFrequency() + right.getFrequency();
            HuffmanNode parent = new HuffmanNode(combinedFrequency, left, right);

            priorityQueue.add(parent);
        }

        return priorityQueue.poll();
    }

    /**
     * @brief Traverse tree and generate Huffman codes
     *
     * @param node :: Current node
     * @param code :: Current binary code
     *
     * @return void :: None
     */
    private void generateCodes(HuffmanNode node, String code) {

        if (node == null) {
            return;
        }

        //Found a character
        if (node.isLeaf()) {

            huffmanCodes.put(node.getCharacter(), code);

            return;
        }

        generateCodes(node.getLeft(), code + "0");
        generateCodes(node.getRight(), code + "1");
    }

    /**
     * @brief Get Huffman code for a character
     *
     * @param character :: Input character
     *
     * @return String :: Binary code
     */
    public String getCode(char character) {

        String code = huffmanCodes.get(character);

        if (code == null) {
            return "";
        }

        return code;
    }

    /**
     * @brief Return all Huffman codes
     *
     * @return Map<Character, String> :: Huffman code map
     */
    public Map<Character, String> getAllCodes() {
        return new HashMap<>(huffmanCodes);
    }

    /**
     * @brief Encode text into binary
     *
     * @param text :: Input text
     *
     * @return String :: Encoded binary string
     */
    public String encode(String text) {

        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder encoded = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);
            String code = getCode(c);

            if (code.isEmpty()) {
                throw new IllegalArgumentException("Character '" + c + "' not found in Huffman tree");
            }

            encoded.append(code);
        }

        return encoded.toString();
    }

    /**
     * @brief Decode binary back into text
     *
     * @param encodedText :: Encoded binary string
     *
     * @return String :: Decoded text
     */
    public String decode(String encodedText) {

        if (encodedText == null || encodedText.isEmpty()) {
            return "";
        }

        StringBuilder decoded = new StringBuilder();

        HuffmanNode current = root;

        for (int i = 0; i < encodedText.length(); i++) {

            char bit = encodedText.charAt(i);
            if (bit == '0') {
                current = current.getLeft();

            } else if (bit == '1') {
                current = current.getRight();

            } else {
                throw new IllegalArgumentException("Invalid bit: " + bit);
            }

            //Full character
            if (current.isLeaf()) {

                decoded.append(current.getCharacter());

                current = root;
            }
        }

        return decoded.toString();
    }

    /**
     * @brief Get root node
     *
     * @return HuffmanNode :: Root node
     */
    public HuffmanNode getRoot() {
        return root;
    }

    /**
     * @brief Print tree structure
     *
     * @return void :: None
     */
    public void printTree() {
        printTreeHelper(root, 0);
    }

    /**
     * @brief Recursive helper used to print tree
     *
     * @param node :: Current node
     * @param depth :: Tree depth
     *
     * @return void :: None
     */
    private void printTreeHelper(HuffmanNode node, int depth) {

        if (node == null) {
            return;
        }


        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }

        System.out.println(node);
        printTreeHelper(node.getLeft(), depth + 1);
        printTreeHelper(node.getRight(), depth + 1);
    }
}

