package main.java.brickGame.assets;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import main.java.brickGame.GameConfig;
import main.java.brickGame.assets.brick.Brick;
import main.java.brickGame.gameui.GameData;

import java.util.Random;

/**
 * Represents the ball in the brick game.
 * This class manages the ball's properties and behavior, such as its position, appearance, and movement.
 */
public class Ball {
    private Circle ball; // The graphical representation of the ball
    private double xBall; // X-coordinate of the ball's position
    private double yBall; // Y-coordinate of the ball's position
    private int ballRadius = 10; // Radius of the ball

    /**
     * Constructor to initialize a new ball.
     * The initial position of the ball is randomized within the game's scene constraints.
     *
     * @param gameData The GameData instance containing current game state information.
     */
    public Ball(GameData gameData) {
        Random random = new Random();
        this.xBall = random.nextInt(GameConfig.sceneWidth) + 1;
        this.yBall = random.nextInt(GameConfig.sceneHeight - 350) + ((gameData.getLevel() + 1) * Brick.getHeight()) + 15;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("file:src/main/java/resources/ball/ball.png")));
        setCenter();  // Sets the initial position of the ball
    }

    /**
     * Retrieves the Circle object representing the ball.
     *
     * @return The Circle object of the ball.
     */
    public Circle getBall() {
        return ball;
    }

    /**
     * Retrieves the x-coordinate of the ball.
     *
     * @return The current x-coordinate of the ball.
     */
    public double getxBall() {
        return xBall;
    }

    /**
     * Retrieves the y-coordinate of the ball.
     *
     * @return The current y-coordinate of the ball.
     */
    public double getyBall() {
        return yBall;
    }

    /**
     * Updates the Circle object representing the ball.
     *
     * @param ball The new Circle object for the ball.
     */
    public void setBall(Circle ball) {
        this.ball = ball;
    }

    /**
     * Updates the x-coordinate of the ball.
     * Also updates the ball's graphical position.
     *
     * @param xBall The new x-coordinate of the ball.
     */
    public void setxBall(double xBall) {
        this.xBall = xBall;
        setCenter();
    }

    /**
     * Updates the y-coordinate of the ball.
     * Also updates the ball's graphical position.
     *
     * @param yBall The new y-coordinate of the ball.
     */
    public void setyBall(double yBall) {
        this.yBall = yBall;
        setCenter();
    }

    /**
     * Updates the radius of the ball.
     *
     * @param ballRadius The new radius of the ball.
     */
    public void setBallRadius(int ballRadius) {
        this.ballRadius = ballRadius;
    }

    /**
     * Retrieves the radius of the ball.
     *
     * @return The radius of the ball.
     */
    public int getBallRadius() {
        return ballRadius;
    }

    /**
     * Sets the center coordinates of the ball.
     * Ensures updates to the ball's position are made on the JavaFX Application Thread.
     */
    public void setCenter() {
        Platform.runLater(() -> {
            ball.setCenterX(xBall);
            ball.setCenterY(yBall);
        });
    }

    /**
     * Sets the fill pattern (appearance) of the ball.
     *
     * @param imagePattern The new fill pattern for the ball.
     */
    public void setFill(ImagePattern imagePattern) {
        ball.setFill(imagePattern);
    }
}
