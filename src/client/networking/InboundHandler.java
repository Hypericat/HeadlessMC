package client.networking;

import client.HeadlessInstance;
import client.networking.packets.C2S.play.KeepAliveC2SPacket;
import client.networking.packets.S2C.*;
import client.networking.packets.S2C.configuration.*;
import client.networking.packets.S2C.play.KeepAliveS2CPacket;
import io.netty.buffer.ByteBuf;
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

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf buf) {
        System.out.println("RECEIVED REAL PACKET WITH SIZE " + buf.readableBytes());
        while (buf.readableBytes() != 0) {
            int readerIndex = buf.readerIndex();
            int packetSize = PacketUtil.readVarInt(buf);
            buf.readerIndex(readerIndex);

            ByteBuf sliced = buf.slice(readerIndex, packetSize + 1);
            readBuf(ctx, sliced, packetSize);
            buf.readerIndex(readerIndex + packetSize + 1);
        }
        System.out.println("END OF PACKET");
    }
    public void readBuf(ChannelHandlerContext ctx, ByteBuf buf, int maxLength) {
        System.out.println("Buf Size " + buf.capacity());
        int readable = -1;
        int packetType = -1;
        try {
            readable = buf.readableBytes();
            PacketUtil.readVarInt(buf); //this is just the maxLength which we have already read
            packetType = buf.readByte();
        } catch(IndexOutOfBoundsException e) {
            return;
        }

        //needed sometimes?
        //PacketUtil.readVarInt(buf);
        System.out.println("Received packet with type " + packetType + " with " + handler.getNetworkState().toString());
        //System.out.println("Total of " + readable);
        System.out.println("Packet of " + maxLength);
        try {
            Class<?> clazz = packetMap.get(handler.getNetworkState().calcOffset(packetType));
            if (clazz == null) throw new ClassNotFoundException();
            S2CPacket packet = (S2CPacket) clazz.getDeclaredConstructors()[0].newInstance(buf, maxLength);
            packet.apply(packetHandler);
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to apply packet id : " + packetType);
            //e.printStackTrace();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("Failed to decode packet");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid/Unknown packet ID Packet Received, class is null");
        }
        buf.readerIndex(buf.capacity() - 1);
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
    }
}
