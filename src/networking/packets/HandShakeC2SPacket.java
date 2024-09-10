package networking.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import networking.Packet;
import networking.ClientPacketListener;
import utils.PacketUtil;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HandShakeC2SPacket implements Packet<ClientPacketListener> {
    private int protocolNumber;
    private String serverAddress;
    private int serverPort;
    private int nextState;

    //state 1 = status
    //state 2 = login
    //state 3 = transfer?

    public HandShakeC2SPacket(int protocolNumber, String serverAddress, int serverPort, int nextState) {
        this.protocolNumber = protocolNumber;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.nextState = nextState;
    }
    public HandShakeC2SPacket(String serverAddress, int serverPort, int nextState) {
        this(765, serverAddress, serverPort, nextState);
    }
    public HandShakeC2SPacket(String serverAddress, int serverPort) {
        this(serverAddress, serverPort, 2);
    }
    public HandShakeC2SPacket(String serverAddress) {
        this(serverAddress, 25565);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        //no response
    }

    @Override
    public byte getTypeId() {
        return 0x00;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(getTypeId());
        buf.writeInt(this.protocolNumber);
        PacketUtil.writeString(this.serverAddress, buf);
        buf.writeInt(this.serverPort);
        buf.writeInt(this.nextState);
    }

    public int getProtocolNumber() {
        return protocolNumber;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return protocolNumber;
    }

    public int getNextState() {
        return nextState;
    }

    public void setProtocolNumber(int protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public void setServerAddress(String address) {
        this.serverAddress = address;
    }

    public void setServerPort(int port) {
        this.serverPort = port;
    }

    public void setNextState(int nextState) {
        this.nextState = nextState;
    }

}
