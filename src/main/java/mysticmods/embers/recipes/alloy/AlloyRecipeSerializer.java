package mysticmods.embers.recipes.alloy;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class AlloyRecipeSerializer implements RecipeSerializer<AlloyRecipe> {

    public static MapCodec<AlloyRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("metalOne").forGetter(AlloyRecipe::getMetalOne),
            Ingredient.CODEC.fieldOf("metalTwo").forGetter(AlloyRecipe::getMetalTwo),
            ItemStack.CODEC.fieldOf("output").forGetter(AlloyRecipe::getOutput)
    ).apply(inst, AlloyRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AlloyRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, AlloyRecipe::getMetalOne,
                    Ingredient.CONTENTS_STREAM_CODEC, AlloyRecipe::getMetalTwo,
                    ItemStack.STREAM_CODEC, AlloyRecipe::getOutput,
                    AlloyRecipe::new
            );

    @Override
    public @NotNull MapCodec<AlloyRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, AlloyRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
