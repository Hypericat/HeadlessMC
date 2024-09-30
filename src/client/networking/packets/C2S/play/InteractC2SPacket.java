package client.networking.packets.C2S.play;

import client.game.Entity;
import client.game.Hand;
import client.networking.ClientPacketListener;
import client.networking.PacketHandler;
import client.networking.packets.C2S.C2SPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.util.Optional;

public class InteractC2SPacket extends C2SPacket {
    public static final int typeID = 0x16;
    private final int entityID;
    private final int interactType;
    private final Optional<Float> targetX;
    private final Optional<Float> targetY;
    private final Optional<Float> targetZ;
    private final int hand;
    private final boolean clientSneaking;

    public InteractC2SPacket(Entity entity, InteractType type) {
        this(entity, type, type == InteractType.INTERACT_AT ? Optional.of(0f) : getEmpty(), type == InteractType.INTERACT_AT ? Optional.of(0f) : getEmpty(), type == InteractType.INTERACT_AT ? Optional.of(0f) : getEmpty(), Hand.MAIN);
    }
    public InteractC2SPacket(int entityID, InteractType type) {
        this(entityID, type, type == InteractType.INTERACT_AT ? Optional.of(0f) : getEmpty(), type == InteractType.INTERACT_AT ? Optional.of(0f) : getEmpty(), type == InteractType.INTERACT_AT ? Optional.of(0f) : getEmpty(), Hand.MAIN);
    }

    public InteractC2SPacket(Entity entity, InteractType type, Optional<Float> targetX, Optional<Float> targetY, Optional<Float> targetZ, Hand hand) {
        this(entity, type, targetX, targetY, targetZ, hand, false);
    }
    public InteractC2SPacket(int entity, InteractType type, Optional<Float> targetX, Optional<Float> targetY, Optional<Float> targetZ, Hand hand) {
        this(entity, type, targetX, targetY, targetZ, hand, false);
    }

    public InteractC2SPacket(Entity entity, InteractType type, Optional<Float> targetX, Optional<Float> targetY, Optional<Float> targetZ, Hand hand, boolean clientSneaking) {
        this(entity.getEntityID(), type.getValue(), targetX, targetY, targetZ, hand.getValue(), clientSneaking);
    }
    public InteractC2SPacket(int entity, InteractType type, Optional<Float> targetX, Optional<Float> targetY, Optional<Float> targetZ, Hand hand, boolean clientSneaking) {
        this(entity, type.getValue(), targetX, targetY, targetZ, hand.getValue(), clientSneaking);
    }


    public InteractC2SPacket(int entityID, int interactType, Optional<Float> targetX, Optional<Float> targetY, Optional<Float> targetZ, int hand, boolean clientSneaking) {
        this.entityID = entityID;
        this.interactType = interactType;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.hand = hand;
        this.clientSneaking = clientSneaking;
    }

    @Override
    public void apply(ClientPacketListener listener) {
        //no response
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    public static Optional<Float> getEmpty() {
        return Optional.empty();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeId());
        PacketUtil.writeVarInt(buf, entityID);
        PacketUtil.writeVarInt(buf, interactType);
        if (interactType == InteractType.INTERACT_AT.getValue()) {
            buf.writeFloat(targetX.get());
            buf.writeFloat(targetY.get());
            buf.writeFloat(targetZ.get());
        }
        if (interactType != InteractType.ATTACK.getValue()) {
            PacketUtil.writeVarInt(buf, hand);
        }
        buf.writeBoolean(clientSneaking);
    }
}
