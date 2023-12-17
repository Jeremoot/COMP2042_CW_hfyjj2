package main.java.brickGame;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Manages displaying score and messages in the game.
 * This class is responsible for showing score changes, game messages, and game over notifications.
 */
public class Score {

    /**
     * Displays the score at the specified position.
     * The score appears with an animation and then fades away.
     *
     * @param x     The x-coordinate for displaying the score.
     * @param y     The y-coordinate for displaying the score.
     * @param score The score to display.
     * @param main  The main application instance.
     */
    public void show(final double x, final double y, int score, final Main main) {
        final String sign = (score >= 0) ? "+" : "";
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        Platform.runLater(() -> main.root.getChildren().add(label));

        new Thread(() -> {
            for (int i = 0; i < 21; i++) {
                try {
                    final int index = i; // final variable for use inside lambda
                    Platform.runLater(() -> {
                        label.setScaleX(index);
                        label.setScaleY(index);
                        label.setOpacity((20 - index) / 20.0);
                    });
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // handle interrupted exception
                }
            }
            // Remove the label after the animation
            Platform.runLater(() -> main.root.getChildren().remove(label));
        }).start();
    }

    /**
     * Displays a message on the screen.
     * The message appears with an animation and then fades away.
     *
     * @param message The message to display.
     * @param main    The main application instance.
     */
    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> main.root.getChildren().add(label));
        new Thread(() -> {
            for (int i = 0; i < 21; i++) {
                try {
                    label.setScaleX(Math.abs(i-10));
                    label.setScaleY(Math.abs(i-10));
                    label.setOpacity((20 - i) / 20.0);
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Displays the game over message and a restart button.
     *
     * @param main The main application instance.
     */
    public void showGameOver(final Main main) {
        Platform.runLater(() -> {
            final Label label = new Label("GAME OVER");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            final Button restart = new Button("Restart");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction(event -> main.restartGame());

            main.root.getChildren().addAll(label, restart);
        });
    }

    /**
     * Displays a victory message.
     *
     * @param main The main application instance.
     */
    public void showWin(final Main main) {
        Platform.runLater(() -> {
            final Label label = new Label("Victory");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            main.root.getChildren().addAll(label);
        });
    }
}
