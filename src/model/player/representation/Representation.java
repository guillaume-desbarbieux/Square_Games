package model.player.representation;

/**
 * The Representation class models a visual component that can have two states -
 * standard representation and highlighted representation. It is designed to encapsulate
 * the visual formatting logic and provide a unified way to render its state based
 * on the given context.
 */
public class Representation {
    private final String representation;
    private final String highlight;

    /**
     * Constructs a Representation instance with the specified representation and highlight strings.
     * The representation defines the standard visual format, while the highlight defines the
     * visual format to be used when the instance is rendered in a highlighted state.
     *
     * @param representation the string representing the standard visual format
     * @param highlight the string representing the visual format for the highlighted state
     */
    public Representation(String representation, String highlight) {
        this.representation = representation;
        this.highlight = highlight;
    }

    /**
     * Renders the visual representation of the object based on its highlighted state.
     * If the object is highlighted, the highlighted representation is returned.
     * Otherwise, the standard representation is returned.
     *
     * @param highlighted a boolean indicating whether the highlighted representation should be used
     * @return a string containing the visual representation, either highlighted or standard
     */
    public String render(boolean highlighted) {
        return highlighted ? highlight : representation;
    }
}
