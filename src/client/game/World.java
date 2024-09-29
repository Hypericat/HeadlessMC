package client.game;

import client.utils.Pair;
import client.utils.Vec3d;
import client.utils.Vec3i;

import java.util.HashMap;

public class World {
    private int maxHeight;
    HashMap<Long, Chunk> chunks = new HashMap<>();

    public World(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public World() {
        this(384);
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    private static long getHash(Chunk chunk) {
        return getHash(chunk.getChunkX(), chunk.getChunkZ());
    }
    private static long getHash(int chunkX, int chunkZ) {
        return ((long) chunkX & 0xFFFFFFFFL) << 32 | ((long) chunkZ & 0xFFFFFFFFL);
    }

    public void addChunk(Chunk chunk) {
        long hash = getHash(chunk);
        if (chunks.containsKey(hash)) return;

        chunks.put(hash, chunk);
    }

    public Chunk getChunkAtCord(int x, int z) {
        return getChunkAt((int) Math.floor(x / 16d), (int) Math.floor(z / 16d));
    }

    //returns null if no chunk
    public Chunk getChunkAt(int chunkX, int chunkZ) {
        return chunks.get(getHash(chunkX, chunkZ));
    }

    public int getBlock(Vec3d pos) {
        return getBlock(pos.toVec3i());
    }

    public int getBlock(Vec3i pos) {
        return getBlock(pos.getX(), pos.getY(), pos.getZ());
    }

    private int getBlock(int x, int y, int z) {
        Chunk chunk = getChunkAtCord(x, z);
        //air
        if (chunk == null) return 0;
        return chunk.getBlockAt(Math.floorMod(x, 16), y, (Math.floorMod(z, 16)));
    }

    public void setBlock(Vec3i pos, int blockState) {
        if (blockState <= 30) {
            System.out.println("Set block at {" + pos + "} to " + blockState);
        }
        setBlock(pos.getX(), pos.getY(), pos.getZ(), blockState);
    }

    private void setBlock(int x, int y, int z, int blockState) {
        Chunk chunk = getChunkAtCord(x, z);
        if (chunk == null) return;


        chunk.setBlockAt(Math.floorMod(x, 16), y, (Math.floorMod(z, 16)), blockState);
    }

    public void setBlocks(Vec3i chunkPos, Pair<Integer, Vec3i>[] blocks) {
        Chunk chunk = getChunkAt(chunkPos.getX(), chunkPos.getZ());
        if (chunk == null) return;
        chunk.setBlocksAt(chunkPos.getY(), blocks);
    }
}
