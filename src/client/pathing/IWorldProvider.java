package client.pathing;

import client.game.DimensionType;
import client.game.World;

public interface IWorldProvider {

    boolean isLoaded(int blockX, int blockZ);
    DimensionType getDimensionType();
    World getWorld();

}
