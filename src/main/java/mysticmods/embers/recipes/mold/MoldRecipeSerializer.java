package mysticmods.embers.recipes.mold;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mysticmods.embers.recipes.malleable_metal.MalleableMetalRecipe;
import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MoldRecipeSerializer implements RecipeSerializer<MoldRecipe> {

    public static MapCodec<MoldRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemStack.CODEC.fieldOf("output").forGetter(MoldRecipe::getOutput),
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(MoldRecipe::getInputIngredients)
    ).apply(inst, MoldRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MoldRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.STREAM_CODEC, MoldRecipe::getOutput,
                    MoldRecipeSerializer.INGREDIENT_LIST_STREAM_CODEC, MoldRecipe::getInputIngredients,
                    MoldRecipe::new
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Ingredient>> INGREDIENT_LIST_STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public void encode(RegistryFriendlyByteBuf buf, List<Ingredient> value) {
                    ByteBufCodecs.VAR_INT.encode(buf, value.size());
                    for (Ingredient ingredient : value) {
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
                    }
                }

                @Override
                public List<Ingredient> decode(RegistryFriendlyByteBuf buf) {
                    int size = ByteBufCodecs.VAR_INT.decode(buf);
                    List<Ingredient> ingredients = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        ingredients.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                    }
                    return ingredients;
                }
            };

    @Override
    public @NotNull MapCodec<MoldRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, MoldRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
