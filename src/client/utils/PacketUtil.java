package client.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.EncoderException;
import math.Vec3i;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.BitSet;


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

    public static int getIntVarIntSize(int value) {
        int byteSize = 0;
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                byteSize ++;
                return byteSize;
            }
            byteSize ++;
            value >>>= 7;
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
        buf.readerIndex(readIndex + byteCount);
        return new String(byteArray, Charset.defaultCharset());
    }
    public static void writeBitSet(BitSet bitSet, int size, ByteBuf buf) {
        if (bitSet.length() > size) {
            throw new EncoderException("BitSet is larger than expected size (" + bitSet.length() + ">" + size + ")");
        } else {
            byte[] bs = bitSet.toByteArray();
            buf.writeBytes(Arrays.copyOf(bs, Math.ceilDiv(size, 8)));
        }
    }

    public static Vec3i readPosition(ByteBuf buf) {
        long pos = buf.readLong();
        int x = (int) (pos >> 38);
        int y = (int) (pos << 52 >> 52);
        int z = (int) (pos << 26 >> 38);

        return new Vec3i(x, y, z);
    }
    public static void writePosition(ByteBuf buf, Vec3i pos) {
        buf.writeLong(((long) (pos.getX() & 0x3FFFFFF) << 38) | ((long) (pos.getZ() & 0x3FFFFFF) << 12) | (pos.getY() & 0xFFF));
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
    public static String toHex(int i) {
        return String.format("0x%02X", i);
    }
}
