package mysticmods.embers.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EmbersTags {
    public static final TagKey<Item> IRON_NUGGETS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "nuggets/iron"));
    public static final TagKey<Item> IRON_INGOTS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/iron"));
    public static final TagKey<Item> IRON_RAW_ORES_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "raw_ores/iron"));

    public static final TagKey<Item> COPPER_NUGGETS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "nuggets/copper"));
    public static final TagKey<Item> COPPER_INGOTS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/copper"));
    public static final TagKey<Item> COPPER_RAW_ORES_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "raw_ores/copper"));

    public static final TagKey<Item> GOLD_NUGGETS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "nuggets/gold"));
    public static final TagKey<Item> GOLD_INGOTS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/gold"));
    public static final TagKey<Item> GOLD_RAW_ORES_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "raw_ores/gold"));


    public static void init(){

    }
}
