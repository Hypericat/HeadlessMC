package client.pathing;

import client.pathing.movement.Moves;
import client.pathing.openset.BinaryHeapOpenSet;
import client.pathing.openset.MutableMoveResult;
import math.Vec3i;

import java.util.HashMap;
import java.util.Optional;

public class Pathfinder {
    protected PathNode startNode;
    protected PathNode mostRecentConsidered;
    protected final PathNode[] bestSoFar = new PathNode[COEFFICIENTS.length];
    private final HashMap<Long, PathNode> map;
    public static final double heuristicCost = 3.563;

    private volatile boolean isFinished;
    protected boolean cancelRequested;
    protected static final double[] COEFFICIENTS = {1.5, 2, 2.5, 3, 4, 5, 10};
    protected static final double MIN_DIST_PATH = 5;
    protected static final double MIN_IMPROVEMENT = 0.01;

    private final Goal goal;
    private final Vec3i start;
    private final IWorldProvider world;

    public Pathfinder(final Vec3i start, Goal goal, final IWorldProvider world) {
        this.start = start;
        this.goal = goal;
        this.world = world;
        map = new HashMap<>();
    }


    public Optional<IPath> getPath(long primaryTimeout, long failureTimeout) {
        int minY = world.getDimensionType().getMinY();
        int height = world.getDimensionType().getHeight();
        startNode = getNodeAtPosition(start.getX(), start.getY(), start.getZ(), start.longHash());
        startNode.cost = 0;
        startNode.combinedCost = startNode.heuristic;
        BinaryHeapOpenSet openSet = new BinaryHeapOpenSet();
        openSet.insert(startNode);
        double[] bestHeuristicSoFar = new double[COEFFICIENTS.length];//keep track of the best node by the metric of (estimatedCostToGoal + cost / COEFFICIENTS[i])
        for (int i = 0; i < bestHeuristicSoFar.length; i++) {
            bestHeuristicSoFar[i] = startNode.heuristic;
            bestSoFar[i] = startNode;
        }
        MutableMoveResult res = new MutableMoveResult();
        long startTime = System.currentTimeMillis();
        long primaryTimeoutTime = startTime + primaryTimeout;
        long failureTimeoutTime = startTime + failureTimeout;
        boolean failing = true;
        int numNodes = 0;
        int numMovementsConsidered = 0;
        int numEmptyChunk = 0;
        int timeCheckInterval = 1 << 6;
        int pathingMaxChunkBorderFetch = 50;
        double minimumImprovement = MIN_IMPROVEMENT;
        Moves[] allMoves = Moves.values();
        while (!openSet.isEmpty() && numEmptyChunk < pathingMaxChunkBorderFetch && !cancelRequested) {
            if ((numNodes & (timeCheckInterval - 1)) == 0) { // only call this once every 64 nodes (about half a millisecond)
                long now = System.currentTimeMillis(); // since nanoTime is slow on windows (takes many microseconds)
                if (now - failureTimeoutTime >= 0 || (!failing && now - primaryTimeoutTime >= 0)) {
                    break;
                }
            }
            PathNode currentNode = openSet.removeLowest();
            mostRecentConsidered = currentNode;
            numNodes++;
            if (goal.isInGoal(currentNode.x, currentNode.y, currentNode.z)) {
                logDebug("Took " + (System.currentTimeMillis() - startTime) + "ms, " + numMovementsConsidered + " movements considered");
                return Optional.of(new Path(start, startNode, currentNode, numNodes, goal));
            }
            CalculationContext context = new CalculationContext(world);

            for (Moves moves : allMoves) {
                int newX = currentNode.x + moves.xOffset;
                int newZ = currentNode.z + moves.zOffset;
                if ((newX >> 4 != currentNode.x >> 4 || newZ >> 4 != currentNode.z >> 4) && !world.isLoaded(newX, newZ)) {
                    //i only need to check if the destination is a loaded chunk f it's in a different chunk than the start of the movement
                    if (!moves.dynamicXZ) { // only increment the counter if the movement would have gone out of bounds guaranteed
                        numEmptyChunk++;
                    }
                    continue;
                }
                if (currentNode.y + moves.yOffset > height || currentNode.y + moves.yOffset < minY) {
                    continue;
                }
                res.reset();
                moves.apply(context, currentNode.x, currentNode.y, currentNode.z, res);
                numMovementsConsidered++;
                double actionCost = res.cost;
                if (actionCost >= ActionCosts.COST_INF) {
                    continue;
                }
                if (actionCost <= 0 || Double.isNaN(actionCost)) {
                    throw new IllegalStateException(moves + " calculated implausible cost " + actionCost);
                }
                if (!moves.dynamicXZ && (res.x != newX || res.z != newZ)) {
                    throw new IllegalStateException(moves + " " + res.x + " " + newX + " " + res.z + " " + newZ);
                }
                if (!moves.dynamicY && res.y != currentNode.y + moves.yOffset) {
                    throw new IllegalStateException(moves + " " + res.y + " " + (currentNode.y + moves.yOffset));
                }
                long hashCode = Vec3i.longHash(res.x, res.y, res.z);
                PathNode neighbor = getNodeAtPosition(res.x, res.y, res.z, hashCode);
                double tentativeCost = currentNode.cost + actionCost;
                if (neighbor.cost - tentativeCost > minimumImprovement) {
                    neighbor.previous = currentNode;
                    neighbor.cost = tentativeCost;
                    neighbor.combinedCost = tentativeCost + neighbor.heuristic;
                    if (neighbor.isOpen()) {
                        openSet.update(neighbor);
                    } else {
                        openSet.insert(neighbor);//dont double count, dont insert into open set if it's already there
                    }
                    for (int i = 0; i < COEFFICIENTS.length; i++) {
                        double heuristic = neighbor.heuristic + neighbor.cost / COEFFICIENTS[i];
                        if (bestHeuristicSoFar[i] - heuristic > minimumImprovement) {
                            bestHeuristicSoFar[i] = heuristic;
                            bestSoFar[i] = neighbor;
                            if (failing && getDistFromStartSq(neighbor) > MIN_DIST_PATH * MIN_DIST_PATH) {
                                failing = false;
                            }
                        }
                    }
                }
            }
        }
        if (cancelRequested) {
            return Optional.empty();
        }
        System.out.println(numMovementsConsidered + " movements considered");
        System.out.println("Open set size: " + openSet.size());
        System.out.println("PathNode map size: " + mapSize());
        System.out.println((int) (numNodes * 1.0 / ((System.currentTimeMillis() - startTime) / 1000F)) + " nodes per second");
        Optional<IPath> result = bestSoFar(true, numNodes);
        if (result.isPresent()) {
            logDebug("Took " + (System.currentTimeMillis() - startTime) + "ms, " + numMovementsConsidered + " movements considered");
        }
        return result;
    }

    protected double getDistFromStartSq(PathNode n) {
        int xDiff = n.x - start.getX();
        int yDiff = n.y - start.getY();
        int zDiff = n.z - start.getZ();
        return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
    }

    protected PathNode getNodeAtPosition(int x, int y, int z, long hashCode) {
        PathNode node = map.get(hashCode);
        if (node == null) {
            node = new PathNode(x, y, z, goal);
            map.put(hashCode, node);
        }
        return node;
    }

    public Optional<IPath> pathToMostRecentNodeConsidered() {
        return Optional.ofNullable(mostRecentConsidered).map(node -> new Path(start, startNode, node, 0, goal));
    }

    public Optional<IPath> bestPathSoFar() {
        return bestSoFar(false, 0);
    }

    protected Optional<IPath> bestSoFar(boolean logInfo, int numNodes) {
        if (startNode == null) {
            return Optional.empty();
        }
        double bestDist = 0;
        for (int i = 0; i < COEFFICIENTS.length; i++) {
            if (bestSoFar[i] == null) {
                continue;
            }
            double dist = getDistFromStartSq(bestSoFar[i]);
            if (dist > bestDist) {
                bestDist = dist;
            }
            if (dist > MIN_DIST_PATH * MIN_DIST_PATH) { // square the comparison since distFromStartSq is squared
                if (logInfo) {
                    if (COEFFICIENTS[i] >= 3) {
                        System.out.println("Warning: cost coefficient is greater than three! Probably means that");
                        System.out.println("the path I found is pretty terrible (like sneak-bridging for dozens of blocks)");
                        System.out.println("But I'm going to do it anyway, because yolo");
                    }
                    System.out.println("Path goes for " + Math.sqrt(dist) + " blocks");
                    logDebug("A* cost coefficient " + COEFFICIENTS[i]);
                }
                return Optional.of(new Path(start, startNode, bestSoFar[i], numNodes, goal));
            }
        }
        if (logInfo) {
            logDebug("Even with a cost coefficient of " + COEFFICIENTS[COEFFICIENTS.length - 1] + ", I couldn't get more than " + Math.sqrt(bestDist) + " blocks");
            logDebug("No path found =(");
        }
        return Optional.empty();
    }

    public void logDebug(Object o) {
        System.out.println(o);
    }

    public final boolean isFinished() {
        return isFinished;
    }

    public final Goal getGoal() {
        return goal;
    }

    public Vec3i getStart() {
        return start;
    }

    protected int mapSize() {
        return map.size();
    }
}
