package main.java.brickGame.assets.brick;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import java.util.Random;

/**
 * Represents a normal brick in the game.
 * This class extends the Brick class, providing a variety of visual appearances for normal bricks.
 * Each NormalBrick object randomly selects one of several predefined images for its appearance.
 */
public class NormalBrick extends Brick {
    // Array of image paths for different appearances of normal bricks
    private static final String[] imagePaths = {
            "file:src/main/java/resources/brick/normalbrick1.png",
            "file:src/main/java/resources/brick/normalbrick2.png",
            "file:src/main/java/resources/brick/normalbrick3.png",
            "file:src/main/java/resources/brick/normalbrick4.png",
            "file:src/main/java/resources/brick/normalbrick5.png",
            "file:src/main/java/resources/brick/normalbrick6.png",
            "file:src/main/java/resources/brick/normalbrick7.png",
            "file:src/main/java/resources/brick/normalbrick8.png",
            "file:src/main/java/resources/brick/normalbrick9.png",
            "file:src/main/java/resources/brick/normalbrick10.png",
    };

    // Random number generator for selecting brick images
    private static final Random random = new Random();

    /**
     * Constructor for NormalBrick.
     * Initializes a new instance of NormalBrick with specified row, column, and color.
     *
     * @param row    The row index where the brick is located.
     * @param column The column index where the brick is located.
     * @param color  The color of the brick (used in the superclass).
     */
    public NormalBrick(int row, int column, Color color) {
        super(row, column, color);
    }

    /**
     * Draws the normal brick with a randomly selected image.
     * Overrides the draw method from the Brick class to set a random image pattern.
     */
    @Override
    public void draw() {
        ImagePattern randomBrickImage = getRandomBrickImage();
        setBrickBlockFill(randomBrickImage);
    }

    /**
     * Selects and returns a random image pattern for the brick.
     * This method chooses a random image from the predefined set.
     *
     * @return An ImagePattern object representing the selected image.
     */
    private ImagePattern getRandomBrickImage() {
        String selectedImagePath = imagePaths[random.nextInt(imagePaths.length)];
        return new ImagePattern(new Image(selectedImagePath));
    }
}
