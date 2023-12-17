package main.java.brickGame.assets.brick;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * Represents a chocolate-themed brick in the game.
 * This class extends the Brick class, specifically representing a chocolate brick.
 */
public class ChocoBrick extends Brick {

    /**
     * Constructor for ChocoBrick.
     * Initializes a new instance of ChocoBrick with specified row, column, and color.
     *
     * @param row    The row index where the brick is located.
     * @param column The column index where the brick is located.
     * @param color  The color of the brick (though it will be overridden by the chocolate image).
     */
    public ChocoBrick(int row, int column, Color color) {
        super(row, column, color);
    }

    /**
     * Draws the chocolate brick.
     * Overrides the draw method from Brick class to set a chocolate-themed image pattern.
     */
    @Override
    public void draw() {
        // Load the chocolate brick image
        Image image = new Image("file:src/main/java/resources/brick/chocobrick.jpg");

        // Create an image pattern with the loaded image
        ImagePattern pattern = new ImagePattern(image);

        // Set the brick block fill with the chocolate image pattern
        setBrickBlockFill(pattern);
    }
}
