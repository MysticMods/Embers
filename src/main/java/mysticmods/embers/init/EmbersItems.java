package mysticmods.embers.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import mysticmods.embers.Embers;
import mysticmods.embers.api.data.EmbersApiTags;
import mysticmods.embers.core.items.HeatedMetalItem;
import mysticmods.embers.core.items.tools.ForgingGloveItem;
import mysticmods.embers.core.items.tools.IronHammerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;

public class EmbersItems {
	private static final Registrate REGISTRATE = Embers.registrate();
	public static final ItemEntry<Item> CAMINITE_BRICK = REGISTRATE.item("caminite_brick", Item::new).register();
	public static final ItemEntry<Item> CAMINITE_BLEND = REGISTRATE.item("caminite_blend", Item::new).register();
	public static final ItemEntry<Item> EMBER_CRYSTAL = REGISTRATE.item("ember_crystal", Item::new).register();
	public static final ItemEntry<Item> CINDERSTEEL_INGOT = REGISTRATE.item("cindersteel_ingot", Item::new).register();
	public static final ItemEntry<Item> DAWNSTONE_INGOT = REGISTRATE.item("dawnstone_ingot", Item::new).register();
	public static final ItemEntry<Item> ASHEN_STEEL_INGOT = REGISTRATE.item("ashen_steel_ingot", Item::new).register();
	public static final ItemEntry<Item> QUICKSILVER = REGISTRATE.item("quicksilver", Item::new).register();
	public static final ItemEntry<Item> CINNABAR = REGISTRATE.item("cinnabar", Item::new).tag(EmbersApiTags.Items.CINNABAR).register();
	public static final ItemEntry<Item> VERMILLIONITE_CHUNK = REGISTRATE.item("vermillionite_chunk", Item::new).tag(EmbersApiTags.Items.VERMILLIONITE).register();
	public static final ItemEntry<HeatedMetalItem> HEATED_METAL = REGISTRATE.item("heated_metal", HeatedMetalItem::new).register();

	//Tools
	public static final ItemEntry<IronHammerItem> IRON_HAMMER = REGISTRATE.item("iron_hammer", (prop) -> new IronHammerItem(Tiers.IRON, 3.0f, -1.4f, prop)).register();
	public static final ItemEntry<ForgingGloveItem> FORGING_GLOVE = REGISTRATE.item("forging_glove", ForgingGloveItem::new).register();

	//Blocks

	public static void init() {
	}
}
