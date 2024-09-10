package networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface Packet<T extends PacketListener> {
    void apply(T listener);
    byte getTypeId();
    void encode(ByteBuf buf);
}
