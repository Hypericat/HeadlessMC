package client.networking;

import client.HeadlessInstance;
import client.networking.packets.S2C.*;
import client.networking.packets.S2C.configuration.*;
import client.networking.packets.S2C.play.*;
import client.utils.Vec3i;
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
    public InboundHandler(NetworkHandler handler, HeadlessInstance instance) {
        super();
        packetHandler = new PacketHandler(instance);
        this.handler = handler;
        initPacketMap();
    }
    byte[] lastBytes;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf buf) {
        if (lastBytes != null) {
            ByteBuf newBuf = Unpooled.buffer();
            newBuf.writeBytes(lastBytes);
            newBuf.writeBytes(buf);
            newBuf.readerIndex(0);
            buf = newBuf;
            lastBytes = null;
        }
        //System.out.println("RECEIVED REAL PACKET WITH SIZE " + buf.readableBytes());


        //NetworkHandler.debugBuf(buf);
        while (buf.readableBytes() > 0) {
            int readerIndex = buf.readerIndex();
            int packetSize;
            try {
                packetSize = PacketUtil.readVarInt(buf);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            if (packetSize > buf.readableBytes()) {
                buf.readerIndex(readerIndex);
                lastBytes = new byte[buf.readableBytes()];
                buf.readBytes(lastBytes);
                return;
            }

            readerIndex = buf.readerIndex();

            ByteBuf sliced = buf.slice(readerIndex, packetSize);
            //if (packetSize == 0) break;
            readBuf(ctx, sliced, packetSize);
            buf.readerIndex(readerIndex + packetSize);
        }
        //System.out.println("END OF PACKET");
    }
    public void readBuf(ChannelHandlerContext ctx, ByteBuf buf, int maxLength) {
        int packetType;
        try {
            packetType = PacketUtil.readVarInt(buf);
        } catch(IndexOutOfBoundsException e) {
            return;
        }

        //System.out.println("Received packet with type " + PacketUtil.toHex(packetType) + " with " + handler.getNetworkState().toString());
        //System.out.println("Packet of size " + maxLength);
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
            e.printStackTrace();
        }
    }
    @Override
    public void channelInactive(ChannelHandlerContext context) {
        //dc
        handler.getChannel().close().awaitUninterruptibly();
    }

    private void initPacketMap() {
        //login / configuration
        packetMap = new HashMap<>();
        packetMap.put(StatusResponseS2CPacket.networkState.calcOffset(StatusResponseS2CPacket.typeID), StatusResponseS2CPacket.class);
        packetMap.put(CompressionRequestS2CPacket.networkState.calcOffset(CompressionRequestS2CPacket.typeID), CompressionRequestS2CPacket.class);
        packetMap.put(LoginSuccessfulS2CPacket.networkState.calcOffset(LoginSuccessfulS2CPacket.typeID), LoginSuccessfulS2CPacket.class);
        packetMap.put(ClientBoundKnownPacksS2CPacket.networkState.calcOffset(ClientBoundKnownPacksS2CPacket.typeID), ClientBoundKnownPacksS2CPacket.class);
        packetMap.put(CookieRequestS2CPacket.networkState.calcOffset(CookieRequestS2CPacket.typeID), CookieRequestS2CPacket.class);
        packetMap.put(ClientBoundPluginMessageS2CPacket.networkState.calcOffset(ClientBoundPluginMessageS2CPacket.typeID), ClientBoundPluginMessageS2CPacket.class);
        packetMap.put(FinishConfigurationS2CPacket.networkState.calcOffset(FinishConfigurationS2CPacket.typeID), FinishConfigurationS2CPacket.class);
        //packetMap.put(EncryptionRequestS2CPacket.getTypeIdOffset(), EncryptionRequestS2CPacket.class);


        //play
        packetMap.put(KeepAliveS2CPacket.networkState.calcOffset(KeepAliveS2CPacket.typeID), KeepAliveS2CPacket.class);
        packetMap.put(SetHealthS2CPacket.networkState.calcOffset(SetHealthS2CPacket.typeID), SetHealthS2CPacket.class);
        packetMap.put(SetHeldItemS2CPacket.networkState.calcOffset(SetHeldItemS2CPacket.typeID), SetHeldItemS2CPacket.class);
        packetMap.put(SynchronizePlayerPositionS2CPacket.networkState.calcOffset(SynchronizePlayerPositionS2CPacket.typeID), SynchronizePlayerPositionS2CPacket.class);
        packetMap.put(SetCenterChunkS2CPacket.networkState.calcOffset(SetCenterChunkS2CPacket.typeID), SetCenterChunkS2CPacket.class);
        packetMap.put(ChunkDataS2CPacket.networkState.calcOffset(ChunkDataS2CPacket.typeID), ChunkDataS2CPacket.class);
        packetMap.put(BlockUpdateS2CPacket.networkState.calcOffset(BlockUpdateS2CPacket.typeID), BlockUpdateS2CPacket.class);
        packetMap.put(UpdateBlockSectionS2CPacket.networkState.calcOffset(UpdateBlockSectionS2CPacket.typeID), UpdateBlockSectionS2CPacket.class);
    }
}
