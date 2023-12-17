package main.java.brickGame.assets.brick;

import javafx.scene.paint.Color;

/**
 * Factory class for creating various types of bricks.
 * This class utilizes the Factory Design Pattern to instantiate different types of brick objects.
 */
public class BrickFactory {

    // Constant for normal brick type
    public static final int BRICK_NORMAL = 99;

    // Constant for chocolate brick type
    public static final int BRICK_CHOCO = 100;

    // Constant for star brick type
    public static final int BRICK_STAR = 101;

    // Constant for heart brick type
    public static final int BRICK_HEART = 102;

    /**
     * Creates and returns a Brick object based on the specified type.
     * The method uses a switch statement to determine which subclass of Brick to instantiate.
     *
     * @param row The row position where the brick will be placed.
     * @param column The column position where the brick will be placed.
     * @param color The color of the brick.
     * @param type The type of the brick (e.g., normal, chocolate, star, heart).
     * @return An instance of a subclass of Brick corresponding to the specified type.
     */
    public static Brick createBrick(int row, int column, Color color, int type) {
        return switch(type) {
            case BRICK_CHOCO -> new ChocoBrick(row, column, color);
            case BRICK_STAR -> new StarBrick(row, column, color);
            case BRICK_HEART -> new HeartBrick(row, column, color);
            default -> new NormalBrick(row, column, color);
        };
    }
}
