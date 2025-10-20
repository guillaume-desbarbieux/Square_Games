package player.factory;

import player.Representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RepresentationFactory {
    private final List<Color> colors;
    private final Color resetColor;
    private final List<Character> symbols;
    private final Random random;

    public RepresentationFactory(List<Color> colors, List<Character> symbols) {
        this.symbols = new ArrayList<>(symbols);
        this.colors = new ArrayList<>(colors);
        this.resetColor = Color.RESET;
        this.random = new Random();
    }

    public Representation getRepresentation() {
        Color color = this.colors.get(random.nextInt(colors.size()));
        Character symbol = this.symbols.get(random.nextInt(symbols.size()));
        return getRepresentation(color, symbol);
    }

    public Representation getRepresentation(Color color, Character symbol) {
        return new Representation("" + color + symbol + resetColor);
    }

    public List<Representation> getRepresentations(int quantity) {
        List<Representation> representations = new ArrayList<>();
        Collections.shuffle(colors);
        Collections.shuffle(symbols);
        for (int i = 0; i < quantity; i++) {
            representations.add(getRepresentation(colors.get(i % colors.size()), symbols.get(i % symbols.size())));
        }
        return representations;
    }
}
