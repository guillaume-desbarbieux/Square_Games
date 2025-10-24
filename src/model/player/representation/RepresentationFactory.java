package model.player.representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The RepresentationFactory class is responsible for generating Representation objects
 * using customizable color and symbol configurations. It provides functionality
 * to create individual or multiple representations with random visual attributes.
 */
public class RepresentationFactory {
    private final List<Color> colors;
    private final Color resetColor;
    private final List<Character> symbols;
    private final Random random;
    private final Character highlight;

    /**
     * Constructs a RepresentationFactory instance with customizable configurations for colors and symbols.
     * The factory is used to generate representations with random visual attributes based on the provided
     * lists of colors and symbols.
     *
     * @param colors a list of Color instances to be used for representation formatting
     * @param symbols a list of characters to be used as symbols for the representations
     */
    public RepresentationFactory(List<Color> colors, List<Character> symbols) {
        this.symbols = new ArrayList<>(symbols);
        this.colors = new ArrayList<>(colors);
        this.resetColor = Color.RESET;
        this.random = new Random();
        this.highlight = '✪';
    }

    /**
     * Creates a new Representation object using the provided color, symbol, and highlight.
     * The Representation object encapsulates the standard visual format and a highlighted format.
     *
     * @param color the color to be applied to the representation and highlight
     * @param symbol the character to be used in the standard representation
     * @param highlight the character to be used in the highlighted representation
     * @return a Representation object encapsulating the formatted representation and highlight
     */
    public Representation getRepresentation(Color color, Character symbol, Character highlight) {
        return new Representation("" + color + symbol + resetColor, "" + color + highlight + resetColor);
    }

    /**
     * Generates a list of Representation objects based on the specified quantity.
     * The method randomizes the order of colors and symbols, and combines them with
     * a predefined highlight character to create unique representations.
     *
     * @param quantity the number of Representation objects to generate
     * @return a list containing the generated Representation objects
     */
    public List<Representation> getRepresentations(int quantity) {
        List<Representation> representations = new ArrayList<>();
        Collections.shuffle(colors);
        Collections.shuffle(symbols);
        for (int i = 0; i < quantity; i++) {
            representations.add(getRepresentation(colors.get(i % colors.size()), symbols.get(i % symbols.size()), highlight));
        }
        return representations;
    }
}
