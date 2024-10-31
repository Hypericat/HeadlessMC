package client.pathing;

public interface ActionCosts {

    double WALK_ONE_BLOCK_COST = 20 / 4.317; // 4.633
    double WALK_ONE_IN_WATER_COST = 20 / 2.2; // 9.091
    double WALK_ONE_OVER_SOUL_SAND_COST = WALK_ONE_BLOCK_COST * 2; // 0.4 in BlockSoulSand but effectively about half
    double LADDER_UP_ONE_COST = 20 / 2.35; // 8.511
    double LADDER_DOWN_ONE_COST = 20 / 3.0; // 6.667
    double SNEAK_ONE_BLOCK_COST = 20 / 1.3; // 15.385
    double SPRINT_ONE_BLOCK_COST = 20 / 5.612; // 3.564
    double SPRINT_MULTIPLIER = SPRINT_ONE_BLOCK_COST / WALK_ONE_BLOCK_COST; // 0.769
    double WALK_OFF_BLOCK_COST = WALK_ONE_BLOCK_COST * 0.8; // 3.706
    double CENTER_AFTER_FALL_COST = WALK_ONE_BLOCK_COST - WALK_OFF_BLOCK_COST; // 0.927
    double COST_INF = 1000000;
    double[] FALL_N_BLOCKS_COST = generateFallNBlocksCost();
    double FALL_1_25_BLOCKS_COST = distanceToTicks(1.25);
    double FALL_0_25_BLOCKS_COST = distanceToTicks(0.25);
    double MINE_PENALTY = 5;
    double JUMP_ONE_BLOCK_COST = FALL_1_25_BLOCKS_COST - FALL_0_25_BLOCKS_COST;


    static double[] generateFallNBlocksCost() {
        double[] costs = new double[4097];
        for (int i = 0; i < 4097; i++) {
            costs[i] = distanceToTicks(i);
        }
        return costs;
    }

    static double velocity(int ticks) {
        return (Math.pow(0.98, ticks) - 1) * -3.92;
    }

    static double oldFormula(double ticks) {
        return -3.92 * (99 - 49.5 * (Math.pow(0.98, ticks) + 1) - ticks);
    }

    static double distanceToTicks(double distance) {
        if (distance == 0) {
            return 0;
        }
        double tmpDistance = distance;
        int tickCount = 0;
        while (true) {
            double fallDistance = velocity(tickCount);
            if (tmpDistance <= fallDistance) {
                return tickCount + tmpDistance / fallDistance;
            }
            tmpDistance -= fallDistance;
            tickCount++;
        }
    }
}