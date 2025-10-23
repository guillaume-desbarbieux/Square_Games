import controller.Square_Games;

/**
 * The Main class serves as the entry point for the application.
 * It is responsible for invoking the start process of the Square_Games class,
 * which manages the game's interactive command-line interface and game options.
 * <p>
 * This class contains the static `main` method, which initializes and launches
 * the Square_Games application, allowing users to select and play various board games
 * or exit the application.
 */
public class Main {
    /**
     * The main method serves as the entry point for the Square_Games application.
     * This method creates an instance of the Square_Games class and starts the
     * game selection and management process.
     *
     * @param args Command-line arguments passed to the application. These are
     *             not used in this implementation.
     */
    public static void main(String[] args) {
        new Square_Games().start();
    }
}