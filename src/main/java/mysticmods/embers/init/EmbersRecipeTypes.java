package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.recipes.MalleableMetalRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EmbersRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Embers.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<MalleableMetalRecipe>> MALLEABLE_METAL =
            RECIPE_TYPES.register(
                    "malleable_metal",
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Embers.MODID, "malleable_metal"))
            );



    public static void register(IEventBus bus) {
        RECIPE_TYPES.register(bus);
    }
}
