package mysticmods.embers.recipes.crystallizer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class CrystallizerRecipeSerializer implements RecipeSerializer<CrystallizerRecipe> {

    public static MapCodec<CrystallizerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("input").forGetter(CrystallizerRecipe::getInput),
            ItemStack.CODEC.fieldOf("output").forGetter(CrystallizerRecipe::getOutput)
    ).apply(inst, CrystallizerRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CrystallizerRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, CrystallizerRecipe::getInput,
                    ItemStack.STREAM_CODEC, CrystallizerRecipe::getOutput,
                    CrystallizerRecipe::new
            );

    @Override
    public @NotNull MapCodec<CrystallizerRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, CrystallizerRecipe> streamCodec() {
        return STREAM_CODEC;
    }

}
