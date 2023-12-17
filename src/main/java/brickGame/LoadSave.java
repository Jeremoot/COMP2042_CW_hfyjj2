package main.java.brickGame;

import main.java.brickGame.assets.brick.BrickSerializable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Manages loading game data from a saved state.
 * This class is responsible for reading saved game data and restoring the game state.
 */
public class LoadSave {
    // Game state flags
    public boolean heartBrickExists;
    public boolean isGolden;
    public boolean goDownBall;
    public boolean goRightBall;
    public boolean collideToBreak;
    public boolean collideToBreakAndMoveToRight;
    public boolean collideToRightWall;
    public boolean collideToLeftWall;
    public boolean collideToRightBrick;
    public boolean collideToBottomBrick;
    public boolean collideToLeftBrick;
    public boolean collideToTopBrick;

    // Game state variables
    public int level;
    public int score;
    public int heart;
    public int destroyedBrickCount;
    public double xBall;
    public double yBall;
    public double xBreak;
    public double yBreak;
    public double centerBreakX;
    public long time;
    public long goldTime;
    public double velocityX;

    // List of bricks in the game
    public ArrayList<BrickSerializable> bricks = new ArrayList<>();

    /**
     * Reads saved game data and restores the game state.
     * Loads various game parameters and state flags from a file.
     */
    public void read() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Main.savePath))) {
            // Load basic game state
            level = inputStream.readInt();
            score = inputStream.readInt();
            heart = inputStream.readInt();
            destroyedBrickCount = inputStream.readInt();

            // Load ball and break positions
            xBall = inputStream.readDouble();
            yBall = inputStream.readDouble();
            xBreak = inputStream.readDouble();
            yBreak = inputStream.readDouble();
            centerBreakX = inputStream.readDouble();

            // Load time-related parameters
            time = inputStream.readLong();
            goldTime = inputStream.readLong();
            velocityX = inputStream.readDouble();

            // Load state flags
            heartBrickExists = inputStream.readBoolean();
            isGolden = inputStream.readBoolean();
            goDownBall = inputStream.readBoolean();
            goRightBall = inputStream.readBoolean();
            collideToBreak = inputStream.readBoolean();
            collideToBreakAndMoveToRight = inputStream.readBoolean();
            collideToRightWall = inputStream.readBoolean();
            collideToLeftWall = inputStream.readBoolean();
            collideToRightBrick = inputStream.readBoolean();
            collideToBottomBrick = inputStream.readBoolean();
            collideToLeftBrick = inputStream.readBoolean();
            collideToTopBrick = inputStream.readBoolean();

            // Load serialized brick data
            try {
                bricks = (ArrayList<BrickSerializable>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
