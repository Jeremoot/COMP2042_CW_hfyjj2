package main.java.brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import main.java.brickGame.assets.Paddle;
import main.java.brickGame.assets.Ball;
import main.java.brickGame.assets.brick.BrickFactory;
import main.java.brickGame.assets.brick.Brick;
import main.java.brickGame.assets.brick.BrickSerializable;
import main.java.brickGame.gameui.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * The main class for the Brick Breaker game.
 * It sets up the game stage, manages game states and interactions.
 */
public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    // Main game objects
    private Ball ball;
    private Paddle paddle;
    private GameEngine engine;
    public Pane root;

    // Game state variables
    private boolean isGolden      = false;
    private boolean heartBrickExists = false;
    private int destroyedBrickCount = 0;
    private double velocity = 1.000;
    private long time     = 0;
    private long hitTime  = 0;
    private long goldTime = 0;

    // Path for saving and loading the game
    public static String savePath    = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    // Collections for game elements
    private ArrayList<Brick> bricks = new ArrayList<>();
    private ArrayList<Bonus> chocos = new ArrayList<>();

    // Array of colors for bricks
    private Color[]          colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };

    // Game data and UI elements
    private GameData gameData = new GameData();
    private boolean loadSavedGame = false;
    Stage  primaryStage;
    ButtonUI buttonUI;
    ScoreUI scoreUI= new ScoreUI(gameData);
    LevelUI levelUI= new LevelUI(gameData);
    HealthUI healthUI= new HealthUI(gameData);

    /**
     * Starts the main game application.
     * Initializes the game window and starts the game loop.
     *
     * @param primaryStage The primary stage for this application.
     * @throws Exception If there is an issue during startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        if (!loadSavedGame) {
            gameData.setLevel(gameData.getLevel()+1);
            if (gameData.getLevel() >1){
                new Score().showMessage("Level Up!)", this);
            }
            if (gameData.getLevel() == 18) {
                new Score().showWin(this);
                return;
            }
            ball = new Ball(gameData);
            paddle = new Paddle();
            buttonUI = new ButtonUI(this);
            initBoard();
        }

        root = new Pane();

        Image backgroundImage = new Image("file:src/main/java/resources/bg/bg2.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(GameConfig.sceneWidth);  // Set width to match scene's width
        backgroundView.setFitHeight(GameConfig.sceneHeight); // Set height to match scene's height

        root.getChildren().add(backgroundView);

        if (!loadSavedGame) {
            root.getChildren().addAll(
                    paddle.getPaddle(),
                    ball.getBall(),
                    scoreUI.getScoreUI(),
                    healthUI.getHeartUI(),
                    levelUI.getLevelUI(),
                    buttonUI.getNewGameButton()
            );
        } else {
            root.getChildren().addAll(
                    paddle.getPaddle(),
                    ball.getBall(),
                    scoreUI.getScoreUI(),
                    healthUI.getHeartUI(),
                    levelUI.getLevelUI()
            );
        }
        for (Brick brick : bricks) {
            root.getChildren().add(brick.getBrickBlock());
        }

        Scene scene = new Scene(root, GameConfig.sceneWidth, GameConfig.sceneHeight);
        scene.getStylesheets().add("file:src/main/java/resources/extras/style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (!loadSavedGame) {
            if (gameData.getLevel() > 1 && gameData.getLevel() < 18) {
                buttonUI.setLoadButtonVisibility(false);
                buttonUI.setNewGameButtonVisibility(false);
                initGameEngine();
            }
        } else {
            initGameEngine();
            loadSavedGame = false;
        }

    }

    /**
     * Initializes the game engine.
     * Sets up the game loop and starts processing game actions.
     */
    public void initGameEngine(){
        engine = new GameEngine();
        engine.setOnAction(this);
        engine.setFps(120);
        engine.start();
    }

    /**
     * Initializes the game board.
     * Sets up bricks and other game elements based on the current level.
     */
    private void initBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < gameData.getLevel() + 1; j++) {
                int type;
                int r = new Random().nextInt(10);
                if (r == 1) {
                    type = BrickFactory.BRICK_CHOCO;
                } else if (r == 2) {
                    if (!heartBrickExists) {
                        type = BrickFactory.BRICK_HEART;
                        heartBrickExists = true;
                    } else {
                        type = BrickFactory.BRICK_NORMAL;
                    }
                } else if (r == 3) {
                    type = BrickFactory.BRICK_STAR;
                } else {
                    type = BrickFactory.BRICK_NORMAL;
                }
                bricks.add(BrickFactory.createBrick(j, i, colors[new Random().nextInt(colors.length)], type));
            }
        }
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Handles keyboard events for game controls.
     *
     * @param event The keyboard event to handle.
     */
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                paddle.move(Paddle.LEFT);
                break;
            case RIGHT:
                paddle.move(Paddle.RIGHT);
                break;
            case DOWN:
                break;
            case S:
                saveGame();
                break;
            case L:
                loadGame();
                break;
        }
    }

    // Additional private methods for game logic like collision detection, game saving, and level progression
    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;
    private boolean collideToBreak               = false;
    private boolean collideToBreakAndMoveToRight = true;
    private boolean collideToRightWall           = false;
    private boolean collideToLeftWall            = false;
    private boolean collideToRightBrick          = false;
    private boolean collideToBottomBrick         = false;
    private boolean collideToLeftBrick           = false;
    private boolean collideToTopBrick            = false;

    private double velocityX = 1.000;
    private double velocityY = 1.000;


    private void resetCollideFlags() {

        collideToBreak = false;
        collideToBreakAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBrick = false;
        collideToBottomBrick = false;
        collideToLeftBrick = false;
        collideToTopBrick = false;
    }

    /**
     * Sets the physics for the ball, including its movement and collision detection.
     */
    private void setPhysicsToBall() {
        //v = ((time - hitTime) / 1000.000) + 1.000;

        // Update the ball's vertical position
        if (goDownBall) {
            ball.setyBall(ball.getyBall() + velocityY);
        } else {
            ball.setyBall(ball.getyBall() - velocityY);
        }

        // Update the ball's horizontal position
        if (goRightBall) {
            ball.setxBall(ball.getxBall() + velocityX);
        } else {
            ball.setxBall(ball.getxBall() - velocityX);
        }

        // Collision with left wall
        if (ball.getxBall() <= ball.getBallRadius()) {
            goRightBall = true;
            resetCollideFlags();
        }

        // Collision with right wall
        if (ball.getxBall() >= GameConfig.sceneWidth - ball.getBallRadius()) {
            goRightBall = false;
            resetCollideFlags();
        }

        // Collision with top wall
        if (ball.getyBall() <= ball.getBallRadius()) {
            goDownBall = true;
            resetCollideFlags();
        }

        // Collision with bottom wall - game over or life lost
        if (ball.getyBall() >= GameConfig.sceneHeight - ball.getBallRadius()) {
            // Handle game over or life lost
            goDownBall = false;

            if (!isGolden) {
                //TODO gameover

                gameData.setHeart(gameData.getHeart()-1);
                new Score().show((double) GameConfig.sceneWidth / 2, (double) GameConfig.sceneHeight / 2, -1, this);

                if (gameData.getHeart() == 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                }

            }
            //return;
        }

        if (ball.getyBall() >= paddle.getyPaddle() - ball.getBallRadius()) {
            //System.out.println("collide1");
            if (ball.getxBall() >= paddle.getxPaddle() && ball.getxBall() <= paddle.getxPaddle() + Paddle.paddle_Width) {
                hitTime = time;
                resetCollideFlags();
                collideToBreak = true;
                goDownBall = false;

                double relation = (ball.getxBall() - paddle.getCenterPaddleX()) / ((double) Paddle.paddle_Width / 2);

                if (Math.abs(relation) <= 0.3) {
                    //velocityX = 0;
                    velocityX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    velocityX = (Math.abs(relation) * 1.5) + (gameData.getLevel() / 3.500);
                    //System.out.println("velocityX " + velocityX);
                } else {
                    velocityX = (Math.abs(relation) * 2) + (gameData.getLevel() / 3.500);
                    //System.out.println("velocityX " + velocityX);
                }

                collideToBreakAndMoveToRight = ball.getxBall() - paddle.getCenterPaddleX() > 0;
                //System.out.println("collide2");
            }
        }

        if (ball.getxBall() >= GameConfig.sceneWidth) {
            resetCollideFlags();
            //velocityX = 1.000;
            collideToRightWall = true;
        }

        if (ball.getxBall() <= 0) {
            resetCollideFlags();
            //velocityX = 1.000;
            collideToLeftWall = true;
        }

        if (collideToBreak) {
            goRightBall = collideToBreakAndMoveToRight;
        }

        //Wall collide

        if (collideToRightWall) {
            goRightBall = false;
        }

        if (collideToLeftWall) {
            goRightBall = true;
        }

        //Brick collide

        if (collideToRightBrick) {
            goRightBall = true;
        }

        if (collideToLeftBrick) {
            goRightBall = true;
        }

        if (collideToTopBrick) {
            goDownBall = false;
        }

        if (collideToBottomBrick) {
            goDownBall = true;
        }

    }

    /**
     * Checks the count of destroyed bricks to determine if the level is completed.
     */
    private void checkDestroyedCount() {
        if (destroyedBrickCount == bricks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");

            nextLevel();
        }
    }

    /**
     * Saves the current game state to a file.
     */
    private void saveGame() {
        new Thread(() -> {
            new File(savePathDir).mkdirs();
            File file = new File(savePath);
            ObjectOutputStream outputStream = null;
            try {
                outputStream = new ObjectOutputStream(new FileOutputStream(file));

                outputStream.writeInt(gameData.getLevel());
                outputStream.writeInt(gameData.getScore());
                outputStream.writeInt(gameData.getHeart());
                outputStream.writeInt(destroyedBrickCount);


                outputStream.writeDouble(ball.getxBall());
                outputStream.writeDouble(ball.getyBall());
                outputStream.writeDouble(paddle.getxPaddle());
                outputStream.writeDouble(paddle.getyPaddle());
                outputStream.writeDouble(paddle.getCenterPaddleX());
                outputStream.writeLong(time);
                outputStream.writeLong(goldTime);
                outputStream.writeDouble(velocityX);


                outputStream.writeBoolean(heartBrickExists);
                outputStream.writeBoolean(isGolden);
                outputStream.writeBoolean(goDownBall);
                outputStream.writeBoolean(goRightBall);
                outputStream.writeBoolean(collideToBreak);
                outputStream.writeBoolean(collideToBreakAndMoveToRight);
                outputStream.writeBoolean(collideToRightWall);
                outputStream.writeBoolean(collideToLeftWall);
                outputStream.writeBoolean(collideToRightBrick);
                outputStream.writeBoolean(collideToBottomBrick);
                outputStream.writeBoolean(collideToLeftBrick);
                outputStream.writeBoolean(collideToTopBrick);

                ArrayList<BrickSerializable> brickSerializables = new ArrayList<>();
                for (Brick brick : bricks) {
                    if (brick.isDestroyed) {
                        continue;
                    }
                    brickSerializables.add(new BrickSerializable(brick.row, brick.column, brick.type));
                }

                outputStream.writeObject(brickSerializables);

                new Score().showMessage("Game Saved", Main.this);


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert outputStream != null;
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Loads a saved game state from a file.
     */
    private void loadGame() {

        LoadSave loadSave = new LoadSave();
        loadSave.read();


        heartBrickExists = loadSave.heartBrickExists;
        isGolden = loadSave.isGolden;
        goDownBall = loadSave.goDownBall;
        goRightBall = loadSave.goRightBall;
        collideToBreak = loadSave.collideToBreak;
        collideToBreakAndMoveToRight = loadSave.collideToBreakAndMoveToRight;
        collideToRightWall = loadSave.collideToRightWall;
        collideToLeftWall = loadSave.collideToLeftWall;
        collideToRightBrick = loadSave.collideToRightBrick;
        collideToBottomBrick = loadSave.collideToBottomBrick;
        collideToLeftBrick = loadSave.collideToLeftBrick;
        collideToTopBrick = loadSave.collideToTopBrick;
        gameData.set(loadSave.score, loadSave.heart, loadSave.level);
        destroyedBrickCount = loadSave.destroyedBrickCount;
        ball.setxBall(loadSave.xBall);
        ball.setyBall(loadSave.yBall);
        paddle.setxPaddle(loadSave.xBreak);
        paddle.setyPaddle(loadSave.yBreak);
        paddle.setxPaddle(loadSave.centerBreakX - Paddle.half_paddle_Width);
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        velocityX = loadSave.velocityX;

        bricks.clear();
        chocos.clear();

        for (BrickSerializable ser : loadSave.bricks) {
            int r = new Random().nextInt(200);
            // Use the accessor methods to get the values from the record
            bricks.add(BrickFactory.createBrick(ser.row(), ser.j(), colors[r % colors.length], ser.type()));
        }

        try {
            loadSavedGame = true;
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Advances the game to the next level.
     */
    private void nextLevel() {
        Platform.runLater(() -> {
            try {
                // Stop the game engine and reset game states
                engine.stop();
                resetCollideFlags();
                goDownBall = true;
                isGolden = false;
                heartBrickExists = false;
                hitTime = 0;
                time = 0;
                goldTime = 0;

                // Clear existing game objects and reset counters
                bricks.clear();
                chocos.clear();
                destroyedBrickCount = 0;

                start(primaryStage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Restarts the game, resetting all game states and beginning anew.
     */
    public void restartGame() {

        try {
            gameData.set(0,3,0);
            velocityX = 1.000;
            destroyedBrickCount = 0;
            resetCollideFlags();
            goDownBall = true;

            isGolden = false;
            heartBrickExists = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            bricks.clear();
            chocos.clear();

            start(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoked by the game engine on each update cycle.
     * Used to update game objects and check game state.
     */
    @Override
    public void onUpdate() {
        Platform.runLater(() -> {

            ball.setCenter();

            for (Bonus choco : chocos) {
                choco.choco.setY(choco.y);
            }
        });

        if (ball.getyBall() >= Brick.getPaddingTop() && ball.getyBall() <= (Brick.getHeight() * (gameData.getLevel() + 1)) + Brick.getPaddingTop()) {
            for (final Brick brick : bricks) {
                int hitCode = brick.checkHitToBrick(ball.getxBall(), ball.getyBall());
                if (hitCode != Brick.NO_HIT) {

                    gameData.setScore(gameData.getScore() + 1);
                    new Score().show(brick.x, brick.y, 1, this);

                    brick.setBrickBlockVisibility(false);
                    brick.isDestroyed = true;
                    destroyedBrickCount++;
                    //System.out.println("size is " + bricks.size());
                    resetCollideFlags();

                    if (brick.type == BrickFactory.BRICK_CHOCO) {
                        final Bonus choco = new Bonus(brick.row, brick.column);
                        choco.timeCreated = time;
                        Platform.runLater(() -> root.getChildren().add(choco.choco));
                        chocos.add(choco);
                    }

                    if (brick.type == BrickFactory.BRICK_STAR) {
                        goldTime = time;
                        ball.setFill(new ImagePattern(new Image("file:src/main/java/resources/ball/goldball.png")));
                        System.out.println("GOLDEN BALL");
                        root.getStyleClass().add("goldRoot");
                        isGolden = true;
                    }

                    if (brick.type == BrickFactory.BRICK_HEART) {
                        gameData.setHeart(gameData.getHeart()+1);
                    }

                    if (hitCode == Brick.HIT_RIGHT) {
                        collideToRightBrick = true;
                    } else if (hitCode == Brick.HIT_BOTTOM) {
                        collideToBottomBrick = true;
                    } else if (hitCode == Brick.HIT_LEFT) {
                        collideToLeftBrick = true;
                    } else if (hitCode == Brick.HIT_TOP) {
                        collideToTopBrick = true;
                    }

                }

                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + brick.row + " and column:" + brick.column + " hit");
            }
        }
    }

    /**
     * Invoked by the game engine at game initialization.
     * Used to set up initial game state.
     */
    @Override
    public void onInit() {

    }

    /**
     * Invoked by the game engine for physics calculations.
     * Used to handle game physics and collision detection.
     */
    @Override
    public void onPhysicsUpdate() {
        checkDestroyedCount();
        setPhysicsToBall();


        if (time - goldTime > 5000) {
            ball.setFill(new ImagePattern(new Image("file:src/main/java/resources/ball/ball.png")));
            root.getStyleClass().remove("goldRoot");
            isGolden = false;
        }

        for (Bonus choco : chocos) {
            if (choco.y > GameConfig.sceneHeight || choco.taken) {
                continue;
            }
            if (choco.y >= paddle.getyPaddle() && choco.y <= paddle.getyPaddle() + Paddle.paddle_Height && choco.x >= paddle.getxPaddle() && choco.x <= paddle.getxPaddle() + Paddle.paddle_Width) {
                System.out.println("You Got it! +3 Score");
                choco.taken = true;
                choco.choco.setVisible(false);
                gameData.setScore(gameData.getScore()+3);
                new Score().show(choco.x, choco.y, 3, this);

                System.out.println("New Score " + gameData.getScore());
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }

        //System.out.println("time is:" + time + " goldTime is " + goldTime);

    }

    /**
     * Invoked by the game engine for time tracking.
     *
     * @param time The current game time.
     */
    @Override
    public void onTime(long time) {
        this.time = time;
    }
}
