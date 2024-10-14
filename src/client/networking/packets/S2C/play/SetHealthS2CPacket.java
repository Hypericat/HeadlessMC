package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class SetHealthS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.SET_HEALTH_PLAY_S2C;

    float health;
    int food;
    float foodSaturation;

    public SetHealthS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onSetHealth(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        health = buf.readFloat();
        food = PacketUtil.readVarInt(buf);
        foodSaturation = buf.readFloat();
    }

    public float getHealth() {
        return health;
    }

    public int getFood() {
        return food;
    }

    public float getFoodSaturation() {
        return foodSaturation;
    }
}
