import java.util.*;

/**
 * Huffman tree.
 */
public class HuffmanTree {
    /**
     * The root node.
     */
    private final HuffmanNode root;

    /**
     * Mapping from characters to the corresponding Huffman codes, which are represented as strings
     * of bits.
     */
    private final Map<Byte, String> byCharacter = new HashMap<>();

    /**
     * Mapping from Huffman codes to the corresponding character.
     */
    private final Map<String, Byte> byCode = new HashMap<>();

    /**
     * Creates a Huffman tree.
     * @param root The root node.
     */
    public HuffmanTree(HuffmanNode root) {
        this.root = root;

        // Traverse the tree and initialize byCharacter and byCode
        readNode(root, "");
    }

    /**
     * Reads a node.
     * @param node The node to read.
     * @param code The code prefix.
     */
    private void readNode(final HuffmanNode node, final String code) {
        if (node.isLeaf()) {
            final byte character = node.getCharacter();
            byCharacter.put(character, code);
            byCode.put(code, character);
        } else {
            readNode(node.getLeft(), code + '0');
            readNode(node.getRight(), code + '1');
        }
    }

    /**
     * Get a code of a character.
     * @param character The character given.
     * @return the code of the given character.
     * @throws NoSuchElementException if the given character doesn't exist in this tree.
     */
    public String getCode(final byte character) throws NoSuchElementException {
        return Optional.of(byCharacter.get(character))
            .orElseThrow(() -> new NoSuchElementException("Key does not exist: " + ((char) character)));
    }

    /**
     * Gets character by a code.
     * @param code The code given.
     * @return the character of the given code.
     * @throws NoSuchElementException if the given code doesn't exist in this tree.
     */
    public byte getCharacter(final String code) throws NoSuchElementException {
        return Optional.of(byCode.get(code))
            .orElseThrow(() -> new NoSuchElementException("Code does not exist:" + code));
    }

    /**
     * Gets the next code.
     * @param binaryString The binary string; should contain either 0s or 1s.
     * @return The next code.
     */
    public String getNextCode(String binaryString) {
        HuffmanNode currentNode = root;
        int index = 0;
        while (!currentNode.isLeaf()) {
            final char bit = binaryString.charAt(index);
            currentNode = bit == '1' ? currentNode.getRight() : currentNode.getLeft();

            index++;
        }

        return binaryString.substring(0, index);
    }

    /**
     * Prints all characters and their corresponding Huffman codes.
     */
    public void printCharacterMap() {
        final Comparator<Map.Entry<Byte, String>> comparator
            = Comparator.comparingInt(entry -> entry.getValue().length());
        final PriorityQueue<Map.Entry<Byte, String>> priorityQueue
            = new PriorityQueue<>(comparator);
        priorityQueue.addAll(byCharacter.entrySet());

        for (Map.Entry<Byte, String> entry : priorityQueue) {
            System.out.println("'" + HuffmanNode.parseChar(entry.getKey()) + "': "
                + entry.getValue());
        }
    }

    /**
     * Prints all codes and their corresponding characters.
     */
    public void printCodeMap() {
        for (Map.Entry<String, Byte> entry : byCode.entrySet()) {
            System.out.println(entry.getKey() + ": "
                + "'" + HuffmanNode.parseChar(entry.getValue()) + "'");
        }
    }

    /**
     * Prints the frequency of each character in a decreasing order.
     */
    public void printFrequencies() {
        final List<HuffmanNode> nodeList = new ArrayList<>();
        addAllLeafNodesToList(root, nodeList);

        final Comparator<HuffmanNode> comparator = Comparator.comparingInt(HuffmanNode::getFrequency);
        final PriorityQueue<HuffmanNode> nodePriorityQueue = new PriorityQueue<>(comparator);
        nodePriorityQueue.addAll(nodeList);
        nodePriorityQueue.forEach(System.out::println);
    }

    /**
     * Adds all leaf nodes of a node to a list.
     * @param node     The node to add.
     * @param nodeList The list of node.
     */
    private void addAllLeafNodesToList(HuffmanNode node, List<HuffmanNode> nodeList) {
        if (node.isLeaf()) {
            nodeList.add(node);
        } else {
            addAllLeafNodesToList(node.getLeft(), nodeList);
            addAllLeafNodesToList(node.getRight(), nodeList);
        }
    }
}
