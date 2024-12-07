package huffman

import java.security.InvalidParameterException
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.Throws

class HuffmanTree(val root: HuffmanNode) {
    private val byByte = hashMapOf<Byte, String>()
    private val byCode = hashMapOf<String, Byte>()

    init {
        traverseNodes(root, "")
    }

    private fun traverseNodes(node: HuffmanNode, code: String) {
        if (node.isLeaf()) {
            val byte = node.toByte()
            byByte[byte] = code
            byCode[code] = byte
        } else {
            node.left?.let { traverseNodes(it, code + '0') }
            node.right?.let { traverseNodes(it, code + '1') }
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    fun getCode(byte: Byte): String = byByte[byte]
        ?: throw NoSuchElementException("Byte does not exist: $byte")

    @Throws(NoSuchAlgorithmException::class)
    fun getCode(char: Char): String = getCode(char.code.toByte())

    @Throws(NoSuchAlgorithmException::class)
    fun getByte(code: String): Byte = byCode[code]
        ?: throw NoSuchElementException("Code does not exist: $code")

    fun printByteTable() {
        val comparator =
            Comparator.comparingInt<Map.Entry<Byte, String>> { it.value.length }
        PriorityQueue(comparator)
            .apply { addAll(byByte.entries) }
            .forEach { println("${it.key.toString().padStart(5)} ${it.value}") }
    }

    companion object {
        /**
         * Converts a byte array into Huffman tree.
         */
        fun fromByteArray(byteArray: ByteArray): HuffmanTree {
            // Traverse the byte array to get the frequency of each byte
            val frequencyTable = IntArray(Byte.MAX_VALUE - Byte.MIN_VALUE + 1)
            byteArray.forEach { frequencyTable[it.toInt() - Byte.MIN_VALUE]++ }

            // Create Huffman nodes based on the frequency array
            val nodeList = mutableListOf<HuffmanNode>()
            frequencyTable.forEachIndexed { byte, frequency ->
                if (frequency == 0) return@forEachIndexed
                nodeList.add(
                    HuffmanNode(
                        frequency,
                        byte + Byte.MIN_VALUE.toInt()
                    )
                )
            }

            // Create a priority queue to sort the Huffman nodes in an
            // increasing order of frequency
            val comparator = Comparator.comparingInt(HuffmanNode::frequency)
            val priorityQueue = PriorityQueue(comparator)
            priorityQueue.addAll(nodeList)

            // Create a Huffman tree based on the priority queue
            while (priorityQueue.size >= 2) {
                val node1 = priorityQueue.poll()
                val node2 = priorityQueue.poll()
                priorityQueue.add(combineNode(node1, node2))
            }

            return HuffmanTree(priorityQueue.poll())
        }

        /**
         * Combines two Huffman nodes into one. The frequency of the left node
         * must be greater than or equal to the right node.
         * @return An internal Huffman node.
         */
        private fun combineNode(
            node1: HuffmanNode,
            node2: HuffmanNode
        ): HuffmanNode {
            if (node1.frequency < node2.frequency) {
                return combineNode(node2, node1)
            }

            // Ensured: node1.frequency >= node2.frequency
            return HuffmanNode(node1.frequency + node2.frequency).apply {
                left = node1
                right = node2
            }
        }

        /**
         * Converts a binary string into an integer.
         */
        @Throws(InvalidParameterException::class)
        fun codeToInt(code: String): Int {
            if (code.isEmpty())
                throw InvalidParameterException("Input code must not be empty.")

            var result = 0
            code.forEach {
                result = (result shl 1) or (if (it == '1') 1 else 0)
            }

            return result
        }
    }
}