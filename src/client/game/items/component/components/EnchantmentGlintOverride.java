package client.game.items.component.components;

import client.game.items.component.BooleanComponent;
import client.game.items.component.IntComponent;
import io.netty.buffer.ByteBuf;

public class EnchantmentGlintOverride extends IntComponent {
    public EnchantmentGlintOverride(int value) {
        super(value);
    }

    @Override
    public int getTypeID() {
        return 18;
    }
}