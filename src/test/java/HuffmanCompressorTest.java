import org.junit.jupiter.api.Test;

public class HuffmanCompressorTest {
    @Test
    public void shouldCompressText() {
        final String text = "1111111111111111111111111111111111111111111111222222222222223333334";
        final byte[] textByteArray = text.getBytes();
        final HuffmanTree huffmanTree = HuffmanCompressor.getHuffmanTree(textByteArray);
        final byte[] compressedByteArray
            = HuffmanCompressor.compressByteArray(textByteArray, huffmanTree);

        System.out.println("The frequency of each character in the Huffman tree:");
        huffmanTree.printFrequencies();
        System.out.println();

        final byte[] decompressedByteArray
            = HuffmanCompressor.decompressByteArray(compressedByteArray, huffmanTree);
        final String decompressedText = new String(decompressedByteArray);
        System.out.println(text.equals(decompressedText));
    }

    @Test
    public void shouldCompressFile() {
        final String sourceDir = "src/main/resources/";
        final String textFile = sourceDir + "text.txt";
        final String compressedFile = sourceDir + "compressed.dat";
        final String decompressedFile = sourceDir + "decompressed.txt";

        // Compress process
        final byte[] textByteArray = FileUtil.readFile(textFile);
        final HuffmanTree huffmanTree = HuffmanCompressor.getHuffmanTree(textByteArray);
        final byte[] compressedByteArray
            = HuffmanCompressor.compressByteArray(textByteArray, huffmanTree);
        FileUtil.outputByteArray(compressedByteArray, compressedFile);

        System.out.println("All characters and their corresponding Huffman code:");
        huffmanTree.printCharacterMap();
        System.out.println();

        // Decompress process
        final byte[] binaryByteArray = FileUtil.readFile(compressedFile);
        final byte[] decompressedByteArray
            = HuffmanCompressor.decompressByteArray(binaryByteArray, huffmanTree);
        FileUtil.outputByteArray(decompressedByteArray, decompressedFile);
    }
}
