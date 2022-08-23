package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.*;
import net.minecraftforge.common.Tags;

public class EmbersItems {
	private static final Registrate REGISTRATE = Embers.registrate();

	public static final ItemEntry<Item> ARCAIC_BRICK = REGISTRATE.item("arcaic_brick", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> CAMINITE_BRICK = REGISTRATE.item("caminite_brick", Item::new).tab(() -> Embers.ITEM_GROUP).recipe((ctx, p) -> ShapedRecipeBuilder.shaped(ctx.getEntry().asItem(), 1)
					.pattern("SCS")
					.pattern("CCC")
					.pattern("SCS")
					.define('S', Tags.Items.SAND)
					.define('C', Items.CLAY_BALL)
					.unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
					.save(p)).register();
	public static final ItemEntry<Item> EMBER_CRYSTAL = REGISTRATE.item("ember_crystal", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> CINDERSTEEL_NUGGET = REGISTRATE.item("cindersteel_nugget", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> CINDERSTEEL_DUST = REGISTRATE.item("cindersteel_dust", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> CINDERSTEEL_INGOT = REGISTRATE.item("cindersteel_ingot", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> DAWNSTONE_NUGGET = REGISTRATE.item("dawnstone_nugget", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> DAWNSTONE_DUST = REGISTRATE.item("dawnstone_dust", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> DAWNSTONE_INGOT = REGISTRATE.item("dawnstone_ingot", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> SOOTY_IRON_NUGGET = REGISTRATE.item("sooty_iron_nugget", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> SOOTY_IRON_DUST = REGISTRATE.item("sooty_iron_dust", Item::new).tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<Item> SOOTY_IRON_INGOT = REGISTRATE.item("sooty_iron_ingot", Item::new).tab(() -> Embers.ITEM_GROUP).register();

	//CAMINITE
	public static final ItemEntry<AxeItem> CAMINITE_AXE = REGISTRATE.item("caminite_axe", (p) -> new AxeItem(Tiers.IRON, 6.0F, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<HoeItem> CAMINITE_HOE = REGISTRATE.item("caminite_hoe", (p) -> new HoeItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	//public static final ItemEntry<AxeItem> CAMINITE_KNIFE = REGISTRATE.item("caminite_knife", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
	//        .tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<PickaxeItem> CAMINITE_PICKAXE = REGISTRATE.item("caminite_pickaxe", (p) -> new PickaxeItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<ShovelItem> CAMINITE_SHOVEL = REGISTRATE.item("caminite_shovel", (p) -> new ShovelItem(Tiers.IRON, 6.0F, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	//public static final ItemEntry<AxeItem> CAMINITE_SPEAR = REGISTRATE.item("caminite_spear", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
	//        .tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<SwordItem> CAMINITE_SWORD = REGISTRATE.item("caminite_sword", (p) -> new SwordItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();

	//CINDERSTEEL
	public static final ItemEntry<AxeItem> CINDERSTEEL_AXE = REGISTRATE.item("cindersteel_axe", (p) -> new AxeItem(Tiers.IRON, 6.0F, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<HoeItem> CINDERSTEEL_HOE = REGISTRATE.item("cindersteel_hoe", (p) -> new HoeItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	//public static final ItemEntry<AxeItem> CINDERSTEEL_KNIFE = REGISTRATE.item("cindersteel_knife", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
	//        .tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<PickaxeItem> CINDERSTEEL_PICKAXE = REGISTRATE.item("cindersteel_pickaxe", (p) -> new PickaxeItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<ShovelItem> CINDERSTEEL_SHOVEL = REGISTRATE.item("cindersteel_shovel", (p) -> new ShovelItem(Tiers.IRON, 6.0F, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	//public static final ItemEntry<AxeItem> CINDERSTEEL_SPEAR = REGISTRATE.item("cindersteel_spear", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
	//        .tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<SwordItem> CINDERSTEEL_SWORD = REGISTRATE.item("cindersteel_sword", (p) -> new SwordItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();

	//DAWNSTONE
	public static final ItemEntry<AxeItem> DAWNSTONE_AXE = REGISTRATE.item("dawnstone_axe", (p) -> new AxeItem(Tiers.IRON, 6.0F, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<HoeItem> DAWNSTONE_HOE = REGISTRATE.item("dawnstone_hoe", (p) -> new HoeItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	//public static final ItemEntry<AxeItem> DAWNSTONE_KNIFE = REGISTRATE.item("dawnstone_knife", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
	//        .tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<PickaxeItem> DAWNSTONE_PICKAXE = REGISTRATE.item("dawnstone_pickaxe", (p) -> new PickaxeItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<ShovelItem> DAWNSTONE_SHOVEL = REGISTRATE.item("dawnstone_shovel", (p) -> new ShovelItem(Tiers.IRON, 6.0F, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	//public static final ItemEntry<AxeItem> DAWNSTONE_SPEAR = REGISTRATE.item("dawnstone_spear", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
	//        .tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<SwordItem> DAWNSTONE_SWORD = REGISTRATE.item("dawnstone_sword", (p) -> new SwordItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();

	//SOOTY_IRON
	public static final ItemEntry<AxeItem> SOOTY_IRON_AXE = REGISTRATE.item("sooty_iron_axe", (p) -> new AxeItem(Tiers.IRON, 6.0F, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<HoeItem> SOOTY_IRON_HOE = REGISTRATE.item("sooty_iron_hoe", (p) -> new HoeItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	//public static final ItemEntry<AxeItem> SOOTY_IRON_KNIFE = REGISTRATE.item("sooty_iron_knife", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
	//        .tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<PickaxeItem> SOOTY_IRON_PICKAXE = REGISTRATE.item("sooty_iron_pickaxe", (p) -> new PickaxeItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<ShovelItem> SOOTY_IRON_SHOVEL = REGISTRATE.item("sooty_iron_shovel", (p) -> new ShovelItem(Tiers.IRON, 6.0F, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();
	//public static final ItemEntry<AxeItem> SOOTY_IRON_SPEAR = REGISTRATE.item("sooty_iron_spear", (p)-> new AxeItem( Tiers.IRON, 6.0F, -3.1F, p))
	//        .tab(() -> Embers.ITEM_GROUP).register();
	public static final ItemEntry<SwordItem> SOOTY_IRON_SWORD = REGISTRATE.item("sooty_iron_sword", (p) -> new SwordItem(Tiers.IRON, 6, -3.1F, p))
					.tab(() -> Embers.ITEM_GROUP).register();

	public static void init() {
	}
}
