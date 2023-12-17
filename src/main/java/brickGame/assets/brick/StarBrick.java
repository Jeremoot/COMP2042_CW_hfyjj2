package main.java.brickGame.assets.brick;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * Represents a star-themed brick in the game.
 * This class extends the Brick class, specifically representing a brick with a star theme.
 */
public class StarBrick extends Brick {

    /**
     * Constructor for StarBrick.
     * Initializes a new instance of StarBrick with specified row, column, and color.
     *
     * @param row    The row index where the brick is located.
     * @param column The column index where the brick is located.
     * @param color  The color of the brick (though it will be overridden by the star image).
     */
    public StarBrick(int row, int column, Color color) {
        super(row, column, color);
    }

    /**
     * Draws the star brick.
     * Overrides the draw method from the Brick class to set a star-themed image pattern.
     */
    @Override
    public void draw() {
        // Load the star brick image
        Image image = new Image("file:src/main/java/resources/brick/starbrick.jpg");

        // Create an image pattern with the loaded image
        ImagePattern pattern = new ImagePattern(image);

        // Set the brick block fill with the star image pattern
        setBrickBlockFill(pattern);
    }
}
