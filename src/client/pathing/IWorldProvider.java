package client.pathing;

import client.game.DimensionType;
import client.game.World;

public interface IWorldProvider {

    boolean isLoaded(int chunkX, int chunkZ);
    DimensionType getDimensionType();
    World getWorld();

}
