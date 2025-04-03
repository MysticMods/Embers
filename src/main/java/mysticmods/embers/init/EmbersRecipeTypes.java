package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.recipes.MalleableMetalRecipe;
import mysticmods.embers.recipes.MalleableMetalRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EmbersRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Embers.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Embers.MODID);

    public static final Supplier<RecipeType<MalleableMetalRecipe>> MALLEABLE_METAL =
            RECIPE_TYPES.register(
                    "malleable_metal",
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Embers.MODID, "malleable_metal"))
            );


    public static final Supplier<RecipeSerializer<MalleableMetalRecipe>> MALLEABLE_METAL_SERIALIZER =
            RECIPE_SERIALIZERS.register("malleable_metal", MalleableMetalRecipeSerializer::new);

    public static void init() {
    }
}
