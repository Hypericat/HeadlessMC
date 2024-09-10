package utils;

import io.netty.buffer.ByteBuf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PacketUtil {
    public static void writeString(DataOutputStream out, String string, Charset charset) throws IOException {
        byte [] bytes = string.getBytes(charset);
        writeVarInt(out, bytes.length);
        out.write(bytes);
    }
    public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }

            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }
    public static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) throw new RuntimeException("VarInt too big");
            if ((k & 0x80) != 128) break;
        }
        return i;
    }

    public static void writeString(String string, ByteBuf buf) {
        byte[] stringBytes = string.getBytes(UTF_8);
        buf.writeInt(stringBytes.length);
        buf.writeBytes(stringBytes);
    }
}
