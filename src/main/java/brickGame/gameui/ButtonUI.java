package main.java.brickGame.gameui;

import main.java.brickGame.Main;
import javafx.scene.control.Button;

/**
 * The ButtonUI class manages the user interface buttons in the Brick Breaker game.
 * It includes buttons for starting a new game and loading a saved game.
 */
public class ButtonUI {
    private Main main;

    // Button to load a saved game
    private Button loadButton;

    // Button to start a new game
    private Button newGameButton;

    /**
     * Constructor for creating a ButtonUI object.
     * Initializes buttons for loading a game and starting a new game.
     *
     * @param main The main application instance.
     */
    public ButtonUI(Main main) {
        this.main = main;

        this.loadButton = new Button("Load Game");
        this.loadButton.setTranslateX(200);
        this.loadButton.setTranslateY(340);
        this.loadButton.setOnAction(event -> handleLoadButtonClick());

        this.newGameButton = new Button("Start New Game");
        this.newGameButton.setTranslateX(200);
        this.newGameButton.setTranslateY(340);
        this.newGameButton.setOnAction(event -> handleNewGameButtonClick());
    }

    /**
     * Handles the action when the "Load Game" button is clicked.
     * Initiates game engine and hides the UI buttons.
     */
    public void handleLoadButtonClick() {
        main.initGameEngine();
        this.loadButton.setVisible(false);
        this.newGameButton.setVisible(false);
    }

    /**
     * Handles the action when the "Start New Game" button is clicked.
     * Initiates game engine and hides the UI buttons.
     */
    private void handleNewGameButtonClick() {
        main.initGameEngine();
        this.loadButton.setVisible(false);
        this.newGameButton.setVisible(false);
    }

    /**
     * Retrieves the "Load Game" button.
     *
     * @return The "Load Game" button.
     */
    public Button getLoadButton() {
        return loadButton;
    }

    /**
     * Retrieves the "Start New Game" button.
     *
     * @return The "Start New Game" button.
     */
    public Button getNewGameButton() {
        return newGameButton;
    }

    /**
     * Sets the visibility of the "Load Game" button.
     *
     * @param visible True to make the button visible, false to hide it.
     */
    public void setLoadButtonVisibility(boolean visible) {
        this.loadButton.setVisible(visible);
    }

    /**
     * Sets the visibility of the "Start New Game" button.
     *
     * @param visible True to make the button visible, false to hide it.
     */
    public void setNewGameButtonVisibility(boolean visible) {
        this.newGameButton.setVisible(visible);
    }
}
