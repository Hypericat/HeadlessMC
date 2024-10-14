package client.networking.packets.C2S.play;

import client.game.BlockFace;
import client.networking.PlayerActionType;
import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.utils.PacketUtil;
import math.Vec3i;
import io.netty.buffer.ByteBuf;

public class PlayerActionC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.PLAYER_ACTION_PLAY_C2S;

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
        return new PlayerActionC2SPacket(PlayerActionType.CANCEL_DIGGING, blockPos, BlockFace.DOWN, sequence);
    }

    public static PlayerActionC2SPacket dropStack() {
        return new PlayerActionC2SPacket(PlayerActionType.DROP_ITEM_STACK, Vec3i.ZERO, BlockFace.DOWN, 0);
    }

    public static PlayerActionC2SPacket dropItem() {
        return new PlayerActionC2SPacket(PlayerActionType.DROP_ITEM, Vec3i.ZERO, BlockFace.DOWN, 0);
    }

    public static PlayerActionC2SPacket updateHandState() {
        return new PlayerActionC2SPacket(PlayerActionType.FINISH_EATING, Vec3i.ZERO, BlockFace.DOWN, 0);
    }

    public static PlayerActionC2SPacket swapHands() {
        return new PlayerActionC2SPacket(PlayerActionType.SWAP_HANDS, Vec3i.ZERO, BlockFace.DOWN, 0);
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
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeID());
        PacketUtil.writeVarInt(buf, actionType);
        PacketUtil.writePosition(buf, blockPos);
        buf.writeByte(face);
        PacketUtil.writeVarInt(buf, sequence);
    }
}
