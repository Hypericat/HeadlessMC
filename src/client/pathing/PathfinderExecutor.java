package client.pathing;

import client.HeadlessInstance;
import client.game.Blocks;
import client.pathing.goals.Goal;
import client.pathing.movement.BlockBreakTickCache;
import math.Vec3d;
import math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class PathfinderExecutor {
    private IPath currentPath;
    private List<Vec3i> positions;
    private int positionIndex;
    private boolean isLast = false;
    private boolean isFinished = false;
    private boolean readyMove = false;
    private Goal goal;
    private final HeadlessInstance instance;
    private final boolean drawing;

    public static final float IDLE_DOWN_VELOCITY = 0.03f;

    public static final int MAX_RETRY = 1;


    public PathfinderExecutor(Goal goal, HeadlessInstance instance) {
        this(goal, instance, false);
    }

    private PathfinderExecutor(Goal goal, HeadlessInstance instance, boolean drawing) {
        this.goal = goal;
        this.instance = instance;
        this.positions = new ArrayList<>();
        this.drawing = drawing;
        nextPath();
    }

    public static PathfinderExecutor draw(Goal goal, HeadlessInstance instance) {
        return new PathfinderExecutor(goal, instance, true);
    }

    public void resetPathfind() {
        readyMove = false;
        runPathFinderThread();
    }

    private void nextPath() {
        if (isLast || positionIndex < positions.size()) return;
        readyMove = false;
        runPathFinderThread();
    }

    private void runPathFinderThread() {
        Thread thread = new Thread(this::findNextPath);
        thread.start();
    }

    private void findNextPath() {
        for (int i = 0; i < MAX_RETRY; i++) {
            instance.getLogger().logUser("Trying to find path!");
            Pathfinder pathfinder = new Pathfinder(instance.getPlayer().getBlockPos(), goal, new CalculationContext(instance.getWorld(), new BlockBreakTickCache(instance.getPlayer().getInventory())));
            currentPath = pathfinder.calculate();
            if (currentPath != null) {
                instance.getLogger().logUser("Found path! Going to " + currentPath.positions().getLast());
                break;
            }
            instance.getLogger().logUser("Failed to find path!");
        }
        //handle no path found
        if (currentPath == null) {
            isFinished = true;
            return;
        }
        positions = currentPath.positions();
        positionIndex = 0;
        if (currentPath instanceof Path) isLast = goal.isInGoal(currentPath.getDest());
        readyMove = true;
    }

    public void doNextMovement() {
        if (!readyMove) {
            instance.getPlayer().setVelocity(Vec3d.ZERO.setY(-IDLE_DOWN_VELOCITY));
            return;
        }
        if (drawing) {
            tickDraw();
            return;
        }
        Vec3i feetBlock = positions.get(positionIndex);
        Vec3i headBlock = feetBlock.addY(1);
        if (instance.getWorld().getBlock(feetBlock).hasCollision()) {
            instance.getInteractionManager().mineWithBestSlot(feetBlock);
            //System.out.println("Block is full : " + feetBlock + " block is : " + instance.getWorld().getBlock(feetBlock));
            return;
        }
        if (instance.getWorld().getBlock(headBlock).hasCollision()) {
            instance.getInteractionManager().mineWithBestSlot(headBlock);
            instance.getPlayer().setVelocity(Vec3d.ZERO.setY(-IDLE_DOWN_VELOCITY));
            //System.out.println("Block is full : " + headBlock + " block is : " + instance.getWorld().getBlock(headBlock));
            return;
        }
        instance.getPlayer().setPos(Vec3d.fromBlock(positions.get(positionIndex)));
        instance.getPlayer().setVelocity(Vec3d.ZERO);


        positionIndex++;
        nextPath();
        if (isLast && positionIndex >= positions.size()) {
            this.isFinished = !goal.next();
            //this should be nextPath()
            if (!this.isFinished) findNextPath();
        }
    }

    private void tickDraw() {
        positions.get(positionIndex).setBlock(this.instance.getInteractionManager(), Blocks.GLASS);
        isLast = true;
        positionIndex++;
        nextPath();
        if (isLast && positionIndex >= positions.size()) {
            isFinished = true;
        }
    }

    public boolean isFinished() {
        return isFinished;
    }


}
