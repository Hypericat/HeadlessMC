package networking.packets.C2S;

import io.netty.buffer.ByteBuf;
import networking.ClientPacketListener;
import utils.PacketUtil;

public class HandShakeC2SPacket extends C2SPacket {
    public static final int typeID = 0x00;
    private int protocolNumber;
    private String serverAddress;
    private short serverPort;
    private byte nextState;

    //state 1 = status
    //state 2 = login
    //state 3 = transfer?

    public HandShakeC2SPacket(int protocolNumber, String serverAddress, int serverPort, int nextState) {
        this.protocolNumber = protocolNumber;
        this.serverAddress = serverAddress;
        this.serverPort = (short) serverPort;
        this.nextState = (byte) nextState;
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
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {
        PacketUtil.writeVarInt(buf, getTypeId());
        PacketUtil.writeVarInt(buf, this.protocolNumber);
        PacketUtil.writeString(buf, this.serverAddress);
        buf.writeShort(this.serverPort);
        buf.writeByte(this.nextState);
    }

    public int getProtocolNumber() {
        return protocolNumber;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public short getServerPort() {
        return serverPort;
    }

    public byte getNextState() {
        return nextState;
    }

    public void setProtocolNumber(int protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public void setServerAddress(String address) {
        this.serverAddress = address;
    }

    public void setServerPort(short port) {
        this.serverPort = port;
    }

    public void setNextState(byte nextState) {
        this.nextState = nextState;
    }

}
