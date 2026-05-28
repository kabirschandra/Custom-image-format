package Huffman;

/**
 * @brief Represents a node inside the Huffman tree
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    private char character;
    private int frequency;

    private HuffmanNode left;
    private HuffmanNode right;

    /**
     * @brief Construct a leaf node
     *
     * @param character :: Stored character
     * @param frequency :: Character frequency
     */
    public HuffmanNode(char character, int frequency) {

        this.character = character;
        this.frequency = frequency;

        this.left = null;
        this.right = null;
    }

    /**
     * @brief Construct an internal node
     *
     * @param frequency :: Combined frequency
     * @param left :: Left child node
     * @param right :: Right child node
     */
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {

        this.character = '\0'; //Internal node
        this.frequency = frequency;

        this.left = left;
        this.right = right;
    }

    /**
     * @brief Check if node is a leaf node
     *
     * @return boolean :: True if node has no children
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * @brief Get stored character
     *
     * @return char :: Stored character
     */
    public char getCharacter() {
        return character;
    }

    /**
     * @brief Get node frequency
     *
     * @return int :: Node frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * @brief Get left child
     *
     * @return HuffmanNode :: Left child node
     */
    public HuffmanNode getLeft() {
        return left;
    }

    /**
     * @brief Get right child
     *
     * @return HuffmanNode :: Right child node
     */
    public HuffmanNode getRight() {
        return right;
    }

    /**
     * @brief Set left child node
     *
     * @param left :: Left child node
     *
     * @return void :: None
     */
    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    /**
     * @brief Set right child node
     *
     * @param right :: Right child node
     *
     * @return void :: None
     */
    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    /**
     * @brief Compare nodes by frequency
     *
     * @param other :: Other node
     *
     * @return int :: Comparison result
     */
    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.frequency, other.frequency);
    }

    @Override
    public String toString() {

        if (isLeaf()) {
            return "Node[char=" + character + ", freq=" + frequency + "]";
        }

        return "Node[internal, freq=" + frequency + "]";
    }
}

