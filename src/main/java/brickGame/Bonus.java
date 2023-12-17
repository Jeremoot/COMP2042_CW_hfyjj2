package main.java.brickGame;

import main.java.brickGame.assets.brick.Brick;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents a bonus item in the game.
 * This class manages the properties and behavior of bonus items, such as their position and appearance.
 */
public class Bonus implements Serializable {
    public Rectangle choco; // Graphical representation of the bonus item

    public double x; // X-coordinate of the bonus item's position
    public double y; // Y-coordinate of the bonus item's position
    public long timeCreated; // Timestamp of when the bonus item was created
    public boolean taken = false; // Flag to check if the bonus item has been taken

    /**
     * Constructor to initialize a new bonus item.
     * Sets the initial position and draws the bonus item.
     *
     * @param row    The row index where the bonus item is located.
     * @param column The column index where the bonus item is located.
     */
    public Bonus(int row, int column) {
        x = (column * (Brick.getWidth())) + Brick.getPaddingH() + ((double) Brick.getWidth() / 2) - 15;
        y = (row * (Brick.getHeight())) + Brick.getPaddingTop() + ((double) Brick.getHeight() / 2) - 15;

        draw();
    }

    /**
     * Draws the bonus item.
     * Sets the size, position, and appearance of the bonus item.
     */
    private void draw() {
        choco = new Rectangle();
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        // Randomly select an image for the bonus item
        String url;
        if (new Random().nextInt(20) % 2 == 0) {
            url = "file:src/main/resources/extras/bonus1.png";
        } else {
            url = "file:src/main/resources/extras/bonus2.png";
        }

        choco.setFill(new ImagePattern(new Image(url)));
    }
}
