import huffman.HuffmanNode
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.test.assertNull

class HuffmanNodeTest {
    @Test
    fun `constructor initializes properties correctly`() {
        val node = HuffmanNode(5, 'A'.toChar().code)

        assertEquals(5, node.frequency)
        assertEquals(65, node.byte)
        assertNull(node.left)
        assertNull(node.right)
    }

    @Test
    fun `isLeaf returns true for non-leaf nodes`() {
        assertFalse(HuffmanNode(5).isLeaf())
    }

    @Test
    fun `isLeaf returns false for leaf nodes`() {
        assertTrue(HuffmanNode(5, 65).isLeaf())
    }

    @Test
    fun `toByte converts byte correctly`() {
        val node = HuffmanNode(5, 65)
        assertEquals(65.toByte(), node.toByte())
    }
}