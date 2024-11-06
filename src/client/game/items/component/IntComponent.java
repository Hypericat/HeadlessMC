package client.game.items.component;

import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public abstract class IntComponent implements IComponent {
    private int value;

    public IntComponent(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void read(ByteBuf buf) {
        setValue(PacketUtil.readVarInt(buf));
    }
}
