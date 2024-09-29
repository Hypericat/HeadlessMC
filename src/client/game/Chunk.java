package client.game;

import client.utils.ChunkSectionContainer;
import client.utils.Pair;
import client.utils.Vec3i;

import java.util.ArrayList;
import java.util.List;

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

    public int getBlockAt(int chunkPosX, int chunkPosY, int chunkPosZ) {
        return chunkSections.get((chunkPosY + 64) / 16).getIdAt(chunkPosX, (chunkPosY + 64) % 16, chunkPosZ);
    }

    public void setBlockAt(int chunkPosX, int chunkPosY, int chunkPosZ, int blockID) {
        chunkSections.get((chunkPosY + 64) / 16).setAtID(chunkPosX, (chunkPosY + 64) % 16, chunkPosZ, blockID);
    }

    public void setBlocksAt(int sectionY, Pair<Integer, Vec3i>[] blocks) {
        int index = sectionY + 4;
        if (index < 0 || index > chunkSections.size()) return;
        chunkSections.get(index).fillAtIDs(blocks);
    }

    public String toString() {
        return "X : " + this.getChunkX() * 16 + " Z : " + this.getChunkZ() * 16;
    }

}