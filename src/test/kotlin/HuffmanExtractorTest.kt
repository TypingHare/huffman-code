import huffman.HuffmanExtractor
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HuffmanExtractorTest {
    @Test
    fun `convert byte array into byte string`() {
        val byteArray = byteArrayOf(25, 13)
        val binaryString = HuffmanExtractor.byteArrayToBinaryString(byteArray)
        assertEquals("100100001101", binaryString)
    }

    @Test
    fun `convert byte array into byte string edge case`() {
        val byteArray = byteArrayOf(1, 9, 13)
        val binaryString = HuffmanExtractor.byteArrayToBinaryString(byteArray)
        assertEquals("0000100100001101", binaryString)
    }
}