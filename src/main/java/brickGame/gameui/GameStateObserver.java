package main.java.brickGame.gameui;

/**
 * Interface for observing changes in various aspects of the Brick Breaker game state.
 * Implementers of this interface can be notified about updates to the score, heart count, and level.
 */
public interface GameStateObserver {

    /**
     * Called when there are changes in multiple aspects of the game state.
     * This method is used to update the score, heart count, and level simultaneously.
     *
     * @param score the updated score
     * @param heart the updated heart count
     * @param level the updated level
     */
    void update(int score, int heart, int level);

    /**
     * Called when there is a change in the score.
     * This method is used to update the score in the game.
     *
     * @param score the updated score
     */
    void updateScore(int score);

    /**
     * Called when there is a change in the heart count.
     * This method is used to update the heart count in the game.
     *
     * @param heart the updated heart count
     */
    void updateHeart(int heart);

    /**
     * Called when there is a change in the level.
     * This method is used to update the level in the game.
     *
     * @param level the updated level
     */
    void updateLevel(int level);
}
