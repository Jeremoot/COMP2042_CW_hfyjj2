package main.java.brickGame.assets.brick;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * Represents a heart-themed brick in the game.
 * This class extends the Brick class, specifically representing a brick with a heart theme.
 */
public class HeartBrick extends Brick {

    /**
     * Constructor for HeartBrick.
     * Initializes a new instance of HeartBrick with specified row, column, and color.
     *
     * @param row    The row index where the brick is located.
     * @param column The column index where the brick is located.
     * @param color  The color of the brick (though it will be overridden by the heart image).
     */
    public HeartBrick(int row, int column, Color color) {
        super(row, column, color);
    }

    /**
     * Draws the heart brick.
     * Overrides the draw method from Brick class to set a heart-themed image pattern.
     */
    @Override
    public void draw() {
        // Load the heart brick image
        Image image = new Image("file:src/main/java/resources/brick/heartbrick.jpg");

        // Create an image pattern with the loaded image
        ImagePattern pattern = new ImagePattern(image);

        // Set the brick block fill with the heart image pattern
        setBrickBlockFill(pattern);
    }
}
