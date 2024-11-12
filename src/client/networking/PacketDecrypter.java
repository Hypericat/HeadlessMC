package client.networking;

import auth.AuthUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import javax.crypto.Cipher;
import java.util.List;

public class PacketDecrypter extends MessageToMessageDecoder<ByteBuf> {
    private final PacketEncryptionManager manager;

    public PacketDecrypter(Cipher cipher) {
        this.manager = new PacketEncryptionManager(cipher);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        list.add(manager.decrypt(channelHandlerContext, buf));
    }
}
