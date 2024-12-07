import huffman.HuffmanTree
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HuffmanTreeTest {
    @Test
    fun `getCode should return Huffman code`() {
        val str = """
            11111111111111111111111111222222222222223333334
        """.trimIndent()
        val huffmanTree = HuffmanTree.fromByteArray(str.toByteArray())

        assertEquals("0", huffmanTree.getCode('1'))
        assertEquals("10", huffmanTree.getCode('2'))
        assertEquals("110", huffmanTree.getCode('3'))
        assertEquals("111", huffmanTree.getCode('4'))
    }

    @Test
    fun `getByte should return the byte`() {
        val str = """
            11111111111111111111111111222222222222223333334
        """.trimIndent()
        val huffmanTree = HuffmanTree.fromByteArray(str.toByteArray())

        assertEquals('1'.code.toByte(), huffmanTree.getByte("0"))
        assertEquals('2'.code.toByte(), huffmanTree.getByte("10"))
        assertEquals('3'.code.toByte(), huffmanTree.getByte("110"))
        assertEquals('4'.code.toByte(), huffmanTree.getByte("111"))
    }

    @Test
    fun `convert binary code into an integer`() {
        assertEquals(4, HuffmanTree.codeToInt("100"))
        assertEquals(10, HuffmanTree.codeToInt("1010"))
        assertEquals(255, HuffmanTree.codeToInt("11111111"))
    }
}