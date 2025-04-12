package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.api.EmbersRegistries;
import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EmbersMalleableMetals {
    public static final DeferredRegister<MalleableMetal> MALLEABLE_METAL = DeferredRegister.create(EmbersRegistries.Keys.MALLEABLE_METALS, Embers.MODID);

    public static final DeferredHolder<MalleableMetal, MalleableMetal> MALLEABLE_IRON = MALLEABLE_METAL.register("malleable_iron", () -> new MalleableMetal(
            EmbersTags.IRON_ORES_TAG,
            Ingredient.of(EmbersTags.IRON_NUGGETS_TAG),
            Ingredient.of(EmbersTags.IRON_INGOTS_TAG),
            Ingredient.of(EmbersTags.IRON_RAW_ORES_TAG)
    ));

    public static final DeferredHolder<MalleableMetal, MalleableMetal> MALLEABLE_COPPER = MALLEABLE_METAL.register("malleable_copper", () -> new MalleableMetal(
            EmbersTags.COPPER_ORES_TAG,
            Ingredient.of(EmbersTags.COPPER_NUGGETS_TAG),
            Ingredient.of(EmbersTags.COPPER_INGOTS_TAG),
            Ingredient.of(EmbersTags.COPPER_RAW_ORES_TAG)
    ));

    public static final DeferredHolder<MalleableMetal, MalleableMetal> MALLEABLE_GOLD = MALLEABLE_METAL.register("malleable_gold", () -> new MalleableMetal(
            EmbersTags.GOLD_ORES_TAG,
            Ingredient.of(EmbersTags.GOLD_NUGGETS_TAG),
            Ingredient.of(EmbersTags.GOLD_INGOTS_TAG),
            Ingredient.of(EmbersTags.GOLD_RAW_ORES_TAG)
    ));

    public static void register(IEventBus bus) {
        MALLEABLE_METAL.register(bus);
    }

}
