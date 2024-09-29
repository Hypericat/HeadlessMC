package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class SetHealthS2CPacket extends S2CPacket {
    public static final int typeID = 0x5D;
    public final static NetworkState networkState = NetworkState.PLAY;
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

    @Override
    public int getTypeId() {
        return typeID;
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
