package client.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import javax.crypto.Cipher;
import java.util.List;

public class PacketEncrypter extends MessageToByteEncoder<ByteBuf> {
    private final PacketEncryptionManager manager;

    public PacketEncrypter(Cipher cipher) {
        this.manager = new PacketEncryptionManager(cipher);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, ByteBuf buf2) throws Exception {
        this.manager.encrypt(buf, buf2);
    }
}
