package client.game.items.component;

import io.netty.buffer.ByteBuf;

public interface IComponent {
    //public int getTypeID();
    //void read(ByteBuf buf);

    default int getTypeID() {
        throw new IllegalStateException();
    }

    default void read(ByteBuf buf) {
        throw new IllegalStateException();
    }



}
