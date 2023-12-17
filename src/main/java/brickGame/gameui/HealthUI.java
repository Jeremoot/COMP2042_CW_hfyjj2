package main.java.brickGame.gameui;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Manages the display and updating of the heart count (health) in the user interface.
 * Implements the GameStateObserver interface to receive updates about changes in the heart count.
 */
public class HealthUI implements GameStateObserver {
    private Label healthUI; // Label for displaying the heart count
    private GameData gameData; // Reference to the game data
    private int sceneWidth = 500; // Width of the scene to position the label

    private int heartCount; // Stores the previous heart count for comparison

    /**
     * Constructs a HealthUI object.
     * Initializes the heart count display and registers itself as an observer of game data.
     *
     * @param gameData The GameData instance to observe.
     */
    public HealthUI(GameData gameData) {
        this.gameData = gameData;
        this.gameData.registerObserver(this);
        this.healthUI = new Label("Health " + gameData.getHeart());
        this.healthUI.setTranslateX(sceneWidth - 70);

        // Initialize previous heart count
        this.heartCount = gameData.getHeart();
    }

    /**
     * Retrieves the label used to display the heart count.
     *
     * @return The Label for heart count display.
     */
    public Label getHeartUI() {
        return this.healthUI;
    }

    @Override
    public void update(int score, int heart, int level) {
        Platform.runLater(() -> this.healthUI.setText("Health " + heart));
    }

    @Override
    public void updateScore(int score) {
        // Not used in this implementation
    }

    @Override
    public void updateHeart(int heart) {
        Platform.runLater(() -> {
            if (heart > heartCount) {
                System.out.println("Heart Earned!");
            } else if (heart < heartCount) {
                System.out.println("Heart Lost!");
            }
            this.healthUI.setText("Health " + heart);
            heartCount = heart;
        });
    }

    @Override
    public void updateLevel(int level) {
        // Not used in this implementation
    }
}
