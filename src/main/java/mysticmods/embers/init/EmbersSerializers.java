package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.recipes.alloy.AlloyRecipeSerializer;
import mysticmods.embers.recipes.malleable_metal.MalleableMetalRecipeSerializer;
import mysticmods.embers.recipes.mold.MoldRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EmbersSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Embers.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, MalleableMetalRecipeSerializer> MALLEABLE_METAL_SERIALIZER =
            RECIPE_SERIALIZERS.register("malleable_metal", MalleableMetalRecipeSerializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, AlloyRecipeSerializer> ALLOY_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("alloy_recipe", AlloyRecipeSerializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, MoldRecipeSerializer> MOLD_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("mold_recipe", MoldRecipeSerializer::new);

    public static void register(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
    }
}
