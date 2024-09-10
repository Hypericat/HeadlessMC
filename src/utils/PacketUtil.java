package utils;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;


public class PacketUtil {

    public static void writeString(ByteBuf buf, String string) {
        byte[] bytes = string.getBytes(Charset.defaultCharset());
        writeVarInt(buf, bytes.length);
        buf.writeBytes(bytes);
    }

    public static void writeVarInt(ByteBuf buf, int Int) {
        while (true) {
            if ((Int & 0xFFFFFF80) == 0) {
                buf.writeByte(Int);
                return;
            }

            buf.writeByte(Int & 0x7F | 0x80);
            Int >>>= 7;
        }
    }
    public static int readVarInt(ByteBuf buf) {
        int i = 0;
        int j = 0;
        while (true) {
            int k = buf.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) throw new RuntimeException("VarInt too big");
            if ((k & 0x80) != 128) break;
        }
        return i;
    }
    public static String readString(ByteBuf buf) {
        int readIndex = buf.readerIndex();;
        byte[] byteArray = new byte[buf.readableBytes()];
        buf.getBytes(readIndex, byteArray);
        return new String(byteArray, Charset.defaultCharset());
    }
}
