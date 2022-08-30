package mysticmods.embers.init;

import mysticmods.embers.Embers;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.*;
import net.minecraftforge.common.Tags;

public class EmbersItems {
	private static final Registrate REGISTRATE = Embers.registrate();
	public static final ItemEntry<Item> CAMINITE_BRICK = REGISTRATE.item("caminite_brick", Item::new).register();
	public static final ItemEntry<Item> CAMINITE_BLEND = REGISTRATE.item("caminite_blend", Item::new).register();
	public static final ItemEntry<Item> EMBER_CRYSTAL = REGISTRATE.item("ember_crystal", Item::new).register();
	public static final ItemEntry<Item> CINDERSTEEL_INGOT = REGISTRATE.item("cindersteel_ingot", Item::new).register();
	public static final ItemEntry<Item> DAWNSTONE_INGOT = REGISTRATE.item("dawnstone_ingot", Item::new).register();
	public static final ItemEntry<Item> ASHEN_STEEL_INGOT = REGISTRATE.item("ashen_steel_ingot", Item::new).register();
	public static final ItemEntry<Item> QUICKSILVER = REGISTRATE.item("quicksilver", Item::new).register();
	public static final ItemEntry<Item> CINNABAR = REGISTRATE.item("cinnabar", Item::new).register();

	public static void init() {
	}
}
