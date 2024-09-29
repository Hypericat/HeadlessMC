package client.game;

import client.HeadlessInstance;
import client.networking.packets.C2S.play.ClientStatusC2SPacket;
import client.networking.packets.C2S.play.PlayerMoveRotC2SPacket;

public class ClientPlayerEntity extends PlayerEntity{
    private int selectedSlot;
    private int chunkX;
    private int chunkZ;

    public ClientPlayerEntity(HeadlessInstance instance) {
        super(instance);
    }
    @Override
    public void onTick() {
        super.onTick();

        doTestMovement();

        updatePos();
    }

    public int getChunkX() {
        return chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void setChunkZ(int chunkZ) {
        this.chunkZ = chunkZ;
    }

    public void doTestMovement() {
        this.setYaw((float) (Math.random() * 360));
        this.setPitch((float) (Math.random() * 180 - 90));
    }

    public void updatePos() {
        getInstance().getNetworkHandler().sendPacket(new PlayerMoveRotC2SPacket(this.getYaw(), this.getPitch(), true));
    }

    @Override
    public void setHealth(float health) {
        super.setHealth(health);
        System.out.println("Set health to " + health);
        if (this.getHealth() <= 0) {
            respawn();
        }
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public void respawn() {
        setHealth(20);
        System.out.println("Respawning!");
        this.getInstance().getNetworkHandler().sendPacket(new ClientStatusC2SPacket(0));
    }
}
