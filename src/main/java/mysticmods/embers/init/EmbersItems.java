package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.data.components.MalleableMetalDataComponent;
import mysticmods.embers.items.HammerItem;
import mysticmods.embers.items.MalleableMetalItem;
import mysticmods.embers.machines.caminite_forge.CaminiteForgeItemBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EmbersItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Embers.MODID);

    //Items
    public static final DeferredItem<Item> ASHEN_STEEL_INGOT = ITEMS.registerSimpleItem("ashen_steel_ingot", new Item.Properties());
    public static final DeferredItem<Item> CAMINITE_BLEND = ITEMS.registerSimpleItem("caminite_blend", new Item.Properties());
    public static final DeferredItem<Item> CAMINITE_BRICK = ITEMS.registerSimpleItem("caminite_brick", new Item.Properties());
    public static final DeferredItem<Item> CINDERSTEEL_INGOT = ITEMS.registerSimpleItem("cindersteel_ingot", new Item.Properties());
    public static final DeferredItem<Item> CINNABAR = ITEMS.registerSimpleItem("cinnabar", new Item.Properties());
    public static final DeferredItem<Item> DAWNSTONE_INGOT = ITEMS.registerSimpleItem("dawnstone_ingot", new Item.Properties());
    public static final DeferredItem<Item> EMBER_CRYSTAL = ITEMS.registerSimpleItem("ember_crystal", new Item.Properties());
    public static final DeferredItem<Item> QUICKSILVER = ITEMS.registerSimpleItem("quicksilver", new Item.Properties());
    public static final DeferredItem<Item> VERMILLIONITE_CHUNK = ITEMS.registerSimpleItem("vermillionite_chunk", new Item.Properties());
    public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.registerSimpleItem("copper_nugget", new Item.Properties());

    public static final DeferredItem<MalleableMetalItem> HEATED_METAL = ITEMS.register("heated_metal",
            () -> new MalleableMetalItem(new Item.Properties().component(EmbersDataComponents.MALLEABLE_METAL.get(), new MalleableMetalDataComponent(100, null))));

    public static final DeferredItem<HammerItem> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(Tiers.IRON, 8.0f, 2, new Item.Properties()));

    //todo: VERMILLIONITE tag
    //todo: CINNABAR tag

    //Blocks
    public static final DeferredItem<BlockItem> BRAZIER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            EmbersBlocks.BRAZIER
    );
    public static final DeferredItem<BlockItem> CAMINITE_BRICK_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            EmbersBlocks.CAMINITE_BRICK
    );
    public static final DeferredItem<BlockItem> CAMINITE_FORGE_BLOCK_ITEM = ITEMS.register("caminite_forge_block",
            () -> new CaminiteForgeItemBlock(new Item.Properties())
    );
    public static final DeferredItem<BlockItem> COPPER_ANVIL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            EmbersBlocks.COPPER_ANVIL
    );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
