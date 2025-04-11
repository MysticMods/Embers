package mysticmods.embers.recipes.mold;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class MoldRecipeSerializer implements RecipeSerializer<MoldRecipe> {

    public static MapCodec<MoldRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemStack.CODEC.fieldOf("output").forGetter(MoldRecipe::getOutput),
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(MoldRecipe::getInputIngredients)
    ).apply(inst, MoldRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MoldRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.STREAM_CODEC, MoldRecipe::getOutput,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), MoldRecipe::getInputIngredients,
                    MoldRecipe::new
            );

    @Override
    public @NotNull MapCodec<MoldRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, MoldRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
