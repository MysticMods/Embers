package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.registries.MalleableMetal;
import mysticmods.embers.registries.MalleableMetalRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EmbersMalleableMetals {
    public static final DeferredRegister<MalleableMetal> MALLEABLE_METAL = DeferredRegister.create(MalleableMetalRegistry.MALLEABLE_METAL_REGISTRY, Embers.MODID);

    public static final Supplier<MalleableMetal> MALLEABLE_IRON = MALLEABLE_METAL.register("malleable_iron", () -> new MalleableMetal(
            Blocks.IRON_ORE,
            Ingredient.of(EmbersTags.IRON_NUGGETS_TAG),
            Ingredient.of(EmbersTags.IRON_INGOTS_TAG),
            Ingredient.of(EmbersTags.IRON_RAW_ORES_TAG)
    ));

    public static final Supplier<MalleableMetal> MALLEABLE_COPPER = MALLEABLE_METAL.register("malleable_copper", () -> new MalleableMetal(
            Blocks.COPPER_ORE,
            Ingredient.of(EmbersTags.COPPER_NUGGETS_TAG),
            Ingredient.of(EmbersTags.COPPER_INGOTS_TAG),
            Ingredient.of(EmbersTags.COPPER_RAW_ORES_TAG)
    ));

    public static final Supplier<MalleableMetal> MALLEABLE_GOLD = MALLEABLE_METAL.register("malleable_gold", () -> new MalleableMetal(
            Blocks.GOLD_ORE,
            Ingredient.of(EmbersTags.GOLD_NUGGETS_TAG),
            Ingredient.of(EmbersTags.GOLD_INGOTS_TAG),
            Ingredient.of(EmbersTags.GOLD_RAW_ORES_TAG)
    ));

}
