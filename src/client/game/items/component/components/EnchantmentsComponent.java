package client.game.items.component.components;

import client.game.items.Enchantment;
import client.game.items.Enchantments;
import client.game.items.component.IComponent;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.util.List;

public class EnchantmentsComponent implements IComponent {
    private Enchantment[] enchantments;
    private int level;
    @Override
    public IComponent copy() {
        return new EnchantmentsComponent();
    }

    @Override
    public int getTypeID() {
        return 9;
    }

    @Override
    public void read(ByteBuf buf) {
        int enchantmentCount = PacketUtil.readVarInt(buf);
        this.enchantments = new Enchantment[enchantmentCount];
        for (int i = 0; i < enchantmentCount; i++) {
            int enchantmentID = PacketUtil.readVarInt(buf);
            this.enchantments[i] = Enchantments.fromID(enchantmentID);
            this.level = PacketUtil.readVarInt(buf);
            System.out.println("Found enchantment " + this.enchantments[i].getName() + " " + level);
        }
        buf.readBoolean(); //should show tooltip, again we don't care
    }

    public Enchantment[] getEnchantments() {
        return enchantments;
    }

    public int getLevel() {
        return level;
    }
}
