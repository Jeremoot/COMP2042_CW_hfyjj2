package main.java.brickGame.assets.brick;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * The abstract base class for all types of bricks in the game.
 * This class defines the common properties and behaviors of bricks.
 */
public abstract class Brick {
    // Row position of the brick in the grid
    public int row;

    // Column position of the brick in the grid
    public int column;

    // Flag to check if the brick is destroyed
    public boolean isDestroyed = false;

    // Color of the brick
    private Color color;

    // Type of the brick
    public int type;

    // X-coordinate of the brick's position
    public int x;

    // Y-coordinate of the brick's position
    public int y;

    // Width of the brick
    private int width = 100;

    // Height of the brick
    private int height = 30;

    // Top padding for the brick position
    private int paddingTop = height * 2;

    // Horizontal padding for the brick position
    private int paddingH = 50;

    // The graphical representation of the brick
    private Rectangle brickBlock = new Rectangle();

    // Constants for different hit codes
    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    /**
     * Constructor for creating a brick.
     *
     * @param row   The row index of the brick.
     * @param column The column index of the brick.
     * @param color The color of the brick.
     */
    public Brick(int row, int column, Color color) {
        this.row = row;
        this.column = column;
        this.color = color;
        initBrickBlock();
        draw();
    }

    /**
     * Initializes the graphical properties of the brick.
     */
    public void initBrickBlock() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;
        brickBlock.setWidth(width);
        brickBlock.setHeight(height);
        brickBlock.setX(x);
        brickBlock.setY(y);
    }

    /**
     * Abstract method to draw the brick.
     * This method must be implemented by subclasses.
     */
    public abstract void draw();

    /**
     * Gets the graphical rectangle of the brick.
     *
     * @return The Rectangle object representing the brick.
     */
    public Rectangle getBrickBlock() {
        return this.brickBlock;
    }

    /**
     * Sets the fill pattern of the brick's graphical representation.
     *
     * @param p The pattern to set on the brick.
     */
    public void setBrickBlockFill(ImagePattern p) {
        this.brickBlock.setFill(p);
    }

    /**
     * Sets the visibility of the brick's graphical representation.
     *
     * @param b The boolean value to set the visibility.
     */
    public void setBrickBlockVisibility(Boolean b) {
        this.brickBlock.setVisible(b);
    }

    /**
     * Checks if a ball has hit the brick and returns the corresponding hit code.
     *
     * @param xBall The x position of the ball.
     * @param yBall The y position of the ball.
     * @return The hit code indicating the side of the hit, or NO_HIT if there is no hit.
     */
    public int checkHitToBrick(double xBall, double yBall) {
        // Checking and returning the hit code logic
        if (isDestroyed) {
            return NO_HIT;
        }

        if (xBall >= x && xBall <= x + width && yBall == y + height) {
            return HIT_BOTTOM;
        }

        if (xBall >= x && xBall <= x + width && yBall == y) {
            return HIT_TOP;
        }

        if (yBall >= y && yBall <= y + height && xBall == x + width) {
            return HIT_RIGHT;
        }

        if (yBall >= y && yBall <= y + height && xBall == x) {
            return HIT_LEFT;
        }

        return NO_HIT;
    }

    /**
     * Returns the top padding.
     *
     * @return The top padding value.
     */
    public static int getPaddingTop() {
        return 60;
    }

    /**
     * Returns the horizontal padding.
     *
     * @return The horizontal padding value.
     */
    public static int getPaddingH() {
        return 50;
    }

    /**
     * Returns the height of the brick.
     *
     * @return The height value.
     */
    public static int getHeight() {
        return 30;
    }

    /**
     * Returns the width of the brick.
     *
     * @return The width value.
     */
    public static int getWidth() {
        return 100;
    }
}
