package Image;

import Image.Image.ImageData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @brief Handles bitmap image loading and saving
 */
public class BitmapImage {

    /**
     * @brief Read bitmap file into image data
     *
     * @param filename :: Bitmap filename
     *
     * @return ImageData :: Loaded image
     */
    public static ImageData readBitmap(String filename) {

        try {

            BufferedImage image = ImageIO.read(new File(filename));

            if (image == null) {
                System.out.println("Error reading bitmap");
                return null;
            }

            int width = image.getWidth();
            int height = image.getHeight();

            
            int[] pixels = new int[width * height];

            int index = 0;

            for (int y = 0; y < height; y++) {

                for (int x = 0; x < width; x++) {

                    int rgb = image.getRGB(x, y);


                    //Store packed RGB data, not just one colour channel
                    pixels[index] = rgb & 0x00FFFFFF;
                    index++;
                }
            }

            return new ImageData(width, height, pixels);

        } catch (Exception e) {


            System.out.println("Error reading bitmap");
            return null;
        }
    }

    /**
     * @brief Write image data to bitmap
     *
     * @param image :: Image data
     * @param filename :: Output filename
     *
     * @return void :: None
     */
    public static void writeBitmap(
            ImageData image,

            String filename) {

        try {

            BufferedImage bitmap = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB);

            int index = 0;

            for (int y = 0; y < image.height; y++) {

                for (int x = 0; x < image.width; x++) {

                    //Use the packed RGB value directly
                    int rgb = image.pixels[index] & 0x00FFFFFF;

                    bitmap.setRGB(x, y, rgb);


                    index++;
                }
            }

            ImageIO.write(bitmap, "bmp", new File(filename));

        } catch (Exception e) {

            System.out.println("Error writing bitmap");
        }
    }
}



