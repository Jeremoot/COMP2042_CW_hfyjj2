package main.java.brickGame.assets.brick;

import java.io.Serializable;

/**
 * A record class for serializing brick data.
 * This class encapsulates the essential information of a brick object for serialization purposes.
 * Implements {@link Serializable} to allow its instances to be serialized.
 */
public record BrickSerializable(
        // The row position of the brick
        int row,

        // The column position of the brick
        int j,

        // The type identifier of the brick
        int type
) implements Serializable {
}
