package client.game.items.component.components;

import client.game.items.component.IComponent;
import dev.dewy.nbt.Nbt;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.io.NbtReader;
import dev.dewy.nbt.tags.TagType;
import dev.dewy.nbt.tags.collection.CompoundTag;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CustomData implements IComponent {

    public CustomData() {

    }

    @Override
    public IComponent copy() {
        return new CustomData();
    }

    @Override
    public int getTypeID() {
        return 0;
    }

    @Override
    public void read(ByteBuf buf) {
        throw new IllegalStateException();
    }
}
