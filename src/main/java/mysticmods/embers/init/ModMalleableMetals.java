package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.api.registries.EmbersRegistries;
import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMalleableMetals {
    public static final DeferredRegister<MalleableMetal> MALLEABLE_METAL = DeferredRegister.create(EmbersRegistries.Keys.MALLEABLE_METALS, Embers.MODID);

    public static final DeferredHolder<MalleableMetal, MalleableMetal> MALLEABLE_IRON = MALLEABLE_METAL.register("malleable_iron", () -> new MalleableMetal(
            ModTags.IRON_ORES_TAG,
            Ingredient.of(ModTags.IRON_NUGGETS_TAG),
            Ingredient.of(ModTags.IRON_INGOTS_TAG),
            Ingredient.of(ModTags.IRON_RAW_ORES_TAG)
    ));

    public static final DeferredHolder<MalleableMetal, MalleableMetal> MALLEABLE_COPPER = MALLEABLE_METAL.register("malleable_copper", () -> new MalleableMetal(
            ModTags.COPPER_ORES_TAG,
            Ingredient.of(ModTags.COPPER_NUGGETS_TAG),
            Ingredient.of(ModTags.COPPER_INGOTS_TAG),
            Ingredient.of(ModTags.COPPER_RAW_ORES_TAG)
    ));

    public static final DeferredHolder<MalleableMetal, MalleableMetal> MALLEABLE_GOLD = MALLEABLE_METAL.register("malleable_gold", () -> new MalleableMetal(
            ModTags.GOLD_ORES_TAG,
            Ingredient.of(ModTags.GOLD_NUGGETS_TAG),
            Ingredient.of(ModTags.GOLD_INGOTS_TAG),
            Ingredient.of(ModTags.GOLD_RAW_ORES_TAG)
    ));

    public static void register(IEventBus bus) {
        MALLEABLE_METAL.register(bus);
    }

}
