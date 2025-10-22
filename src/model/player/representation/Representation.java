package model.player.representation;

public class Representation {
    private final String representation;
    private final String highlight;

    public Representation(String representation, String highlight) {
        this.representation = representation;
        this.highlight = highlight;
    }

    public String render(boolean highlighted) {
        return highlighted ? highlight : representation;
    }
}
