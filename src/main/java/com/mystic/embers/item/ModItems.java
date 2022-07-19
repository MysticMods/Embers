package com.mystic.embers.item;

import com.mystic.embers.Embers;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;

public class ModItems {
    private static final Registrate REGISTRATE = Embers.registrate();

    public static final ItemEntry<Item> ARCAIC_BRICK = REGISTRATE.item("arcaic_brick", Item::new).tab(() -> Embers.ITEM_GROUP).register();

    public static void classload() {
    }
}
