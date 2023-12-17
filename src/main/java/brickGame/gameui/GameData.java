package main.java.brickGame.gameui;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game data including score, heart count, and level in the Brick Breaker game.
 * This class manages game-related data and notifies observers when the data changes.
 */
public class GameData {

    private List<GameStateObserver> observers; // List of observers for game state changes
    private int score; // Current score in the game
    private int heart; // Current heart count in the game
    private int level; // Current level in the game

    /**
     * Constructs a new GameData object with initial values.
     */
    public GameData() {
        this.observers = new ArrayList<>();
        this.score = 0;
        this.heart = 3;
        this.level = 0;
    }

    /**
     * Registers an observer to receive updates about game data changes.
     *
     * @param observer The observer to register.
     */
    public void registerObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer The observer to remove.
     */
    public void removeObserver(GameStateObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers about changes in the game state.
     */
    public void notifyObservers() {
        for (GameStateObserver observer : observers) {
            observer.update(score, heart, level);
        }
    }

    /**
     * Notifies all registered observers about the current score.
     */
    public void notifyScoreObservers() {
        for (GameStateObserver observer : observers) {
            observer.updateScore(score);
        }
    }

    /**
     * Notifies all registered observers about the current heart count.
     */
    public void notifyHeartObservers() {
        for (GameStateObserver observer : observers) {
            observer.updateHeart(heart);
        }
    }

    /**
     * Notifies all registered observers about the current level.
     */
    public void notifyLevelObservers() {
        for (GameStateObserver observer : observers) {
            observer.updateLevel(level);
        }
    }

    /**
     * Sets the game data with the provided values and notifies observers.
     *
     * @param score The new score value.
     * @param heart The new heart count.
     * @param level The new level.
     */
    public void set(int score, int heart, int level) {
        this.score = score;
        this.heart = heart;
        this.level = level;
        notifyObservers();
    }

    /**
     * Sets the current score and notifies score observers.
     *
     * @param score The new score value.
     */
    public void setScore(int score) {
        this.score = score;
        notifyScoreObservers();
    }

    /**
     * Sets the current heart count and notifies heart observers.
     *
     * @param heart The new heart count.
     */
    public void setHeart(int heart) {
        this.heart = heart;
        notifyHeartObservers();
    }

    /**
     * Sets the current level and notifies level observers.
     *
     * @param level The new level.
     */
    public void setLevel(int level) {
        this.level = level;
        notifyLevelObservers();
    }

    /**
     * Gets the current score.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the current heart count.
     *
     * @return The current heart count.
     */
    public int getHeart() {
        return heart;
    }

    /**
     * Gets the current level.
     *
     * @return The current level.
     */
    public int getLevel() {
        return level;
    }
}
