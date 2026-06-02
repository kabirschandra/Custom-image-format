package Huffman;

/**
 * @brief Represents a node inside the Huffman tree
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    private int symbol;
    private int frequency;
    private int smallestSymbol;

    private HuffmanNode left;
    private HuffmanNode right;

    /**
     * @brief Construct a leaf node
     *
     * @param symbol :: Stored symbol
     * @param frequency :: Symbol frequency
     */
    public HuffmanNode(int symbol, int frequency) {

        this.symbol = symbol;
        this.frequency = frequency;
        this.smallestSymbol = symbol;

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

        this.symbol = -1; //Internal node
        this.frequency = frequency;

        this.left = left;
        this.right = right;

        if (left == null && right == null) {
            this.smallestSymbol = -1;

        } else if (left == null) {
            this.smallestSymbol = right.getSmallestSymbol();

        } else if (right == null) {
            this.smallestSymbol = left.getSmallestSymbol();

        } else {
            this.smallestSymbol =
                    Math.min(left.getSmallestSymbol(), right.getSmallestSymbol());
        }
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
     * @brief Get stored symbol
     *
     * @return int :: Stored symbol
     */
    public int getSymbol() {
        return symbol;
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
     * @brief Get smallest symbol inside this branch
     *
     * @return int :: Smallest symbol
     */
    public int getSmallestSymbol() {
        return smallestSymbol;
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
     * @brief Compare nodes by frequency, then symbol order
     *
     * @param other :: Other node
     *
     * @return int :: Comparison result
     */
    @Override
    public int compareTo(HuffmanNode other) {

        int frequencyCompare =
                Integer.compare(this.frequency, other.frequency);

        if (frequencyCompare != 0) {
            return frequencyCompare;
        }

        return Integer.compare(this.smallestSymbol, other.smallestSymbol);
    }

    @Override
    public String toString() {

        if (isLeaf()) {
            return "Node[symbol=" + symbol + ", freq=" + frequency + "]";
        }

        return "Node[internal, freq=" + frequency + "]";
    }
}

