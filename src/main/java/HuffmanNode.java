/**
 * Huffman tree node representation. Each node stores information about a character and its
 * frequency in a text, and may have left and right child nodes in the Huffman tree. Technically
 * speaking, a node is either a leaf node or a binary node (having two children).
 */
public class HuffmanNode {
    /**
     * This byte stands for no character. Only leaf node contains a character in a Huffman tree.
     */
    public static final byte NO_CHARACTER = -1;

    /**
     * The left node (0).
     */
    private HuffmanNode left;

    /**
     * The right node (1).
     */
    private HuffmanNode right;

    /**
     * The character of this node.
     */
    private final byte character;

    /**
     * The frequency of the character in the whole text.
     */
    private final int frequency;

    /**
     * Creates a Huffman node with the specified character and frequency.
     * @param character The character of this node.
     * @param frequency The frequency of the character in the whole text.
     */
    public HuffmanNode(byte character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    /**
     * Creates a Huffman node with the frequency.
     * @param frequency The frequency of the character in the whole text.
     */
    public HuffmanNode(int frequency) {
        this(NO_CHARACTER, frequency);
    }

    /**
     * Checks whether this node is a leaf node.
     * @return true if both left and right nodes are null.
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Gets the left child node.
     * @return The left child node.
     */
    public HuffmanNode getLeft() {
        return left;
    }

    /**
     * Sets the left child node.
     * @param left The left child node to be set.
     */
    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    /**
     * Gets the right child node.
     * @return The right child node.
     */
    public HuffmanNode getRight() {
        return right;
    }

    /**
     * Sets the right child node.
     * @param right The right child node to be set.
     */
    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    /**
     * Gets the character associated with this node.
     * @return The character of this node.
     */
    public byte getCharacter() {
        return character;
    }

    /**
     * Gets the frequency of the character associated with this node.
     * @return The frequency of the character in the whole text.
     */
    public int getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return parseChar(character) + ": " + frequency;
    }

    /**
     * Converts a character into a readable string.
     * @param character the character to convert.
     * @return a string.
     */
    public static String parseChar(byte character) {
        final char _char = (char) character;
        if (Character.isWhitespace(_char)) {
            return "<" + ((int) _char) + ">";
        } else {
            return String.valueOf(_char);
        }
    }
}
