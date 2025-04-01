package ascii_art;

/**
 * The ROUND enumeration defines rounding modes for matching brightness values.
 * It specifies how to handle the difference between a given brightness and
 * available brightness levels in the mapping.
 */
public enum ROUND {
    /**
     * Rounding up to the nearest brightness value greater than or equal to the input.
     */
    UP,

    /**
     * Rounding down to the nearest brightness value less than or equal to the input.
     */
    DOWN,

    /**
     * Rounding to the brightness value with the smallest absolute difference from the input.
     */
    ABS
}
