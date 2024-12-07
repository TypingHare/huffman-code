package huffman

class HuffmanCompressor(val huffmanTree: HuffmanTree) {
    fun compress(byteArray: ByteArray): ByteArray {
        val stringBuilder = StringBuilder()
        byteArray.forEach { stringBuilder.append(huffmanTree.getCode(it)) }

        return binaryStringToByteArray(stringBuilder.toString())
    }

    companion object {
        /**
         * Converts a binary string into a byte array.
         */
        fun binaryStringToByteArray(binaryString: String): ByteArray {
            // Unshift a 1 and pad 0's to make the length of the string be a
            // multiple of 8
            val numZero = 8 - ((binaryString.length + 1) % 8)
            val string = "0".repeat(numZero) + "1" + binaryString

            val byteList = mutableListOf<Byte>()
            for (i in 0 until string.length step 8) {
                val int =
                    HuffmanTree.codeToInt(string.substring(i, i + 8))
                byteList.add(int.toByte())
            }

            return byteList.toByteArray()
        }
    }
}