package client.game.items.component.components;

import client.game.items.Enchantment;
import client.game.items.EnchantmentType;
import client.game.items.EnchantmentTypes;
import client.game.items.component.IComponent;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EnchantmentsComponent implements IComponent {
    private HashMap<Integer, Enchantment> enchantmentTypes;
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
        this.enchantmentTypes = new HashMap<>(enchantmentCount);
        for (int i = 0; i < enchantmentCount; i++) {
            int enchantmentID = PacketUtil.readVarInt(buf);
            int level = PacketUtil.readVarInt(buf);
            EnchantmentType enchantmentType = EnchantmentTypes.fromID(enchantmentID);
            if (enchantmentType == null) continue;
            this.enchantmentTypes.put(enchantmentID, new Enchantment(enchantmentType, level));
        }
        buf.readBoolean(); //should show tooltip, again we don't care
    }

    public List<Enchantment> getEnchantments() {
        if (enchantmentTypes == null) return List.of();
        return enchantmentTypes.values().stream().toList();
    }

    public boolean hasEnchantment(EnchantmentType enchantmentType) {
        if (enchantmentTypes == null) return false;
        return enchantmentTypes.containsKey(enchantmentType.getTypeID());
    }

    public Enchantment getByType(EnchantmentType type) {
        if (enchantmentTypes == null) return null;
        return enchantmentTypes.get(type.getTypeID());
    }

}
