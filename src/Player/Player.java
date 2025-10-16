package Player;

import Board.TicTacToeBoard;
import Interact.InteractionUser;
import Interact.View;

public abstract class Player {
    private final int id;
    private final String representation;

    public Player(int id, char symbol, Color color) {
        this.id = id;
        this.representation = "" + color + symbol + Color.RESET;
    }

    public String getRepresentation() {
        return this.representation;
    }

    public int getId() {
        return this.id;
    }

    public abstract int[] getMove(InteractionUser interact, View view, TicTacToeBoard board);
}
