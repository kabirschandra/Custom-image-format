package Huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @brief Builds and manages a Huffman tree
 */
public class HuffmanTree {

    private HuffmanNode root;
    private Map<Integer, String> huffmanCodes;

    /**
     * @brief Construct Huffman tree from symbol frequencies
     *
     * @param frequencies :: Symbol frequency map
     */
    public HuffmanTree(Map<Integer, Integer> frequencies) {

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
     * @param frequencies :: Symbol frequencies
     *
     * @return HuffmanNode :: Root node
     */
    private HuffmanNode buildTree(Map<Integer, Integer> frequencies) {

        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();

        //Create leaf nodes
        for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()) {

            int symbol = entry.getKey();
            int frequency = entry.getValue();

            if (frequency > 0) {

                HuffmanNode node = new HuffmanNode(symbol, frequency);

                priorityQueue.add(node);
            }
        }

        //Special case: one unique symbol
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

        //Found a symbol
        if (node.isLeaf()) {

            huffmanCodes.put(node.getSymbol(), code);

            return;
        }

        generateCodes(node.getLeft(), code + "0");
        generateCodes(node.getRight(), code + "1");
    }

    /**
     * @brief Get Huffman code for a symbol
     *
     * @param symbol :: Input symbol
     *
     * @return String :: Binary code
     */
    public String getCode(int symbol) {

        String code = huffmanCodes.get(symbol);

        if (code == null) {
            return "";
        }

        return code;
    }

    /**
     * @brief Return all Huffman codes
     *
     * @return Map<Integer, String> :: Huffman code map
     */
    public Map<Integer, String> getAllCodes() {
        return new HashMap<>(huffmanCodes);
    }

    /**
     * @brief Encode data into binary
     *
     * @param data :: Input data
     *
     * @return String :: Encoded binary string
     */
    public String encode(int[] data) {

        if (data == null || data.length == 0) {
            return "";
        }

        StringBuilder encoded = new StringBuilder();

        for (int i = 0; i < data.length; i++) {

            int symbol = data[i];
            String code = getCode(symbol);

            if (code.isEmpty()) {
                throw new IllegalArgumentException("Symbol '" + symbol + "' not found in Huffman tree");
            }

            encoded.append(code);
        }

        return encoded.toString();
    }

    /**
     * @brief Decode binary back into data
     *
     * @param encodedData :: Encoded binary string
     *
     * @return int[] :: Decoded data
     */
    public int[] decode(String encodedData) {

        if (encodedData == null || encodedData.isEmpty()) {
            return new int[0];
        }

        int[] temp = new int[encodedData.length()];
        int count = 0;

        HuffmanNode current = root;

        for (int i = 0; i < encodedData.length(); i++) {

            char bit = encodedData.charAt(i);
            if (bit == '0') {
                current = current.getLeft();

            } else if (bit == '1') {
                current = current.getRight();

            } else {
                throw new IllegalArgumentException("Invalid bit: " + bit);
            }

            //Full symbol
            if (current.isLeaf()) {

                temp[count] = current.getSymbol();
                count++;

                current = root;
            }
        }

        int[] decoded = new int[count];

        for (int i = 0; i < count; i++) {
            decoded[i] = temp[i];
        }

        return decoded;
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

