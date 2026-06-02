package Testing;

import Image.Image;
import Image.Image.ImageData;
import Image.BitmapImage;

import java.io.File;

/**
 * @brief Tests all bitmap files inside the data folder
 */
public class TestImages {

    /**
     * @brief Program entry point
     *
     * @param args :: Command line arguments
     *
     * @return void :: None
     */
    public static void main(String[] args) {

        File dataFolder = new File("data");

        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            System.out.println("Data folder was not found");
            return;
        }

        File outputFolder = new File("out");

        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        File[] files = dataFolder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found in data folder");
            return;
        }

        int testsRun = 0;
        int testsPassed = 0;

        for (int i = 0; i < files.length; i++) {

            File file = files[i];

            if (!isBitmap(file)) {
                continue;
            }

            if (isRestoredBitmap(file)) {
                continue;
            }

            testsRun++;

            boolean passed = testBitmap(file);

            if (passed) {
                testsPassed++;
            }
        }

        System.out.println();
        System.out.println("=== Test Summary ===");
        System.out.println("Tests run: " + testsRun);
        System.out.println("Tests passed: " + testsPassed);
        System.out.println("Tests failed: " + (testsRun - testsPassed));
    }

    /**
     * @brief Test one bitmap file
     *
     * @param bitmapFile :: Bitmap file to test
     *
     * @return boolean :: True if compression and decompression worked
     */
    private static boolean testBitmap(File bitmapFile) {

        String inputName = bitmapFile.getPath();
        String baseName = removeExtension(bitmapFile.getName());

        String compressedName = "out" + File.separator + baseName + ".kc";
        String outputName = "out" + File.separator + baseName + "_restored.bmp";

        System.out.println("Testing: " + inputName);

        ImageData original = BitmapImage.readBitmap(inputName);

        if (original == null) {
            System.out.println("  Failed: could not read original bitmap");
            return false;
        }

        boolean written = Image.write(original, compressedName);

        if (!written) {
            System.out.println("  Failed: could not write KC file");
            return false;
        }

        ImageData decoded = Image.read(compressedName);

        if (decoded == null) {
            System.out.println("  Failed: could not read KC file");
            return false;
        }

        if (!imagesMatch(original, decoded)) {
            System.out.println("  Failed: decoded KC data does not match original");
            return false;
        }

        BitmapImage.writeBitmap(decoded, outputName);

        ImageData restored = BitmapImage.readBitmap(outputName);

        if (restored == null) {
            System.out.println("  Failed: could not read restored bitmap");
            return false;
        }

        if (!imagesMatch(original, restored)) {
            System.out.println("  Failed: restored bitmap does not match original");
            return false;
        }

        System.out.println("  Passed");
        return true;
    }

    /**
     * @brief Check if a file is a bitmap
     *
     * @param file :: File to check
     *
     * @return boolean :: True if file ends with bmp
     */
    private static boolean isBitmap(File file) {

        if (!file.isFile()) {
            return false;
        }

        String name = file.getName().toLowerCase();

        return name.endsWith(".bmp");
    }

    /**
     * @brief Check if a bitmap is a generated restored file
     *
     * @param file :: File to check
     *
     * @return boolean :: True if file is a restored bitmap
     */
    private static boolean isRestoredBitmap(File file) {

        String name = file.getName().toLowerCase();

        return name.endsWith("_restored.bmp");
    }

    /**
     * @brief Remove file extension from filename
     *
     * @param filename :: Input filename
     *
     * @return String :: Filename without extension
     */
    private static String removeExtension(String filename) {

        int dotIndex = filename.lastIndexOf(".");

        if (dotIndex == -1) {
            return filename;
        }

        return filename.substring(0, dotIndex);
    }

    /**
     * @brief Compare two images
     *
     * @param first :: First image
     * @param second :: Second image
     *
     * @return boolean :: True if images match
     */
    private static boolean imagesMatch(ImageData first, ImageData second) {

        if (first.width != second.width) {
            return false;
        }

        if (first.height != second.height) {
            return false;
        }

        if (first.pixels.length != second.pixels.length) {
            return false;
        }

        for (int i = 0; i < first.pixels.length; i++) {

            if (first.pixels[i] != second.pixels[i]) {
                return false;
            }
        }

        return true;
    }
}


