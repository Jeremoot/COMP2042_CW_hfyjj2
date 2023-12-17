package main.java.brickGame.assets;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import main.java.brickGame.GameConfig;

/**
 * Represents the paddle in the brick game.
 * This class manages the paddle's properties and behavior, such as its position, size, and movement.
 */
public class Paddle {
    private Rectangle paddle; // Graphical representation of the paddle
    private double xPaddle = 0.0f; // X-coordinate of the paddle's position
    private double yPaddle = 640.0f; // Y-coordinate of the paddle's position
    private double paddleSpeed = 10.0; // Speed of the paddle movement

    // Constants for paddle dimensions and movement directions
    public static final int paddle_Width = 130;
    public static final int paddle_Height = 30;
    public static final int half_paddle_Width = paddle_Width / 2;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    /**
     * Constructor to initialize the paddle.
     * Sets the initial size, position, and appearance of the paddle.
     */
    public Paddle() {
        paddle = new Rectangle();
        paddle.setWidth(paddle_Width);
        paddle.setHeight(paddle_Height);
        paddle.setX(xPaddle);
        paddle.setY(yPaddle);

        ImagePattern pattern = new ImagePattern(new Image("file:src/main/java/resources/brick/paddle.jpg"));
        paddle.setFill(pattern);
    }

    /**
     * Retrieves the Rectangle object representing the paddle.
     *
     * @return The Rectangle object of the paddle.
     */
    public Rectangle getPaddle() {
        return paddle;
    }

    /**
     * Retrieves the x-coordinate of the paddle.
     *
     * @return The current x-coordinate of the paddle.
     */
    public double getxPaddle() {
        return xPaddle;
    }

    /**
     * Retrieves the y-coordinate of the paddle.
     *
     * @return The current y-coordinate of the paddle.
     */
    public double getyPaddle() {
        return yPaddle;
    }

    /**
     * Updates the X-coordinate of the paddle.
     * Also updates the paddle's graphical position along the X-axis.
     *
     * @param xPaddle The new X-coordinate of the paddle.
     */
    public void setxPaddle(double xPaddle) {
        this.xPaddle = xPaddle;
        updatePaddlePositionX();
    }

    /**
     * Updates the X position of the paddle on the screen.
     * Ensures the update is handled in the JavaFX Application Thread.
     */
    private void updatePaddlePositionX() {
        Platform.runLater(() -> {
            paddle.setX(xPaddle);
        });
    }

    /**
     * Updates the Y-coordinate of the paddle.
     * Also updates the paddle's graphical position along the Y-axis.
     *
     * @param yPaddle The new Y-coordinate of the paddle.
     */
    public void setyPaddle(double yPaddle) {
        this.yPaddle = yPaddle;
        updatePaddlePositionY();
    }

    /**
     * Updates the Y position of the paddle on the screen.
     * Ensures the update is handled in the JavaFX Application Thread.
     */
    private void updatePaddlePositionY() {
        Platform.runLater(() -> {
            paddle.setY(yPaddle);
        });
    }

    /**
     * Retrieves the center X-coordinate of the paddle.
     *
     * @return The center X-coordinate of the paddle.
     */
    public double getCenterPaddleX() {
        return xPaddle + half_paddle_Width;
    }

    /**
     * Moves the paddle in the specified direction.
     * Ensures the paddle remains within the boundaries of the game scene.
     *
     * @param direction The direction to move the paddle (LEFT or RIGHT).
     */
    public void move(final int direction) {
        if (direction == RIGHT && xPaddle < (GameConfig.sceneWidth - Paddle.paddle_Width)) {
            setxPaddle(xPaddle + paddleSpeed);
        } else if (direction == LEFT && xPaddle > 0) {
            setxPaddle(xPaddle - paddleSpeed);
        }
    }

}
