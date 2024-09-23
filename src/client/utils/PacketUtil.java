package client.utils;

import client.networking.NetworkHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.EncoderException;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

import java.nio.charset.Charset;


public class PacketUtil {

    public static void writeString(ByteBuf buf, String string) {
        byte[] bytes = string.getBytes(Charset.defaultCharset());
        writeVarInt(buf, bytes.length);
        buf.writeBytes(bytes);
    }
    public static void encodeString(ByteBuf buf, CharSequence string) {
        encodeString(buf, string, string.length());
    }
    public static void encodeString(ByteBuf buf, CharSequence string, int maxLength) {
        if (string.length() > maxLength) {
            throw new EncoderException("String too big (was " + string.length() + " characters, max " + maxLength + ")");
        } else {
            int i = string.length();
            ByteBuf byteBuf = buf.alloc().buffer(i);

            try {
                int j = ByteBufUtil.writeUtf8(byteBuf, string);
                int k = maxLength;
                if (j > k) {
                    throw new EncoderException("String too big (was " + j + " bytes encoded, max " + k + ")");
                }

                writeVarInt(buf, j);
                buf.writeBytes(byteBuf);
            } finally {
                byteBuf.release();
            }
        }
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
    public static long readVarLong(ByteBuf buf) {
        long l = 0L;
        int i = 0;

        byte b;
        do {
            b = buf.readByte();
            l |= (long)(b & 127) << i++ * 7;
            if (i > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((b & 128) == 128);

        return l;
    }

    public static ByteBuf writeVarLong(ByteBuf buf, long l) {
        while ((l & -128L) != 0L) {
            buf.writeByte((int)(l & 127L) | 128);
            l >>>= 7;
        }

        buf.writeByte((int)l);
        return buf;
    }

    public static String readString(ByteBuf buf) {
        return readString(buf, -1);
    }
    public static String readString(ByteBuf buf, int byteCount) {
        int readIndex = buf.readerIndex();
        byteCount = Math.min(buf.readableBytes(), byteCount == -1 ? Integer.MAX_VALUE : byteCount);
        byte[] byteArray = new byte[byteCount];
        buf.getBytes(readIndex, byteArray);
        return new String(byteArray, Charset.defaultCharset());
    }

    public static void debugBuf(ByteBuf buf) {
        int index = buf.readerIndex();
        for (int i = index; i < buf.readableBytes(); i++) {
            byte[] b = {buf.getByte(i)};
            String str = new String(b, Charset.defaultCharset());
            if (!str.matches("\\A\\p{ASCII}*\\z")) {
                System.out.println(buf.getByte(i));
                return;
            }
            System.out.println(str);
        }
    }
}
