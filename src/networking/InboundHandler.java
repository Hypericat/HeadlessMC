package networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import networking.packets.S2C.S2CPacket;
import networking.packets.S2C.StatusResponseS2CPacket;
import utils.PacketUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class InboundHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final PacketHandler packetHandler;
    private HashMap<Integer, Class<?>> packetMap;
    public InboundHandler() {
        super();
        packetHandler = new PacketHandler();
        initPacketMap();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf buf) {
        int packetSize = PacketUtil.readVarInt(buf);
        int idk = buf.readByte();
        int packetType = PacketUtil.readVarInt(buf);
        try {
            Class<?> clazz = packetMap.get(packetType);
            if (clazz == null) throw new ClassNotFoundException();
            S2CPacket packet = (S2CPacket) clazz.getDeclaredConstructors()[0].newInstance(buf, packetSize);
            packet.apply(packetHandler);

            //switch (packetType) {
            //    case StatusResponseS2CPacket.typeID -> new StatusResponseS2CPacket(buf, packetSize).apply(packetHandler);
            //    default -> System.err.println("Invalid/Unknown packet ID Packet Received");
            //}
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to apply packet id : " + packetType);
            e.printStackTrace();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("Invalid/Unknown packet ID Packet Received");
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid/Unknown packet ID Packet Received, class is null");
        }

    }
    private void initPacketMap() {
        packetMap = new HashMap<>();
        packetMap.put(StatusResponseS2CPacket.typeID, StatusResponseS2CPacket.class);
    }
}
