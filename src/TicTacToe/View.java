package TicTacToe;

public class View {
    private boolean maximize = false;

    public View() {
    }

    public void setMaximize(boolean maximize) {
        this.maximize = maximize;
    }

    public void displayMessage(String message) {

        System.out.println(message);
    }

    public void displayBoard(Board board) {
        int indexWidth = String.valueOf(board.height()).length();
        String horizontalSeparator = " ";
        String verticalSeparator = "";
        int cellWidth = horizontalSeparator.length() + 1;

        if (maximize) {
            horizontalSeparator = " | ";
            cellWidth = horizontalSeparator.length() + 1;
            verticalSeparator = " ".repeat(indexWidth + cellWidth/2) + "-".repeat(cellWidth * board.width()) + "\n";
        }

        StringBuilder message = new StringBuilder();
        message.append(" ".repeat(indexWidth));

        for (int j = 0; j < board.width(); j++) {
            message.append(String.format("%" + cellWidth + "d", j + 1));
        }
        message.append("\n").append(verticalSeparator);

        for (int i = 0; i < board.height(); i++) {
            message.append(String.format("%" + indexWidth + "d", i + 1)).append(horizontalSeparator);
            for (int j = 0; j < board.width(); j++) {
                message.append(board.getCell(i, j).getRepresentation()).append(horizontalSeparator);
            }
            message.append("\n").append(verticalSeparator);
        }
        displayMessage(message.toString());
    }

    public void displayTitle(String title) {
        displayMessage("-".repeat(50));
        displayMessage(title);
        displayMessage("-".repeat(50));
    }
}
