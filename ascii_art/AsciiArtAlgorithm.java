package ascii_art;

import image.Image;
import image_char_matching.SubImgCharMatcher;
import java.awt.*;
import java.util.HashSet;

import static ascii_art.Constants.*;


/**
 * Class that implements an algorithm to generate ASCII art from an image.
 * The algorithm processes an image, divides it into sub-images, calculates the brightness of each sub-image,
 * and maps the brightness values to characters using a specified character matcher.
 */
public class AsciiArtAlgorithm {
    private final Image image;
    private int resolution;
    private final SubImgCharMatcher matcher;
    private HashSet<Character> charSet;

    /**
     * Constructor for AsciiArtAlgorithm.
     *
     * @param image      The input image.
     * @param resolution The resolution for ASCII art.
     */
    public AsciiArtAlgorithm(Image image, int resolution,
                             SubImgCharMatcher matcher) {
        this.image = image;
        this.resolution = resolution;
        this.matcher = matcher;
    }



    /**
     * Executes the ASCII art algorithm.
     * @return 2D array representing ASCII art.
     */
    public char[][] run() {
        int subImageSize = image.getHeight() / resolution;
        Color[][][] subImages = image.divideIntoSubImages(subImageSize);

        int numRows = image.getHeight() / subImageSize;
        int numCols = image.getWidth() / subImageSize;
        char[][] asciiArt = new char[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                double brightness = calculateSubImageBrightness(subImages[i * numCols + j]);
                asciiArt[i][j] = matcher.getCharByImageBrightness(brightness);
            }
        }
        return asciiArt;
    }


    /**
     * calculate Sub Image Brightness
     * @param subImage
     * @return sub image brightness
     */
    private double calculateSubImageBrightness(Color[][] subImage) {
        double totalBrightness = 0;
        int totalPixels = subImage.length * subImage[FIRST].length;

        for (Color[] row : subImage) {
            for (Color pixel : row) {
                double gray = pixel.getRed() * RED_CONSTANT + pixel.getGreen() *
                        GREEN_CONSTANT + pixel.getBlue() * BLUE_CONSTANT;
                totalBrightness += gray / RGB_CONSTANT;
            }
        }

        return totalBrightness / totalPixels;
    }


    /**
     * getting the resolution
     * @return
     */
    public int getResolution(){
        return resolution;
    }

    /**
     * setting the resolution
     * @param round
     */
    public void setResolution(ROUND round){
        if (round.equals(ROUND.UP)){
            resolution *= RES_MULTI;
        }
        else {
            resolution /= RES_MULTI;
        }
    }

}
