package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;
import java.io.IOException;
import static ascii_art.Constants.*;


/**
 * Class for ASCII art shell interaction.
 */
public class Shell {

    private AsciiArtAlgorithm algorithm = null;
    private Image image;
    private SubImgCharMatcher matcher = null;
    private String outputMethod;

    /**
     * Constructs the Shell instance.
     */
    public Shell() {
        this.outputMethod = DEFAULT_OUTPUT;
    }

    /**
     * Runs the shell and processes user input commands.
     *
     * @param imageName Path to the input image.
     */
    public void run(String imageName) {
        try {
            image = new Image(imageName);
            image.padImage();
            this.matcher = new SubImgCharMatcher(charSet);
            this.algorithm = new AsciiArtAlgorithm(image, DEFAULT_RESOLUTION, matcher);

            while (true) {
                try {
                    System.out.print(PROMPT); // Show the prompt
                    String input = KeyboardInput.readLine();
                    String[] parts = input.split(" ");
                    String command = parts[FIRST].toLowerCase();

                    switch (command) {
                        case EXIT:
                            return;
                        case CHARS:
                            matcher.printCurrentCharset();
                            break;
                        case ADD:
                            handleAddCommand(parts);
                            break;
                        case REMOVE:
                            handleRemoveCommand(parts);
                            break;
                        case RES:
                            handleResolutionCommand(parts);
                            break;
                        case OUTPUT:
                            handleOutputCommand(parts);
                            break;
                        case ASCIIART:
                            runAsciiArtAlgorithm();
                            break;
                        case ROUND_TXT:
                            round(parts);
                            break;
                        default:
                            System.out.println(INVALID_COMMAND_TRY_AGAIN);
                    }
                } catch (IllegalArgumentException  e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void round(String[] parts) {
        if (parts.length != THIRD) {
            throw new IllegalArgumentException(ROUND_INCORRECT_FORMAT);
        } else if (parts[SECOND].equals(ROUND_ABS)) {
            matcher.setRound(ROUND.ABS);
        } else if (parts[SECOND].equals(ROUND_UP)) {
            matcher.setRound(ROUND.UP);
        } else if (parts[SECOND].equals(ROUND_DOWN)) {
            matcher.setRound(ROUND.DOWN);
        } else {
            throw new IllegalArgumentException(ROUND_INCORRECT_FORMAT);
        }
    }


    private void handleAddCommand(String[] parts) {
        if (parts.length != THIRD) {
            throw new IllegalArgumentException(ADD_INCORRECT_FORMAT);
        } else if (parts[SECOND].equals(ALL)) {
            for (int i = ASCII_START; i < ASCII_END; i++) {
                matcher.addChar((char) i);
            }
        } else if (parts[SECOND].equals(SPACE)) {
            matcher.addChar((char) ASCII_START);
        } else if (parts[SECOND].length() == FOURTH && parts[SECOND].charAt(SECOND) == hyphen) {
            int start = Math.min(parts[SECOND].charAt(FIRST), parts[SECOND].charAt(THIRD));
            int end = Math.max(parts[SECOND].charAt(FIRST), parts[SECOND].charAt(THIRD));
            for (int i = start; i <= end; i++) {
                matcher.addChar((char) i);
            }
        } else if (parts[SECOND].length() != SECOND) {
            throw new IllegalArgumentException(ADD_INCORRECT_FORMAT);
        } else {
            matcher.addChar(parts[SECOND].charAt(FIRST));
        }
    }

    private void handleRemoveCommand(String[] parts) {
        if (parts.length != THIRD) {
            throw new IllegalArgumentException(REMOVE_INCORRECT_FORMAT);
        }
        String command = parts[SECOND];

        if (command.equals(ALL)) {
            for (int i = ASCII_START; i < ASCII_END; i++) {
                matcher.removeChar((char) i);
            }
        } else if (command.equals(SPACE)) {
            matcher.removeChar((char) ASCII_START);
        } else if (command.length() == FOURTH && command.charAt(SECOND) == hyphen) {
            char start = (char) Math.min(command.charAt(FIRST), command.charAt(THIRD));
            char end = (char) Math.max(command.charAt(FIRST), command.charAt(THIRD));
            for (char c = start; c <= end; c++) {
                matcher.removeChar(c);
            }
        } else if (command.length() == SECOND) {
            matcher.removeChar(command.charAt(FIRST));
        } else {
            throw new IllegalArgumentException(REMOVE_INCORRECT_FORMAT);
        }
    }

    private void handleResolutionCommand(String[] parts) {
        int minCharsInRow = Math.max(SECOND, image.getWidth() / image.getHeight());
        int maxCharsInRow = image.getWidth();

        if (parts.length != THIRD || (!parts[SECOND].equals(ROUND_UP) && !parts[SECOND].equals(ROUND_DOWN))) {
            throw new IllegalArgumentException(RES_INCORRECT_FORMAT);
        }

        if (parts[SECOND].equals(ROUND_UP)) {
            if (algorithm.getResolution() * THIRD > maxCharsInRow) {
                throw new IllegalArgumentException(RES_EXCEEDING_BOUNDARIES);
            }
            algorithm.setResolution(ROUND.UP);
        } else {
            if (algorithm.getResolution() / THIRD < minCharsInRow) {
                throw new IllegalArgumentException(RES_EXCEEDING_BOUNDARIES);
            }
            algorithm.setResolution(ROUND.DOWN);
        }
        System.out.println(RES_SET_TO + algorithm.getResolution());
    }

    private void handleOutputCommand(String[] parts) {
        if (parts.length != THIRD || (!parts[SECOND].equals(CONSOLE_OUTPUT) &&
                !parts[SECOND].equals(HTML_OUTPUT))) {
            throw new IllegalArgumentException(OUTPUT_INCORRECT_FORMAT);
        }

        outputMethod = parts[SECOND];
    }

    private void runAsciiArtAlgorithm() {
        if (matcher.getSetSize() < THIRD) {
            throw new IllegalArgumentException(ERROR_CHARSET_TOO_SMALL);
        }

        char[][] asciiArt = algorithm.run();

        if (outputMethod.equals(CONSOLE_OUTPUT)) {
            new ConsoleAsciiOutput().out(asciiArt);
        } else if (outputMethod.equals(HTML_OUTPUT)) {
            new HtmlAsciiOutput(FILE_NAME, DEFAULT_FONT).out(asciiArt);
        } else {
            new ConsoleAsciiOutput().out(asciiArt);
        }
    }

    /**
     * Main entry point for the Shell application.
     * @param args Command-line arguments. Expects a single argument specifying the path to the image file.
     */
    public static void main(String[] args) {
        if (args.length == SECOND) {
            String imagePath = args[FIRST];
            Shell shell = new Shell();
            shell.run(imagePath);
        }
    }
}
