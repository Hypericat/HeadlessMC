package client.game;

import client.HeadlessInstance;
import client.pathing.IWorldProvider;
import client.utils.Timer;
import client.utils.UUID;
import math.MutableVec3i;
import math.Pair;
import math.Vec3d;
import math.Vec3i;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

public class World implements IWorldProvider {
    private final int maxHeight;
    HashMap<Long, Chunk> chunks = new HashMap<>();
    private final HashMap<Block, HashMap<Long, Vec3i>> cached = new HashMap<>();
    private final HashMap<Integer, Entity> entities = new HashMap<>();
    private final HeadlessInstance instance;

    public World(int maxHeight, HeadlessInstance instance) {
        this.maxHeight = maxHeight;
        this.instance = instance;
    }

    public World(HeadlessInstance instance) {
        this(384, instance);
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
        int timer = Timer.start();

        long hash = getHash(chunk);
        if (chunks.containsKey(hash))
            unloadChunk(chunks.get(hash), hash);

        HashSet<Block> cachedKeys = new HashSet<>(cached.keySet());
        // Holy O(n)
        for (Pair<Block, MutableVec3i> pair : chunk.getAllBlocksSatisfy(pair -> cachedKeys.contains(pair.getLeft()))) {
            Vec3i blockPos = pair.getRight();
            cached.get(pair.getLeft()).put(blockPos.longHash(), blockPos);
        }
        chunks.put(hash, chunk);

        Timer.end(timer);
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

    public Block getBlock(int x, int y, int z) {
        Chunk chunk = getChunkAtCord(x, z);
        if (chunk == null) return Blocks.AIR;
        return chunk.getBlockAt(Math.floorMod(x, 16), y, (Math.floorMod(z, 16)));
    }

    public void setBlock(Vec3i pos, Block blockState) {
        //System("Set block at {" + pos + "} to " + blockState.toString());
        setBlock(pos.getX(), pos.getY(), pos.getZ(), blockState);
    }

    private void setBlock(int x, int y, int z, Block block) {
        Chunk chunk = getChunkAtCord(x, z);
        if (chunk == null) return;
        Vec3i currentBlock = new Vec3i(x, y, z);

        //update cache for replaced block
        Block replaceBlock = getBlock(currentBlock);
        if (cached.containsKey(replaceBlock)) {
            cached.get(replaceBlock).remove(currentBlock.longHash());
        }

        //update cache for current block
        if (cached.containsKey(block)) {
            cached.get(block).put(currentBlock.longHash(), currentBlock);
        }



        chunk.setBlockAt(Math.floorMod(x, 16), y, (Math.floorMod(z, 16)), block);
    }

    public HeadlessInstance getInstance() {
        return instance;
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

    public void unloadChunkAt(int chunkX, int chunkZ) {
        long hash = getHash(chunkX, chunkZ);
        if (!chunks.containsKey(getHash(chunkX, chunkZ))) return;
        unloadChunk(chunks.get(hash), hash);
    }

    public void unloadChunk(Chunk chunk, long hash) {
        HashSet<Block> cachedKeys = new HashSet<>(cached.keySet());
        // Holy O(n)
        for (Pair<Block, MutableVec3i> pair : chunk.getAllBlocksSatisfy(pair -> cachedKeys.contains(pair.getLeft()))) {
            cached.get(pair.getLeft()).remove(pair.getRight().longHash());
        }
        chunks.remove(hash);
        System.out.println("Cached count : " + countCached());
    }

    public long countCached() {
        AtomicLong count = new AtomicLong(0);
        cached.values().forEach(longVec3iHashMap -> count.addAndGet(longVec3iHashMap.size()));
        return count.get();
    }

    public void removeEntity(Entity entity) {
        removeEntity(entity.getEntityID());
    }

    public void removeEntity(int id) {
        entities.remove(id);
    }

    public List<Entity> getEntitiesWithin(Vec3d origin, double radius) {
        List<Entity> entities = new ArrayList<>();
        try {
            for (Entity entity : this.entities.values()) {
                if (entity.getBoundingBox().getCenter().add(entity.getPos()).isInRange(origin, radius))
                    entities.add(entity);
            }
        } catch (ConcurrentModificationException ex) {
            //return either a half full list or an empty list, realistically we would want to try again because this is a threading error but for now it doesn't matter
            return entities;
        }
        return entities;
    }

    public Entity getEntityByUUID(UUID uuid) {
        for (Entity entity : this.entities.values()) {
            if (entity.getUuid().equals(uuid)) return entity;
        }
        return null;
    }

    public <T extends Entity> List<T> getEntitiesByType(EntityType<?> type) {
        List<T> entities = new ArrayList<>();
        for (Entity entity : this.entities.values()) {
            if (entity.getEntityType().getEntityClass() == type.getEntityClass()) entities.add((T) entity);
        }
        return entities;
    }

    public void cacheIfNotPresent(Block block) {
        if (cached.containsKey(block)) return;
        HashMap<Long, Vec3i> blocks = getAllBlocksSatisfy(blockVec3iPair -> blockVec3iPair.getLeft() == block);
        cached.put(block, blocks);
    }

    public List<Vec3i> findCachedBlock(Block block) {
        cacheIfNotPresent(block);
        System.out.println("There are : " +  cached.get(block).values().size() + " " + block.getName());
        return cached.get(block).values().stream().toList();
    }

    public HashMap<Long, Vec3i> getAllBlocksSatisfy(Predicate<Pair<Block, Vec3i>> predicate) {
        HashMap<Long, Vec3i> blocks = new HashMap<>();
        chunks.values().forEach(chunk -> chunk.getAllBlocksSatisfy(predicate).forEach(pair -> blocks.put(pair.getRight().longHash(), pair.getRight().toVec3i())));
        return blocks;
    }

    @Override
    public boolean isLoaded(int x, int z) {
        return getChunkAtCord(x, z) != null;
    }

    public DimensionType getDimensionType() {
        return DimensionType.OVERWORLD;
    }

    @Override
    public World getWorld() {
        return this;
    }
}
