package client.networking;

import client.HeadlessInstance;
import client.networking.packets.S2C.LoginSuccessfulS2CPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import client.networking.packets.S2C.CompressionRequestS2CPacket;
import client.networking.packets.S2C.S2CPacket;
import client.networking.packets.S2C.StatusResponseS2CPacket;
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
        int packetSize = PacketUtil.readVarInt(buf);
        int packetType = buf.readByte();
        int idk = PacketUtil.readVarInt(buf);
        System.out.println("Received packet with type " + packetType);
        System.out.println("Received packet with size " + buf.readableBytes());
        try {
            Class<?> clazz = packetMap.get(packetType + (long) (handler.getNetworkState() == NetworkState.HANDSHAKE ? Integer.MAX_VALUE : 0));
            if (clazz == null) throw new ClassNotFoundException();
            S2CPacket packet = (S2CPacket) clazz.getDeclaredConstructors()[0].newInstance(buf, packetSize);
            packet.apply(packetHandler);

        } catch (IllegalArgumentException e) {
            System.err.println("Failed to apply packet id : " + packetType);
            e.printStackTrace();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("Failed to decode packet");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid/Unknown packet ID Packet Received, class is null");
        }

    }
    private void initPacketMap() {
        packetMap = new HashMap<>();
        packetMap.put(StatusResponseS2CPacket.getTypeIdOffset(), StatusResponseS2CPacket.class);
        packetMap.put(CompressionRequestS2CPacket.getTypeIdOffset(), CompressionRequestS2CPacket.class);
        packetMap.put(LoginSuccessfulS2CPacket.getTypeIdOffset(), LoginSuccessfulS2CPacket.class);
        //packetMap.put(EncryptionRequestS2CPacket.getTypeIdOffset(), EncryptionRequestS2CPacket.class);
    }
}
