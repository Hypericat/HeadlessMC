package client.networking.packets.C2S.play;

import client.game.Entity;
import client.game.Hand;
import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.utils.PacketUtil;
import client.utils.Vec3i;
import io.netty.buffer.ByteBuf;

import java.util.Optional;

public class PlayerActionC2SPacket extends C2SPacket {
    public static final int typeID = 0x24;
    private final int actionType;
    private final Vec3i blockPos;
    private final byte face;
    private final int sequence;

    public static PlayerActionC2SPacket startDigging(Vec3i blockPos, BlockFace blockFace, int sequence) {
        return new PlayerActionC2SPacket(PlayerActionType.START_DIGGING, blockPos, blockFace, sequence);
    }

    public static PlayerActionC2SPacket finishDigging(Vec3i blockPos, BlockFace blockFace, int sequence) {
        return new PlayerActionC2SPacket(PlayerActionType.FINISHED_DIGGING, blockPos, blockFace, sequence);
    }

    public static PlayerActionC2SPacket cancelDigging(Vec3i blockPos, int sequence) {
        return new PlayerActionC2SPacket(PlayerActionType.CANCEL_DIGGING, blockPos, BlockFace.BOTTOM, sequence);
    }

    public static PlayerActionC2SPacket dropStack() {
        return new PlayerActionC2SPacket(PlayerActionType.DROP_ITEM_STACK, Vec3i.ZERO, BlockFace.BOTTOM, 0);
    }

    public static PlayerActionC2SPacket dropItem() {
        return new PlayerActionC2SPacket(PlayerActionType.DROP_ITEM, Vec3i.ZERO, BlockFace.BOTTOM, 0);
    }

    public static PlayerActionC2SPacket updateHandState() {
        return new PlayerActionC2SPacket(PlayerActionType.FINISH_EATING, Vec3i.ZERO, BlockFace.BOTTOM, 0);
    }

    public static PlayerActionC2SPacket swapHands() {
        return new PlayerActionC2SPacket(PlayerActionType.SWAP_HANDS, Vec3i.ZERO, BlockFace.BOTTOM, 0);
    }


    private PlayerActionC2SPacket(PlayerActionType action, Vec3i blockPos, BlockFace blockFace, int sequence) {
        this.actionType = action.getValue();
        this.blockPos = blockPos;
        this.face = blockFace.getValue();
        this.sequence = sequence;
    }

    @Override
    public void apply(ClientPacketListener listener) {
        //no response
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeId());
        PacketUtil.writeVarInt(buf, actionType);
        PacketUtil.writePosition(buf, blockPos);
        buf.writeByte(face);
        PacketUtil.writeVarInt(buf, sequence);
    }
}
