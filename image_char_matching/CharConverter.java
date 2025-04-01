package image_char_matching;

import java.awt.*;
import java.awt.image.BufferedImage;
import static ascii_art.Constants.*;


/**
 * Inspired by, and partly copied from
 * https://github.com/korhner/asciimg/blob/95c7764a6abe0e893fae56b3b6b580e09e1de209/src/main/java/io/korhner/asciimg/image/AsciiImgCache.java
 * described in the blog:
 * https://dzone.com/articles/ascii-art-generator-java
 * Adaptations made by Dan Nirel and again by Rachel Behar.
 * The class converts characters to a binary "image" (2D array of booleans).
 */
public class CharConverter {


    /**
     * Renders a given character, according to how it looks in the font specified in the
     * constructor, to a square black&white image (2D array of booleans),
     * whose dimension in pixels is specified.
     */
    public static boolean[][] convertToBoolArray(char c) {
        BufferedImage img = getBufferedImage(c, DEFAULT_FONT, DEFAULT_BOOL_ARRAY_SIZE);
        boolean[][] matrix = new boolean[DEFAULT_BOOL_ARRAY_SIZE][DEFAULT_BOOL_ARRAY_SIZE];
        for(int y = 0 ; y < DEFAULT_BOOL_ARRAY_SIZE ; y++) {
            for(int x = 0 ; x < DEFAULT_BOOL_ARRAY_SIZE ; x++) {
                matrix[y][x] = img.getRGB(x, y) == 0; //is the color black
            }
        }
        return matrix;
    }

    private static BufferedImage getBufferedImage(char c, String fontName, int pixelsPerRow) {
        String charStr = Character.toString(c);
        Font font = new Font(fontName, Font.PLAIN, pixelsPerRow);
        BufferedImage img = new BufferedImage(pixelsPerRow, pixelsPerRow, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setFont(font);
        int xOffset = (int)Math.round(pixelsPerRow *X_OFFSET_FACTOR);
        int yOffset = (int)Math.round(pixelsPerRow *Y_OFFSET_FACTOR);
        g.drawString(charStr, xOffset, yOffset);
        return img;
    }

}
