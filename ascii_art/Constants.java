package ascii_art;

/**
 * A utility class that contains various constant values used across the ASCII Art application.
 * This class is final and cannot be subclassed.
 * It includes constants for character sets, color constants, resolution settings,
 * output formats, error messages, and more.
 * <p>
 * All fields in this class are public, static, and final, making them accessible globally and immutable.
 */
public final class Constants {

    /**
     * A default character set used for generating ASCII art from numeric characters.
     */
    public static final char[] charSet =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * Constants representing the contribution of each
     * RGB color channel to the perceived brightness
     * of a pixel (used in grayscale conversion).
     */
    public static final double RED_CONSTANT = 0.2126;
    /**
     * Constants representing the contribution of each
     * RGB color channel to the perceived brightness
     * of a pixel (used in grayscale conversion).
     */
    public static final double GREEN_CONSTANT = 0.7152;
    /**
     * Constants representing the contribution of each
     * RGB color channel to the perceived brightness
     * of a pixel (used in grayscale conversion).
     */
    public static final double BLUE_CONSTANT = 0.0722;
    /**
     * RGB CONST
     */
    public static final double RGB_CONSTANT = 255.0;

    /**
     * Factors used for offset adjustments in X direction.
     */
    public static final double X_OFFSET_FACTOR = 0.2;
    /**
     * Factors used for offset adjustments in Y direction.
     */
    public static final double Y_OFFSET_FACTOR = 0.75;

    /**
     * Default size for boolean arrays in the application.
     */
    public static final int DEFAULT_BOOL_ARRAY_SIZE = 16;

    /**
     * First in list
     */
    public static final int FIRST = 0;
    /**
     * sec in list
     */
    public static final int SECOND = 1;
    /**
     * third in list
     */
    public static final int THIRD = 2;
    /**
     * forth in list
     */
    public static final int FOURTH = 3;

    /**
     * Minimum size of lists used in the application.
     */
    public static final int MIN_SIZE_LIST = 2;

    /**
     * Multiplication factor for resolution scaling.
     */
    public static final int RES_MULTI = 2;

    /**
     * ASCII value range constants for printable characters.
     */
    public static final int ASCII_START = 32;
    /**
     * ASCII value range constants for printable characters.
     */
    public static final int ASCII_END = 127;

    /**
     * Default resolution for generating ASCII art.
     */
    public static final int DEFAULT_RESOLUTION = 2;

    /**
     * Command prompt string displayed to the user.
     */
    public static final String PROMPT = ">>> ";

    /**
     * Default font used in output.
     */
    public static final String DEFAULT_FONT = "Courier New";

    /**
     * Default output method (console).
     */
    public static final String DEFAULT_OUTPUT = "console";

    /**
     * Different types of output methods supported.
     */
    public static final String CONSOLE_OUTPUT = "console";
    /**
     * Different types of output methods supported.
     */
    public static final String HTML_OUTPUT = "html";

    /**
     * Command string for exiting the application.
     */
    public static final String EXIT = "exit";

    /**
     * Command strings for adding and removing elements.
     */
    public static final String CHARS = "chars";
    /**
     * Command strings for adding and removing elements.
     */
    public static final String ADD = "add";
    /**
     * Command strings for adding and removing elements.
     */
    public static final String REMOVE = "remove";

    /**
     * Command string for setting the resolution.
     */
    public static final String RES = "res";

    /**
     * Command string for setting the output method.
     */
    public static final String OUTPUT = "output";

    /**
     * Command string for generating ASCII art.
     */
    public static final String ASCIIART = "asciiart";

    /**
     * Command string for rounding operations.
     */
    public static final String ROUND_TXT = "round";

    /**
     * Rounding method options.
     */
    public static final String ROUND_DOWN = "down";
    /**
     * Rounding method options.
     */
    public static final String ROUND_UP = "up";
    /**
     * Rounding method options.
     */
    public static final String ROUND_ABS = "abs";

    /**
     * Space character.
     */
    public static final String SPACE = "space";

    /**
     * Command string for applying an operation to all elements.
     */
    public static final String ALL = "all";

    /**
     * Character used for hyphenating in the application.
     */
    public static final char hyphen = '-';

    /**
     * String indicating the resolution has been set to a new value.
     */
    public static final String RES_SET_TO = "Resolution set to ";

    /**
     * Error message for an invalid command.
     */
    public static final String INVALID_COMMAND_TRY_AGAIN =
            "Invalid command. Please try again.";

    /**
     * Error message for incorrect format in rounding operations.
     */
    public static final String ROUND_INCORRECT_FORMAT =

            "Did not change rounding method due to incorrect format.";

    /**
     * Error message for incorrect format when adding elements.
     */
    public static final String ADD_INCORRECT_FORMAT =
            "Did not add due to incorrect format.";

    /**
     * Error message for incorrect format when removing elements.
     */
    public static final String REMOVE_INCORRECT_FORMAT =
            "Did not remove due to incorrect format.";

    /**
     * Error message for incorrect format when changing resolution.
     */
    public static final String RES_INCORRECT_FORMAT =
            "Did not change resolution due to incorrect format.";

    /**
     * Error message for resolution exceeding defined boundaries.
     */
    public static final String RES_EXCEEDING_BOUNDARIES =
            "Did not change resolution due to exceeding boundaries.";

    /**
     * Error message for incorrect format when changing the output method.
     */
    public static final String OUTPUT_INCORRECT_FORMAT =
            "Did not change output method due to incorrect format.";

    /**
     * Error message when the charset is too small to perform the required operation.
     */
    public static final String ERROR_CHARSET_TOO_SMALL =
            "Did not execute. Charset is too small.";

    /**
     * Message indicating that the tree structure is empty.
     */
    public static final String TREE_IS_EMPTY =
            "Tree is empty";

    /**
     * Message indicating that a search operation is out of boundaries.
     */
    public static final String SEARCH_OUT_OF_BOUND =
            "Search is out of boundaries";

    /**
     * Default file name for output in HTML format.
     */
    public static final String FILE_NAME = "out.html";
}
