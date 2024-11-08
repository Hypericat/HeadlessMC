package client.game;

import client.utils.ChunkSectionContainer;
import math.MutableVec3i;
import math.Pair;
import math.Vec3i;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Chunk {
    private int chunkX;
    private int chunkZ;
    List<ChunkSectionContainer> chunkSections;

    public Chunk(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        chunkSections = new ArrayList<>();
    }

    public void addSection(ChunkSectionContainer section) {
        this.chunkSections.add(section);
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public Block getBlockAt(int chunkPosX, int chunkPosY, int chunkPosZ) {
        int index = (chunkPosY + 64) / 16;
        if (index < 0 || index > chunkSections.size()) return Blocks.AIR;
        return Blocks.getBlockByID(chunkSections.get(index).getIdAt(chunkPosX, (chunkPosY + 64) % 16, chunkPosZ));
    }

    public void setBlockAt(int chunkPosX, int chunkPosY, int chunkPosZ, Block block) {
        chunkSections.get((chunkPosY + 64) / 16).setAtID(chunkPosX, (chunkPosY + 64) % 16, chunkPosZ, block.getStates().getDefault());
    }

    public void setBlocksAt(int sectionY, Pair<Block, Vec3i>[] blocks) {
        int index = sectionY + 4;
        if (index < 0 || index > chunkSections.size()) return;
        chunkSections.get(index).fillAtIDs(blocks);
    }

    public List<MutableVec3i> getAllBlocksSatisfy(Predicate<Pair<Block, Vec3i>> predicate) {
        List<MutableVec3i> blocks = new ArrayList<>();
        int chunkXOffset = chunkX << 4;
        int chunkZOffset = chunkZ << 4;

        for (ChunkSectionContainer container : chunkSections) {
            List<MutableVec3i> containerBlocks = container.getAllLocalBlocksSatisfy(predicate);
            containerBlocks.forEach(vec3i -> {
                vec3i.addY(container.getY() - 64 - 16);
                vec3i.addX(chunkXOffset);
                vec3i.addZ(chunkZOffset);
            });
            blocks.addAll(containerBlocks);
        }
        return blocks;
    }

    public String toString() {
        return "X : " + this.getChunkX() * 16 + " Z : " + this.getChunkZ() * 16;
    }

}
