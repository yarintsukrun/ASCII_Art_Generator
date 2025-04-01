package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static ascii_art.Constants.*;

/**
 * A class to represent and manipulate an image using a 2D pixel array.
 * Provides functionality for loading an image, padding it to a power of 2,
 * and dividing it into smaller sub-images.
 */
public class Image {

    private Color[][] pixelArray;
    private int width;
    private int height;


    /**
     * Constructs an Image object from a 2D array of colors and specified width and height.
     *
     * @param pixelArray a 2D array of Color objects representing the image's pixels.
     * @param width      the width of the image.
     * @param height     the height of the image.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs an Image object by loading an image from the given filename.
     *
     * @param filename the path to the image file.
     * @throws IOException if the file cannot be read.
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();

        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j] = new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Returns the width of the image.
     *
     * @return the width in pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the image.
     *
     * @return the height in pixels.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the color of the pixel at the specified coordinates.
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return the Color of the specified pixel.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Saves the current image to a file.
     * @param filename the path to save the image file (e.g., "output.png").
     * @throws IOException if an error occurs during file saving.
     */
    public void saveImage(String filename) throws IOException {
        // Create a BufferedImage object
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Populate the BufferedImage with pixel data
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                bufferedImage.setRGB(j, i, pixelArray[i][j].getRGB());
            }
        }

        // Extract file extension from the filename
        String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);

        // Save the image to the specified file
        File outputFile = new File(filename);
        ImageIO.write(bufferedImage, fileExtension, outputFile);
    }
    /**
     * Pads the image to the nearest power of 2 for both width and height.
     * The padded area is filled with white pixels (RGB: 255, 255, 255).
     */
    public void padImage() {
        int newWidth = nextPowerOfTwo(width);
        int newHeight = nextPowerOfTwo(height);

        if (newWidth == width && newHeight == height) {
            return; // Already a power of 2, no padding needed
        }

        Color[][] paddedArray = new Color[newHeight][newWidth];

        // Fill the padded array with white pixels
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                paddedArray[i][j] = new Color((int)RGB_CONSTANT, (int)RGB_CONSTANT, (int)RGB_CONSTANT); // Default white padding
            }
        }

        int yOffset = (newHeight - height) / 2; // Vertical offset
        int xOffset = (newWidth - width) / 2;  // Horizontal offset

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                paddedArray[i + yOffset][j + xOffset] = pixelArray[i][j];
            }
        }

        this.pixelArray = paddedArray;
        this.width = newWidth;
        this.height = newHeight;
    }

    /**
     * Divides the image into smaller sub-images of a specified size.
     * Each sub-image is a square of size subImageSize x subImageSize.
     * Assumes the image dimensions are multiples of subImageSize.
     *
     * @param subImageSize the size of each sub-image (in pixels).
     * @return a 3D array of Color objects representing the sub-images.
     */
    public Color[][][] divideIntoSubImages(int subImageSize) {
        int numRows = height / subImageSize;
        int numCols = width / subImageSize;

        Color[][][] subImages = new Color[numRows * numCols][subImageSize][subImageSize];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Color[][] subImage = new Color[subImageSize][subImageSize];

                for (int i = 0; i < subImageSize; i++) {
                    for (int j = 0; j < subImageSize; j++) {
                        subImage[i][j] = pixelArray[row * subImageSize + i][col * subImageSize + j];
                    }
                }
                subImages[row * numCols + col] = subImage;
            }
        }
        return subImages;
    }

    /**
     * Helper method to compute the next power of 2 for a given number.
     *
     * @param n the input number.
     * @return the smallest power of 2 greater than or equal to n.
     */
    private int nextPowerOfTwo(int n) {
        int power = 1;
        while (power < n) {
            power *= 2;
        }
        return power;
    }
}
