package model.player.representation;

import java.util.List;

/**
 * Enum representing a set of terminal color codes and operations for their management.
 * Each color has an associated ANSI escape code that can be used for color formatting
 * in terminal outputs.
 */
public enum Color {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    /**
     * The ANSI escape code string that represents the terminal color associated with
     * the specific instance of the Color enum. This value is used to format terminal
     * outputs with the corresponding color.
     */
    private final String code;

    /**
     * Constructs a Color instance with the specified ANSI escape code.
     *
     * @param code the ANSI escape code representing the color
     */
    Color(String code) {
        this.code = code;
    }

    /**
     * Retrieves a list of all defined colors in the Color enum.
     *
     * @return a list containing all constants of the Color enum.
     */
    public static List<Color> getList() {
        return List.of(Color.values());
    }

    /**
     * Returns the ANSI escape code associated with the color represented by this enum constant.
     *
     * @return the ANSI escape code as a string for the current color
     */
    @Override
    public String toString() {
        return code;
    }
}
