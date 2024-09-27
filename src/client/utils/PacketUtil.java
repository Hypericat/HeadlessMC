package client.utils;

import client.networking.NetworkHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.EncoderException;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

import java.nio.charset.Charset;


public class PacketUtil {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

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

    public static void writeVarInt(ByteBuf buf, int value) {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                buf.writeByte(value);
                return;
            }

            buf.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }
    public static int readVarInt(ByteBuf buf) {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = buf.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }
    public static long readVarLong(ByteBuf buf) {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = buf.readByte();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }

    public static void writeVarLong(ByteBuf buf, long value) {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                buf.writeByte((int) value);
                return;
            }

            buf.writeByte((int) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
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
