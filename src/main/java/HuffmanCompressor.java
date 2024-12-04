import java.util.*;

/**
 * Compresses text files using Huffman coding algorithm. Note that this compressor only support
 * ASCII coding text.
 * @link <a href="https://cgi.luddy.indiana.edu/~yye/c343-2019/huffman.php">Huffman coding</a>
 */
public class HuffmanCompressor {
    /**
     * Compresses a byte array (ASCII-based text).
     * @param byteArray The byte array to compress.
     * @return A compressed byte array.
     */
    public static byte[] compressByteArray(final byte[] byteArray, final HuffmanTree huffmanTree) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final byte _byte : byteArray) {
            final String code = huffmanTree.getCode(_byte);
            stringBuilder.append(code);
        }

        return binaryStringToByteArray(stringBuilder.toString());
    }

    public static byte[] decompressByteArray(final byte[] byteArray, final HuffmanTree huffmanTree) {
        // Convert the byte array to byte string
        String binaryString = byteArrayToBinaryString(byteArray);

        // Get characters bit by bit (difficult part)
        final List<Byte> characterList = new ArrayList<>();
        while (!binaryString.isEmpty()) {
            final String nextCode = huffmanTree.getNextCode(binaryString);
            characterList.add(huffmanTree.getCharacter(nextCode));

            binaryString = binaryString.substring(nextCode.length());
        }

        return byteListToByteArray(characterList);
    }

    public static HuffmanTree getHuffmanTree(final byte[] byteArray) {
        // Traverse the byte array to get the frequency of each character
        // Note: ASCII characters ranges from 0 to 127 (included)
        final int[] frequency = new int[Byte.MAX_VALUE + 1];
        for (byte _byte : byteArray) {
            frequency[_byte]++;
        }

        // Create Huffman nodes based on the frequency array
        final List<HuffmanNode> nodeList = new ArrayList<>();
        for (byte character = 0; character >= 0; character++) {
            if (frequency[character] == 0) {
                continue;
            }

            nodeList.add(new HuffmanNode(character, frequency[character]));
        }

        // Create a priority queue to sort the Huffman nodes in an increasing order of frequency
        final Comparator<HuffmanNode> comparator = Comparator.comparingInt(HuffmanNode::getFrequency);
        final PriorityQueue<HuffmanNode> nodePriorityQueue = new PriorityQueue<>(comparator);
        nodePriorityQueue.addAll(nodeList);

        // Create a Huffman tree based on the entry priority queue
        return createHuffmanTree(nodePriorityQueue);
    }

    /**
     * Creates and return a Huffman tree based on the given Huffman node priority queue.
     * @param queue A priority queue of Huffman nodes.
     * @return A Huffman tree.
     */
    private static HuffmanTree createHuffmanTree(final PriorityQueue<HuffmanNode> queue) {
        while (queue.size() >= 2) {
            final HuffmanNode internalNode
                = combineNode(queue.poll(), Objects.requireNonNull(queue.poll()));
            queue.add(internalNode);
        }

        // The last Huffman node in the queue must be the root node
        return new HuffmanTree(queue.poll());
    }

    /**
     * Combines two Huffman node into one. The frequency of the left node is larger or equal to the
     * frequency of the right node.
     * @param node1 The first Huffman node to combine.
     * @param node2 The second Huffman node to combine.
     * @return A internal Huffman node.
     */
    private static HuffmanNode combineNode(final HuffmanNode node1, final HuffmanNode node2) {
        if (node1.getFrequency() < node2.getFrequency()) {
            return combineNode(node2, node1);
        }

        // node1's frequency >= node2's frequency
        final HuffmanNode node = new HuffmanNode(node1.getFrequency() + node2.getFrequency());
        node.setLeft(node1);
        node.setRight(node2);

        return node;
    }

    /**
     * Converts a binary string to a byte array.
     * @param binaryString The binary string to convert.
     * @return a byte array.
     */
    private static byte[] binaryStringToByteArray(String binaryString) {
        // The prefix "1" aims at locating the beginning of the binary string
        binaryString = "1" + binaryString;

        final int remainder = binaryString.length() % 8;
        if (remainder != 0) {
            binaryString = "0".repeat(8 - remainder) + binaryString;
        }

        final List<Byte> byteList = new ArrayList<>();
        for (int i = 0; i < binaryString.length(); i += 8) {
            final String str = binaryString.substring(i, i + 8);
            byteList.add(parseByte(str));
        }

        return byteListToByteArray(byteList);
    }

    /**
     * Converts a byte array into a binary string.
     * @param byteArray The byte array to convert.
     * @return a binary string.
     */
    private static String byteArrayToBinaryString(final byte[] byteArray) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (byte _byte : byteArray) {
            stringBuilder.append(parseString(_byte));
        }

        // Find the first "1", and returns the substring after it
        final String string = stringBuilder.toString();
        int index = string.indexOf('1');

        return string.substring(index + 1);
    }

    /**
     * Parses a byte string.
     * @param byteString the string to parse; it should be length of 8.
     * @return a byte.
     */
    private static byte parseByte(final String byteString) {
        byte res = (byte) (byteString.charAt(0) == '0' ? 0 : 1);
        for (int i = 1; i < 8; i++) {
            res <<= 1;
            final char m = byteString.charAt(i);
            if (m == '1') {
                res |= 1;
            }
        }
        return res;
    }

    /**
     * Parses a byte.
     * @param _byte The byte to parse.
     * @return a corresponding byte string.
     */
    private static String parseString(byte _byte) {
        final StringBuilder res = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if ((_byte & 1) == 1) {
                res.insert(0, "1");
            } else {
                res.insert(0, "0");
            }
            _byte >>= 1;
        }

        return res.toString();
    }

    /**
     * Converts a byte list into a byte array.
     * @param byteList the byte list to convert.
     * @return a byte array,
     */
    private static byte[] byteListToByteArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }

        return byteArray;
    }
}
