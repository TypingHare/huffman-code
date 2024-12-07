package huffman

import java.security.InvalidParameterException
import kotlin.Throws

class HuffmanExtractor(val huffmanTree: HuffmanTree) {
    @Throws(InvalidParameterException::class)
    fun extract(byteArray: ByteArray): ByteArray {
        var binaryString = byteArrayToBinaryString(byteArray)
        val byteList = mutableListOf<Byte>()
        while (!binaryString.isEmpty()) {
            val nextCode = lookup(binaryString)
            byteList.add(huffmanTree.getByte(nextCode))
            binaryString = binaryString.substring(nextCode.length)
        }

        return byteList.toByteArray()
    }

    @Throws(InvalidParameterException::class)
    fun lookup(string: String): String {
        var node: HuffmanNode = huffmanTree.root
        var index = 0

        while (!node.isLeaf()) {
            if (index >= string.length) {
                throw InvalidParameterException("Failed to lookup for the next code in the string: $string")
            }

            val bit = string[index]
            val nextNode = if (bit == '1') node.right else node.left
            if (nextNode == null) {
                throw InvalidParameterException("Node not found: $string")
            }

            node = nextNode
            index++
        }

        return string.substring(0, index)
    }

    companion object {
        /**
         * Converts a byte array into a binary string. The length of the
         * resulting string must be a multiple of 8.
         */
        fun byteArrayToBinaryString(bytes: ByteArray): String {
            val string =
                StringBuilder().apply {
                    bytes.forEach { append(byteToBinaryString(it)) }
                }.toString()
            val firstOneIndex = string.indexOf('1')

            return string.substring(firstOneIndex + 1, string.length)
        }

        /**
         * Converts a byte into a binary string. The return value is a string
         * of length 8.
         */
        private fun byteToBinaryString(byte: Byte): String =
            Integer.toBinaryString(byte.toInt() and 0xFF).padStart(8, '0')
    }
}