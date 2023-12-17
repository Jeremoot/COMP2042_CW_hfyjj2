package main.java.brickGame.gameui;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Manages the display and updates of the game score in the user interface for the Brick Breaker game.
 * Observes changes in the game data and updates the score display accordingly.
 */
public class ScoreUI implements GameStateObserver {

    private Label scoreUI; // Label for displaying the current score
    private GameData gameData; // Game data to observe for score updates

    /**
     * Constructs a ScoreUI object.
     * Initializes the score display and registers itself as an observer of game data.
     *
     * @param gameData The GameData object to observe for score updates.
     */
    public ScoreUI(GameData gameData) {
        this.gameData = gameData;
        this.gameData.registerObserver(this);
        this.scoreUI = new Label("Score 0");
        this.scoreUI.setTranslateX(220);
    }

    /**
     * Retrieves the label used for displaying the score.
     *
     * @return The label for the score display.
     */
    public Label getScoreUI() {
        return this.scoreUI;
    }

    @Override
    public void update(int score, int heart, int level) {
        // Update the score display when there is a change in the game state
        Platform.runLater(() -> this.scoreUI.setText("Score " + score));
    }

    @Override
    public void updateScore(int score) {
        // Update the score display specifically
        System.out.println("New Score " + score);
        Platform.runLater(() -> this.scoreUI.setText("Score " + score));
    }

    @Override
    public void updateHeart(int heart) {
        // Not used in this implementation
    }

    @Override
    public void updateLevel(int level) {
        // Not used in this implementation
    }
}
