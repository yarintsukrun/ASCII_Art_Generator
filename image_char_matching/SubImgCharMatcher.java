package image_char_matching;
import ascii_art.ROUND;
import java.util.*;
import static ascii_art.Constants.*;


/**
 * This class matches sub-image brightness values to corresponding characters
 * from a given character set. It calculates brightness levels for characters,
 * normalizes them, and provides efficient methods for retrieving the closest
 * matching character for a given brightness value.
 *
 * <p>
 * The class supports various rounding modes (defined in the {@link ROUND} enum)
 * to control how brightness values are matched.
 */
public class SubImgCharMatcher {

    /**
     * The minimum brightness value found in the character set.
     */
    private double minBrightness = Double.MAX_VALUE;

    /**
     * The maximum brightness value found in the character set.
     */
    private double maxBrightness = Double.MIN_VALUE;

    /**
     * The rounding mode used for matching brightness values.
     */
    private ROUND round;

    /**
     * A mapping between normalized brightness values and corresponding characters.
     * Each brightness value may map to multiple characters stored in a sorted set.
     */
    private TreeMap<Double, TreeSet<Character>> charBrightnessMap;

    /**
     * A set of currently available characters, maintained for quick access and display.
     */
    private final TreeSet<Character> currentChars;

    /**
     * Constructs a SubImgCharMatcher instance with the given character set.
     *
     * @param charSet the set of characters to use for matching.
     */
    public SubImgCharMatcher(char[] charSet) {
        this.currentChars = new TreeSet<>();
        this.round = ROUND.ABS;
        this.charBrightnessMap = new TreeMap<>();

        for (char c : charSet) {
            currentChars.add(c);
            double rawBrightness = calculateSingleCharBrightness(c);
            updateMinAndMaxValues(rawBrightness);

            charBrightnessMap
                    .computeIfAbsent(rawBrightness, _ -> new TreeSet<>())
                    .add(c);
        }
        reNormalizeBrightness();
    }

    /**
     * Sets the rounding mode used for brightness matching.
     *
     * @param round the rounding mode to set.
     */
    public void setRound(ROUND round) {
        this.round = round;
    }

    /**
     * Finds the character whose brightness is closest to the given brightness value,
     * based on the current rounding mode.
     *
     * @param brightness the brightness value to match.
     * @return the character whose brightness is closest to the input brightness.
     */
    public char getCharByImageBrightness(double brightness) {
        if (charBrightnessMap.isEmpty()){
            throw new RuntimeException(TREE_IS_EMPTY);
        }

        else if (charBrightnessMap.size() == 1){
            return charBrightnessMap.firstEntry().getValue().first();
        }

        // If we have a perfect match
        else if (charBrightnessMap.get(brightness) != null){
            return charBrightnessMap.get(brightness).first();
        }

        // If we are out of search boundaries
        else if (charBrightnessMap.firstKey() > brightness || charBrightnessMap.lastKey() < brightness){
            throw new IllegalArgumentException(SEARCH_OUT_OF_BOUND);
        }

        // We don't have a perfect match
            switch (round) {
                case ABS:
                    Map.Entry<Double, TreeSet<Character>> higher =
                            charBrightnessMap.higherEntry(brightness);
                    Map.Entry<Double, TreeSet<Character>> lower =
                            charBrightnessMap.lowerEntry(brightness);
                    if (higher.getKey() - brightness > brightness - lower.getKey()){
                        return lower.getValue().first();
                    }
                    else {
                        return higher.getValue().first();
                    }
                case UP:
                    return charBrightnessMap.higherEntry(brightness).getValue().first();
                case DOWN:
                    return charBrightnessMap.lowerEntry(brightness).getValue().first();
                default:
                    throw new IllegalArgumentException();
            }
        }


    /**
     * Adds a character to the mapping, calculating its brightness and updating
     * the normalization if necessary.
     *
     * @param c the character to add.
     */
    public void addChar(char c) {
        if (!currentChars.contains(c)) {
            currentChars.add(c);
            double rawBrightness = calculateSingleCharBrightness(c);
            boolean normalizationNeeded = updateMinAndMaxValues(rawBrightness);
            double normalizedBrightness = (rawBrightness - minBrightness) / (maxBrightness - minBrightness);
            putCharInExistingSetOrNewOne(c,normalizedBrightness, this.charBrightnessMap);
            if (normalizationNeeded) {
                reNormalizeBrightness();
            }
        }
    }

    /**
     * Removes a character from the mapping and updates the normalization if needed.
     *
     * @param c the character to remove.
     */
    public void removeChar(char c) {

        if (currentChars.contains(c) && !charBrightnessMap.isEmpty()) {
            // Remove the character from currentChars
            currentChars.remove(c);

            // Try to remove the character from the last entry (if it exists)
            if (charBrightnessMap.lastEntry() != null &&
                    charBrightnessMap.lastEntry().getValue().contains(c)) {
                charBrightnessMap.lastEntry().getValue().remove(c);
                // If the value is empty after removal, remove the entry
                if (charBrightnessMap.lastEntry().getValue().isEmpty()) {
                    charBrightnessMap.remove(charBrightnessMap.lastEntry().getKey());
                    reNormalizeBrightness();
                }
            }
            // Try to remove the character from the first entry (if it exists)
            else if (charBrightnessMap.firstEntry() != null &&
                    charBrightnessMap.firstEntry().getValue().contains(c)) {
                charBrightnessMap.firstEntry().getValue().remove(c);
                // If the value is empty after removal, remove the entry
                if (charBrightnessMap.firstEntry().getValue().isEmpty()) {
                    charBrightnessMap.remove(charBrightnessMap.firstEntry().getKey());
                    reNormalizeBrightness();
                }
            }
            // The char is not at the edges, calc and find him
            else {
                double rawBrightness = calculateSingleCharBrightness(c);
                double normalizedBrightness =
                        (rawBrightness - minBrightness)/(maxBrightness - minBrightness);

                TreeSet<Character> charSet = charBrightnessMap.get(normalizedBrightness);
                if (charSet != null) {
                    charSet.remove(c);
                    if (charSet.isEmpty()) {
                        charBrightnessMap.remove(normalizedBrightness);
                    }
                }
                if (updateMinAndMaxValues(rawBrightness)) {
                    reNormalizeBrightness();
                }
            }
        }
    }



    /**
     * Prints the current set of characters managed by this instance.
     */
    public void printCurrentCharset() {
        for (Character c : currentChars) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * Returns the size of the current character set.
     *
     * @return the number of characters in the set.
     */
    public int getSetSize() {
        return currentChars.size();
    }

    /**
     * Recomputes and normalizes brightness values based on the updated min and max values.
     */
    private void reNormalizeBrightness() {
        minBrightness = Double.MAX_VALUE;
        maxBrightness = Double.MIN_VALUE;

        // Find the minimum and maximum brightness values
        for (Map.Entry<Double, TreeSet<Character>> entry : charBrightnessMap.entrySet()) {
            for(char c : entry.getValue()){
                double rawBrightness = calculateSingleCharBrightness(c);
                if (rawBrightness < minBrightness) minBrightness = rawBrightness;
                if (rawBrightness > maxBrightness) maxBrightness = rawBrightness;
            }
        }

        TreeMap<Double, TreeSet<Character>> newTree = new TreeMap<>();

        // Normalize brightness values and store in the new map
        for (Map.Entry<Double, TreeSet<Character>> entry : charBrightnessMap.entrySet()) {
            for (char c : entry.getValue()) {
                double normalizedBrightness =
                        (calculateSingleCharBrightness(c)
                                - minBrightness) / (maxBrightness - minBrightness);
                putCharInExistingSetOrNewOne(c, normalizedBrightness, newTree);
            }
        }
        charBrightnessMap = newTree;
    }

    /**
     * Calculates the brightness of a single character by analyzing its binary representation.
     *
     * @param character the character whose brightness is to be calculated.
     * @return a brightness value between 0 and 1.
     */
    private double calculateSingleCharBrightness(Character character) {
        boolean[][] charArray = CharConverter.convertToBoolArray(character);
        double totalPixels = DEFAULT_BOOL_ARRAY_SIZE * DEFAULT_BOOL_ARRAY_SIZE;
        double whitePixels = 0;

        for (boolean[] row : charArray) {
            for (boolean pixel : row) {
                if (pixel) {
                    whitePixels++;
                }
            }
        }
        return whitePixels / totalPixels;
    }

    /**
     * Updates the minimum and maximum brightness values.
     *
     * @param rawBrightness the brightness value to check.
     * @return true if the min or max values were updated, false otherwise.
     */
    private boolean updateMinAndMaxValues(double rawBrightness) {
        boolean updated = false;
        if (rawBrightness < minBrightness) {
            minBrightness = rawBrightness;
            updated = true;
        }
        if (rawBrightness > maxBrightness) {
            maxBrightness = rawBrightness;
            updated = true;
        }
        return updated;
    }

    private void putCharInExistingSetOrNewOne(char c, double normalizedBrightness, TreeMap<Double
            , TreeSet<Character>> treeMap ){
        if (treeMap.containsKey(normalizedBrightness)){
            treeMap.get(normalizedBrightness).add(c);
        }
        else {
            TreeSet<Character> treeSet = new TreeSet<>();
            treeSet.add(c);
            treeMap.put(normalizedBrightness,treeSet);
        }
    }
}
