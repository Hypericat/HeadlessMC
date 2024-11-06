package client.game.items.component;

import io.netty.buffer.ByteBuf;

public interface IComponent {
    //public int getTypeID();
    //void read(ByteBuf buf);

    public IComponent copy();

    default int getTypeID() {
        throw new IllegalStateException();
    }

    default void read(ByteBuf buf) {
        throw new IllegalStateException();
    }

    default void remove() {
        //do nothing we dont really need to remove in order to read
    }



}
