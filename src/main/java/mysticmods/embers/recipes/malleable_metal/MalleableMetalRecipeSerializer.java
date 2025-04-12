package mysticmods.embers.recipes.malleable_metal;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class MalleableMetalRecipeSerializer implements RecipeSerializer<MalleableMetalRecipe> {

    public static MapCodec<MalleableMetalRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            MalleableMetal.CODEC.fieldOf("malleable_metal").forGetter(MalleableMetalRecipe::getMalleableMetal),
            ExtraCodecs.POSITIVE_FLOAT.fieldOf("experience").forGetter(MalleableMetalRecipe::getExperience),
            ExtraCodecs.POSITIVE_INT.fieldOf("processingTime").forGetter(MalleableMetalRecipe::getProcessingTime)
    ).apply(inst, MalleableMetalRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MalleableMetalRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    MalleableMetal.STREAM_CODEC, MalleableMetalRecipe::getMalleableMetal,
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
