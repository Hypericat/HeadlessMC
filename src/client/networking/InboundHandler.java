package client.networking;

import client.HeadlessInstance;
import client.Logger;
import client.networking.packets.PacketIDS;
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
            int packetSize;
            try {
                packetSize = PacketUtil.readVarInt(buf);
            } catch (IndexOutOfBoundsException ex) {
                buf.readerIndex(readerIndex);
                lastBytes = new byte[buf.readableBytes()];
                buf.readBytes(lastBytes);
                return;
            }

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
        if (NetworkHandler.logIn) {
            handler.logPacket(Boundness.S2C, packetType);
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

            int packetLength;
            try {
                packetLength = PacketUtil.readVarInt(buf);
            } catch (IndexOutOfBoundsException ex) {
                buf.readerIndex(readerIndex);
                lastCompressedBytes = new byte[buf.readableBytes()];
                buf.readBytes(lastCompressedBytes);
                return decompressedBuf;
            }

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
        packetMap.put(StatusResponseS2CPacket.packetID.getOffset(), StatusResponseS2CPacket.class);
        packetMap.put(CompressionRequestS2CPacket.packetID.getOffset(), CompressionRequestS2CPacket.class);
        packetMap.put(LoginSuccessS2CPacket.packetID.getOffset(), LoginSuccessS2CPacket.class);
        packetMap.put(ClientBoundKnownPacksS2CPacket.packetID.getOffset(), ClientBoundKnownPacksS2CPacket.class);
        packetMap.put(CookieRequestS2CPacket.packetID.getOffset(), CookieRequestS2CPacket.class);
        packetMap.put(ClientBoundPluginMessageS2CPacket.packetID.getOffset(), ClientBoundPluginMessageS2CPacket.class);
        packetMap.put(FinishConfigurationS2CPacket.packetID.getOffset(), FinishConfigurationS2CPacket.class);
        //packetMap.put(EncryptionRequestS2CPacket.getTypeIdOffset(), EncryptionRequestS2CPacket.class);


        //play
        packetMap.put(LoginPlayS2CPacket.packetID.getOffset(), LoginPlayS2CPacket.class);
        packetMap.put(KeepAliveS2CPacket.packetID.getOffset(), KeepAliveS2CPacket.class);
        packetMap.put(SetHealthS2CPacket.packetID.getOffset(), SetHealthS2CPacket.class);
        packetMap.put(SetHeldItemS2CPacket.packetID.getOffset(), SetHeldItemS2CPacket.class);
        packetMap.put(SynchronizePlayerPositionS2CPacket.packetID.getOffset(), SynchronizePlayerPositionS2CPacket.class);
        packetMap.put(SetCenterChunkS2CPacket.packetID.getOffset(), SetCenterChunkS2CPacket.class);
        packetMap.put(ChunkDataS2CPacket.packetID.getOffset(), ChunkDataS2CPacket.class);
        packetMap.put(BlockUpdateS2CPacket.packetID.getOffset(), BlockUpdateS2CPacket.class);
        packetMap.put(UpdateBlockSectionS2CPacket.packetID.getOffset(), UpdateBlockSectionS2CPacket.class);
        packetMap.put(SetEntityVelocityS2CPacket.packetID.getOffset(), SetEntityVelocityS2CPacket.class);
        packetMap.put(SpawnEntityS2CPacket.packetID.getOffset(), SpawnEntityS2CPacket.class);
        packetMap.put(RemoveEntitiesS2CPacket.packetID.getOffset(), RemoveEntitiesS2CPacket.class);
        packetMap.put(UpdateEntityRotationS2CPacket.packetID.getOffset(), UpdateEntityRotationS2CPacket.class);
        packetMap.put(UpdateEntityPositionS2CPacket.packetID.getOffset(), UpdateEntityPositionS2CPacket.class);
        packetMap.put(UpdateEntityPositionAndRotation.packetID.getOffset(), UpdateEntityPositionAndRotation.class);
        packetMap.put(TeleportEntityS2CPacket.packetID.getOffset(), TeleportEntityS2CPacket.class);
        packetMap.put(SetContainerContentS2CPacket.packetID.getOffset(), SetContainerContentS2CPacket.class);
        packetMap.put(SetContainerSlotS2CPacket.packetID.getOffset(), SetContainerSlotS2CPacket.class);
        packetMap.put(PlayerChatMessageS2C.packetID.getOffset(), PlayerChatMessageS2C.class);
    }
}
