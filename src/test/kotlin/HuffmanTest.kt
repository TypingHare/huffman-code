import huffman.HuffmanCompressor
import huffman.HuffmanExtractor
import huffman.HuffmanTree
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

class HuffmanTest {
    @Test
    fun `compress and extract a text`() {
        val inputText = """
            11111111111111111111111111222222222222223333334
        """.trimIndent()

        val inputBytes = inputText.toByteArray()
        val huffmanTree = HuffmanTree.fromByteArray(inputBytes)
        val compressedData =
            HuffmanCompressor(huffmanTree).compress(inputBytes)
        val decompressedData =
            HuffmanExtractor(huffmanTree).extract(compressedData)

        huffmanTree.printByteTable()

        assertArrayEquals(inputBytes, decompressedData)
    }

    @Test
    fun `compress and extract a text file`() {
        val resourcesDir = Path.of("src/test/resources")
        require(resourcesDir != null) { "Resources directory not found: $resourcesDir." }
        val inputFilePath = resourcesDir.resolve("test.txt")
        require(inputFilePath != null) { "Input file not found: $inputFilePath." }

        val inputBytes = Files.readAllBytes(inputFilePath)
        val huffmanTree = HuffmanTree.fromByteArray(inputBytes)
        val compressedData = HuffmanCompressor(huffmanTree).compress(inputBytes)

        // Output the byte array into a new file
        val outputFilePath = resourcesDir.resolve("test.dat")
        Files.write(outputFilePath, compressedData)

        // Extract
        val extractedBytes =
            HuffmanExtractor(huffmanTree).extract(compressedData)
        assertArrayEquals(inputBytes, extractedBytes)

        // Print extra information
        val inputFileSize = inputFilePath.toFile().length()
        val outputFileSize = outputFilePath.toFile().length()
        val compressionRate =
            (inputFileSize - outputFileSize) / outputFileSize.toFloat()
        println("Original file size: $inputFileSize")
        println("Compressed file size: $outputFileSize")
        println("Compression rate: ${compressionRate * 100}%")
    }
}