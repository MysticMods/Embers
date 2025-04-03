package mysticmods.embers.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class MalleableMetalRecipeSerializer implements RecipeSerializer<MalleableMetalRecipe> {

    public static MapCodec<MalleableMetalRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("input").forGetter(MalleableMetalRecipe::getInput),
            ItemStack.CODEC.fieldOf("output").forGetter(MalleableMetalRecipe::getOutput),
            ExtraCodecs.POSITIVE_FLOAT.fieldOf("experience").forGetter(MalleableMetalRecipe::getExperience),
            ExtraCodecs.POSITIVE_INT.fieldOf("processingTime").forGetter(MalleableMetalRecipe::getProcessingTime)
    ).apply(inst, MalleableMetalRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MalleableMetalRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, MalleableMetalRecipe::getInput,
                    ItemStack.STREAM_CODEC, MalleableMetalRecipe::getOutput,
                    ByteBufCodecs.FLOAT, MalleableMetalRecipe::getExperience,
                    ByteBufCodecs.VAR_INT, MalleableMetalRecipe::getProcessingTime,
                    MalleableMetalRecipe::new
            );

    @Override
    public @NotNull MapCodec<MalleableMetalRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, MalleableMetalRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
