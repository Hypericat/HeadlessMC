package client.pathing;

import client.HeadlessInstance;
import client.game.Blocks;
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
    private boolean drawing;

    public static final int MAX_RETRY = 3;


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
            System.out.println("Trying to find path!");
            Pathfinder pathfinder = new Pathfinder(instance.getPlayer().getBlockPos(), goal, new CalculationContext(instance.getWorld()));
            currentPath = pathfinder.calculate();
            if (currentPath != null) {
                System.out.println("Found path!");
                break;
            }
            System.out.println("Failed to find path!");
        }
        //handle no path found
        positions = currentPath.positions();
        positionIndex = 0;
        if (currentPath instanceof Path) isLast = goal.isInGoal(currentPath.getDest());
        readyMove = true;
    }

    public void doNextMovement() {
        if (!readyMove) return;
        if (!drawing) {
            instance.getPlayer().setPos(Vec3d.fromBlock(positions.get(positionIndex)));
        } else {
            positions.get(positionIndex).setBlock(this.instance.getInteractionManager(), Blocks.GLASS);
            isLast = true;
        }
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
