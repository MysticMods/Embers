package mysticmods.embers.init;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static mysticmods.embers.EmbersMod.ITEMS;

public class EmbersItems {

    public static final DeferredItem<Item> ASHEN_STEEL_INGOT = ITEMS.registerSimpleItem("ashen_steel_ingot", new Item.Properties());
    public static final DeferredItem<Item> CAMINITE_BLEND = ITEMS.registerSimpleItem("caminite_blend", new Item.Properties());
    public static final DeferredItem<Item> CAMINITE_BRICK = ITEMS.registerSimpleItem("caminite_brick", new Item.Properties());
    public static final DeferredItem<Item> CINDERSTEEL_INGOT = ITEMS.registerSimpleItem("cindersteel_ingot", new Item.Properties());
    public static final DeferredItem<Item> CINNABAR = ITEMS.registerSimpleItem("cinnabar", new Item.Properties());
    public static final DeferredItem<Item> DAWNSTONE_INGOT = ITEMS.registerSimpleItem("dawnstone_ingot", new Item.Properties());
    public static final DeferredItem<Item> EMBER_CRYSTAL = ITEMS.registerSimpleItem("ember_crystal", new Item.Properties());
    public static final DeferredItem<Item> QUICKSILVER = ITEMS.registerSimpleItem("quicksilver", new Item.Properties());
    public static final DeferredItem<Item> VERMILLIONITE_CHUNK = ITEMS.registerSimpleItem("vermillionite_chunk", new Item.Properties());

    //todo: VERMILLIONITE tag
    //todo: CINNABAR tag


    public static void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ASHEN_STEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(CAMINITE_BLEND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(CAMINITE_BRICK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(CINDERSTEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(CINNABAR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(DAWNSTONE_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EMBER_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(QUICKSILVER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(VERMILLIONITE_CHUNK.get(), ModelTemplates.FLAT_ITEM);
    }

    public static void init() {
    }
}
