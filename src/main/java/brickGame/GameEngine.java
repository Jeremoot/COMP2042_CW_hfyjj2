package main.java.brickGame;

/**
 * Manages the main game loop and physics calculations for the game.
 * This class handles updating the game state, performing physics calculations, and tracking time.
 */
public class GameEngine {

    private OnAction onAction; // Interface for callback methods
    private int fps = 15; // Frames per second, converted to milliseconds for the loop
    private Thread updateThread; // Thread for game updates
    private Thread physicsThread; // Thread for physics calculations

    private volatile boolean isRunning = false; // Flag to check if the engine is running

    /**
     * Sets the action listener for game events.
     *
     * @param onAction The callback interface for game actions.
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * Sets the frames per second for the game loop.
     * Converts FPS to milliseconds for timing control.
     *
     * @param fps The frames per second to set.
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }

    /**
     * Starts the update thread for game logic.
     * Runs in a loop until the game stops.
     */
    private synchronized void Update() {
        updateThread = new Thread(() -> {
            while (isRunning) {
                try {
                    onAction.onUpdate();
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    // Restore the interrupted status
                    Thread.currentThread().interrupt();
                }
            }
        });
        updateThread.start();
    }

    /**
     * Initializes game resources.
     * Called at the start of the game.
     */
    private void Initialize() {
        onAction.onInit();
    }

    /**
     * Starts the physics calculation thread.
     * Performs physics updates in a separate thread.
     */
    private synchronized void PhysicsCalculation() {
        physicsThread = new Thread(() -> {
            while (!physicsThread.isInterrupted()) {
                try {
                    onAction.onPhysicsUpdate();
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        physicsThread.start();
    }

    /**
     * Starts the game engine.
     * Initializes and runs the update and physics threads.
     */
    public void start() {
        if (isRunning) {
            return; // Engine is already running
        }
        isRunning = true;
        Initialize();
        Update();
        PhysicsCalculation();
        TimeStart();
    }

    /**
     * Stops the game engine.
     * Interrupts the update and physics threads.
     */
    public void stop() {
        if (isRunning) {
            isRunning = false;
            // Interrupt the threads if they're sleeping or waiting
            updateThread.interrupt();
            physicsThread.interrupt();
            timeThread.interrupt();
        }
    }

    private long time = 0; // Game time counter
    private Thread timeThread; // Thread for tracking game time

    /**
     * Starts the time tracking thread.
     * Increments the time counter every millisecond.
     */
    private void TimeStart() {
        timeThread = new Thread(() -> {
            try {
                while (true) {
                    time++;
                    onAction.onTime(time);
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timeThread.start();
    }

    /**
     * Interface for game action callbacks.
     * Provides methods for different stages of the game loop.
     */
    public interface OnAction {
        void onUpdate(); // Called on each update cycle
        void onInit(); // Called at game initialization
        void onPhysicsUpdate(); // Called for physics calculations
        void onTime(long time); // Called to update game time
    }
}
