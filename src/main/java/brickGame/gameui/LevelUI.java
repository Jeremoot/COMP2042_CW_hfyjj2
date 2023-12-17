package main.java.brickGame.gameui;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Responsible for displaying and updating the current level in the user interface for the Brick Breaker game.
 * This class observes changes in the game data and updates the level display accordingly.
 */
public class LevelUI implements GameStateObserver {

    private Label levelUI; // Label to display the current level
    private GameData gameData; // Game data to observe and get the level information

    /**
     * Creates a new instance of LevelUI.
     *
     * @param gameData The game data to observe.
     */
    public LevelUI(GameData gameData) {
        this.gameData = gameData;
        this.gameData.registerObserver(this);
        this.levelUI = new Label("Level " + gameData.getLevel());
    }

    /**
     * Gets the label used for displaying the level.
     *
     * @return The label for the level display.
     */
    public Label getLevelUI() {
        return this.levelUI;
    }

    @Override
    public void update(int score, int heart, int level) {
        // Update the level display when there is a change in the game state
        Platform.runLater(() -> this.levelUI.setText("Level " + level));
    }

    @Override
    public void updateScore(int score) {
        // Not used in this implementation
    }

    @Override
    public void updateHeart(int heart) {
        // Not used in this implementation
    }

    @Override
    public void updateLevel(int level) {
        // Update the level display when there is a change in the level
        System.out.println("Level " + level);
        Platform.runLater(() -> this.levelUI.setText("Level " + level));
    }
}
