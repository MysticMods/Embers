package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.*;

public class ModItems {
    private static final Registrate REGISTRATE = Embers.registrate();

    public static final ItemEntry<Item> ARCAIC_BRICK = REGISTRATE.item("arcaic_brick", Item::new).tab(() -> Embers.ITEM_GROUP).register();
    public static final ItemEntry<Item> CAMINITE_BRICK = REGISTRATE.item("caminite_brick", Item::new).tab(() -> Embers.ITEM_GROUP).register();

    //CAMINITE
    public static final ItemEntry<AxeItem> CAMINITE_AXE = REGISTRATE.item("caminite_axe", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
            .tab(() -> Embers.ITEM_GROUP).register();
    public static final ItemEntry<HoeItem> CAMINITE_HOE = REGISTRATE.item("caminite_hoe", (p)-> new HoeItem( Tiers.IRON, 6, -3.1F, p))
            .tab(() -> Embers.ITEM_GROUP).register();
    //public static final ItemEntry<AxeItem> CAMINITE_KNIFE = REGISTRATE.item("caminite_knife", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
    //        .tab(() -> Embers.ITEM_GROUP).register();
    public static final ItemEntry<PickaxeItem> CAMINITE_PICKAXE = REGISTRATE.item("caminite_pickaxe", (p)-> new PickaxeItem( Tiers.IRON, 6, -3.1F, p))
            .tab(() -> Embers.ITEM_GROUP).register();
    public static final ItemEntry<ShovelItem> CAMINITE_SHOVEL = REGISTRATE.item("caminite_shovel", (p)-> new ShovelItem( Tiers.IRON, 6.0F, -3.1F, p))
            .tab(() -> Embers.ITEM_GROUP).register();
    //public static final ItemEntry<AxeItem> CAMINITE_SPEAR = REGISTRATE.item("caminite_spear", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
    //        .tab(() -> Embers.ITEM_GROUP).register();
    public static final ItemEntry<SwordItem> CAMINITE_SWORD = REGISTRATE.item("caminite_sword", (p)-> new SwordItem( Tiers.IRON, 6, -3.1F, p))
            .tab(() -> Embers.ITEM_GROUP).register();


    public static void classload() {
    }
}
