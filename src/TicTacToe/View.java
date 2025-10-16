package TicTacToe;

public class View {

    public View() {
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }


    public void displayBoard(Board board) {
        StringBuilder message = new StringBuilder();
        int indexWidth = String.valueOf(board.height()).length();
        int cellWidth = 3;

        String separator = "\n" + " ".repeat(indexWidth + 1) + "-".repeat((cellWidth + 1) * board.width() + 1) + "\n";

        message.append(" ".repeat(indexWidth + 2));

        for (int j = 0; j < board.width(); j++) {
            message.append(String.format("%" + cellWidth + "d ", j + 1));
        }
        message.append(separator);

        for (int i = 0; i < board.height(); i++) {
            message.append(String.format("%" + indexWidth + "d |", i + 1));
            for (int j = 0; j < board.width(); j++) {
                message.append(String.format("%" + cellWidth + "s", board.getCell(i, j).getRepresentation())).append("|");
            }
            message.append(separator);
        }
        displayMessage(message.toString());
    }

    public void displayTitle(String title) {
        displayMessage("-".repeat(50));
        displayMessage(title);
        displayMessage("-".repeat(50));
    }
}
