package client.game;

import client.utils.Pair;
import client.utils.Vec3d;
import client.utils.Vec3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class World {
    private int maxHeight;
    HashMap<Long, Chunk> chunks = new HashMap<>();
    private final HashMap<Integer, Entity> entities = new HashMap<>();

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

    public Block getBlock(Vec3d pos) {
        return getBlock(pos.toVec3i());
    }

    public Block getBlock(Vec3i pos) {
        return getBlock(pos.getX(), pos.getY(), pos.getZ());
    }

    private Block getBlock(int x, int y, int z) {
        Chunk chunk = getChunkAtCord(x, z);
        //air
        if (chunk == null) return Blocks.AIR;
        return chunk.getBlockAt(Math.floorMod(x, 16), y, (Math.floorMod(z, 16)));
    }

    public void setBlock(Vec3i pos, Block blockState) {
        //System.out.println("Set block at {" + pos + "} to " + blockState.toString());
        setBlock(pos.getX(), pos.getY(), pos.getZ(), blockState);
    }

    private void setBlock(int x, int y, int z, Block block) {
        Chunk chunk = getChunkAtCord(x, z);
        if (chunk == null) return;


        chunk.setBlockAt(Math.floorMod(x, 16), y, (Math.floorMod(z, 16)), block);
    }

    public void setBlocks(Vec3i chunkPos, Pair<Block, Vec3i>[] blocks) {
        Chunk chunk = getChunkAt(chunkPos.getX(), chunkPos.getZ());
        if (chunk == null) return;
        chunk.setBlocksAt(chunkPos.getY(), blocks);
    }

    public Entity getEntityByID(int id) {
        return entities.get(id);
    }

    public void addEntity(Entity entity) {
        int id = entity.getEntityID();
        if (entities.containsKey(id)) return;
        entities.put(id, entity);
    }

    public void removeEntity(Entity entity) {
        removeEntity(entity.getEntityID());
    }

    public void removeEntity(int id) {
        entities.remove(id);
    }

    public List<Entity> getEntitiesWithin(Vec3d origin, double radius) {
        List<Entity> entities = new ArrayList<>();
        for (Entity entity : this.entities.values()) {
            if (entity.getBoundingBox().getCenter().add(entity.getPos()).isInRange(origin, radius)) entities.add(entity);
        }
        return entities;
    }

}
