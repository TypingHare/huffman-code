package huffman

/**
 * Represents a Huffman node. It stores the information about a byte and its
 * frequency in a byte stream.
 * @param byte The byte that this node associated with. If the node is not a
 * leaf, its byte would be `EMPTY_BYTE`.
 * @param frequency The frequency of the byte.
 */
class HuffmanNode(val frequency: Int, val byte: Int = EMPTY_BYTE) {
    var left: HuffmanNode? = null
    var right: HuffmanNode? = null

    fun isLeaf(): Boolean = byte != EMPTY_BYTE

    fun toByte(): Byte = byte.toByte()

    override fun toString(): String = stringifyByte(byte)

    companion object {
        const val EMPTY_BYTE: Int = Int.MIN_VALUE

        fun stringifyByte(byte: Int): String {
            if (byte < 0) return "<${byte}>"

            val char = byte.toInt().toChar()
            return if (char.isWhitespace()) "<${char.code}>" else char.toString()
        }
    }
}