package client.networking;

import client.HeadlessInstance;
import client.networking.packets.S2C.*;
import client.networking.packets.S2C.configuration.*;
import client.networking.packets.S2C.play.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import client.utils.PacketUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class InboundHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final PacketHandler packetHandler;
    private HashMap<Long, Class<?>> packetMap;
    private NetworkHandler handler;
    private boolean usedCompression;
    private byte[] lastBytes;
    private byte[] lastCompressedBytes;

    public InboundHandler(NetworkHandler handler, HeadlessInstance instance) {
        super();
        usedCompression = false;
        packetHandler = new PacketHandler(instance);
        this.handler = handler;
        initPacketMap();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf buf) {
        if (handler.isCompressionEnabled()) {
            usedCompression = true;
            buf = handleCompressedPacket(buf);
        }

        if (lastBytes != null) {
            ByteBuf newBuf = Unpooled.buffer();
            newBuf.writeBytes(lastBytes);
            newBuf.writeBytes(buf);
            newBuf.readerIndex(0);
            buf = newBuf;
            lastBytes = null;
        }

        while (buf.readableBytes() > 0) {
            int readerIndex = buf.readerIndex();
            int packetSize = PacketUtil.readVarInt(buf);

            if (!usedCompression && handler.isCompressionEnabled()) {
                packetSize = packetSize - PacketUtil.getIntVarIntSize(PacketUtil.readVarInt(buf));
            }

            if (packetSize > buf.readableBytes()) {
                buf.readerIndex(readerIndex);
                lastBytes = new byte[buf.readableBytes()];
                buf.readBytes(lastBytes);
                return;
            }

            readerIndex = buf.readerIndex();

            ByteBuf sliced = buf.slice(readerIndex, packetSize);
            readBuf(ctx, sliced, packetSize);
            buf.readerIndex(readerIndex + packetSize);
        }
    }
    public void readBuf(ChannelHandlerContext ctx, ByteBuf buf, int maxLength) {

        int packetType;
        try {
            packetType = PacketUtil.readVarInt(buf);
        } catch(IndexOutOfBoundsException e) {
            return;
        }

        try {
            Class<?> clazz = packetMap.get(handler.getNetworkState().calcOffset(packetType));
            if (clazz == null) {
                //System.err.println("Invalid/Unknown packet ID Packet Received, class is null");
                return;
            }
            S2CPacket packet = (S2CPacket) clazz.getDeclaredConstructors()[0].newInstance(buf, maxLength);
            packet.apply(packetHandler);
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to apply packet id : " + PacketUtil.toHex(packetType));
            //e.printStackTrace();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("Failed to decode packet");
            //e.printStackTrace();
        }
    }

    public ByteBuf handleCompressedPacket(ByteBuf buf) {
        ByteBuf decompressedBuf = Unpooled.buffer();
        if (lastCompressedBytes != null) {
            ByteBuf newBuf = Unpooled.buffer();
            newBuf.writeBytes(lastCompressedBytes);
            newBuf.writeBytes(buf);
            newBuf.readerIndex(0);
            buf = newBuf;
            lastCompressedBytes = null;
        }
        while (buf.readableBytes() > 0) {
            int readerIndex = buf.readerIndex();

            int packetLength = PacketUtil.readVarInt(buf);

            if (packetLength > buf.readableBytes()) {
                buf.readerIndex(readerIndex);
                lastCompressedBytes = new byte[buf.readableBytes()];
                buf.readBytes(lastCompressedBytes);
                return decompressedBuf;
            }

            int dataLength = PacketUtil.readVarInt(buf);
            if (dataLength == 0) {
                dataLength = packetLength - PacketUtil.getIntVarIntSize(dataLength);
                PacketUtil.writeVarInt(decompressedBuf, dataLength);
                byte[] data = new byte[dataLength];
                buf.readBytes(data);
                decompressedBuf.writeBytes(data);
            } else {
                int compressedLength = packetLength - PacketUtil.getIntVarIntSize(dataLength);

                PacketUtil.writeVarInt(decompressedBuf, dataLength);

                byte[] data = new byte[compressedLength];
                buf.readBytes(data);
                data = handler.decompress(data);
                if (data.length != dataLength) System.err.println("DECOMPRESSION ERROR");
                decompressedBuf.writeBytes(data);
            }
        }
        return decompressedBuf;
    }
    @Override
    public void channelInactive(ChannelHandlerContext context) {
        //dc
        handler.onDisconnect();
    }

    private void initPacketMap() {
        //login / configuration
        packetMap = new HashMap<>();
        packetMap.put(StatusResponseS2CPacket.networkState.calcOffset(StatusResponseS2CPacket.typeID), StatusResponseS2CPacket.class);
        packetMap.put(CompressionRequestS2CPacket.networkState.calcOffset(CompressionRequestS2CPacket.typeID), CompressionRequestS2CPacket.class);
        packetMap.put(LoginSuccessS2CPacket.networkState.calcOffset(LoginSuccessS2CPacket.typeID), LoginSuccessS2CPacket.class);
        packetMap.put(ClientBoundKnownPacksS2CPacket.networkState.calcOffset(ClientBoundKnownPacksS2CPacket.typeID), ClientBoundKnownPacksS2CPacket.class);
        packetMap.put(CookieRequestS2CPacket.networkState.calcOffset(CookieRequestS2CPacket.typeID), CookieRequestS2CPacket.class);
        packetMap.put(ClientBoundPluginMessageS2CPacket.networkState.calcOffset(ClientBoundPluginMessageS2CPacket.typeID), ClientBoundPluginMessageS2CPacket.class);
        packetMap.put(FinishConfigurationS2CPacket.networkState.calcOffset(FinishConfigurationS2CPacket.typeID), FinishConfigurationS2CPacket.class);
        //packetMap.put(EncryptionRequestS2CPacket.getTypeIdOffset(), EncryptionRequestS2CPacket.class);


        //play
        packetMap.put(LoginPlayS2CPacket.networkState.calcOffset(LoginPlayS2CPacket.typeID), LoginPlayS2CPacket.class);
        packetMap.put(KeepAliveS2CPacket.networkState.calcOffset(KeepAliveS2CPacket.typeID), KeepAliveS2CPacket.class);
        packetMap.put(SetHealthS2CPacket.networkState.calcOffset(SetHealthS2CPacket.typeID), SetHealthS2CPacket.class);
        packetMap.put(SetHeldItemS2CPacket.networkState.calcOffset(SetHeldItemS2CPacket.typeID), SetHeldItemS2CPacket.class);
        packetMap.put(SynchronizePlayerPositionS2CPacket.networkState.calcOffset(SynchronizePlayerPositionS2CPacket.typeID), SynchronizePlayerPositionS2CPacket.class);
        packetMap.put(SetCenterChunkS2CPacket.networkState.calcOffset(SetCenterChunkS2CPacket.typeID), SetCenterChunkS2CPacket.class);
        packetMap.put(ChunkDataS2CPacket.networkState.calcOffset(ChunkDataS2CPacket.typeID), ChunkDataS2CPacket.class);
        packetMap.put(BlockUpdateS2CPacket.networkState.calcOffset(BlockUpdateS2CPacket.typeID), BlockUpdateS2CPacket.class);
        packetMap.put(UpdateBlockSectionS2CPacket.networkState.calcOffset(UpdateBlockSectionS2CPacket.typeID), UpdateBlockSectionS2CPacket.class);
        packetMap.put(SetEntityVelocityS2CPacket.networkState.calcOffset(SetEntityVelocityS2CPacket.typeID), SetEntityVelocityS2CPacket.class);
        packetMap.put(SpawnEntityS2CPacket.networkState.calcOffset(SpawnEntityS2CPacket.typeID), SpawnEntityS2CPacket.class);
        packetMap.put(RemoveEntitiesS2CPacket.networkState.calcOffset(RemoveEntitiesS2CPacket.typeID), RemoveEntitiesS2CPacket.class);
        packetMap.put(UpdateEntityRotationS2CPacket.networkState.calcOffset(UpdateEntityRotationS2CPacket.typeID), UpdateEntityRotationS2CPacket.class);
        packetMap.put(UpdateEntityPositionS2CPacket.networkState.calcOffset(UpdateEntityPositionS2CPacket.typeID), UpdateEntityPositionS2CPacket.class);
        packetMap.put(UpdateEntityPositionAndRotation.networkState.calcOffset(UpdateEntityPositionAndRotation.typeID), UpdateEntityPositionAndRotation.class);
        packetMap.put(TeleportEntityS2CPacket.networkState.calcOffset(TeleportEntityS2CPacket.typeID), TeleportEntityS2CPacket.class);
    }
}
