package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

public class BannerPatterns implements IComponent {

    @Override
    public int getTypeID() {
        return 49;
    }
}