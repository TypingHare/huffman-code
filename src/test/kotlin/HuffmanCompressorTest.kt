import huffman.HuffmanCompressor
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HuffmanCompressorTest {
    @Test
    fun `convert binary string into byte array`() {
        val byteArray =
            HuffmanCompressor.binaryStringToByteArray("100100001101")
        assertEquals(2, byteArray.size)
        assertEquals(25, byteArray[0])
        assertEquals(13, byteArray[1])
    }

    @Test
    fun `convert binary string into byte array edge case`() {
        val byteArray =
            HuffmanCompressor.binaryStringToByteArray("0000100100001101")
        assertEquals(3, byteArray.size)
        assertEquals(1, byteArray[0])
        assertEquals(9, byteArray[1])
        assertEquals(13, byteArray[2])
    }
}