package client.pathing;

import math.Vec3i;

public interface IMovement {

    double getCost();

    MovementStatus update();

    void reset();
    void resetBlockCache();
    boolean safeToCancel();
    boolean calculatedWhileLoaded();

    Vec3i getSrc();

    Vec3i getDest();

    Vec3i getDirection();
}
