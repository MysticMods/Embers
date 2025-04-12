package mysticmods.embers.recipes.mold;

import mysticmods.embers.init.EmbersRecipeTypes;
import mysticmods.embers.init.EmbersSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MoldRecipe implements Recipe<MoldRecipeInput> {

    private final List<Ingredient> inputIngredients;
    private final ItemStack output;

    public MoldRecipe(List<Ingredient> inputIngredients, ItemStack output) {
        if (inputIngredients.size() > 9 || inputIngredients.isEmpty())
            throw new IllegalArgumentException("MoldRecipe requires between 1 and 9 ingredents.");
        this.inputIngredients = inputIngredients;

        this.output = output;
    }

    @Override
    public boolean matches(MoldRecipeInput input, @NotNull Level level) {
        if (input.size() != inputIngredients.size()) {
            return false;
        }

        boolean[] matched = new boolean[inputIngredients.size()];

        for (int i = 0; i < input.size(); i++) {
            ItemStack inputStack = input.getItem(i);
            boolean foundMatch = false;

            for (int j = 0; j < inputIngredients.size(); j++) {
                if (!matched[j] && inputIngredients.get(j).test(inputStack)) {
                    matched[j] = true;
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                return false;
            }
        }

        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MoldRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return EmbersSerializers.MOLD_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return EmbersRecipeTypes.MOLD.get();
    }

    public List<Ingredient> getInputIngredients() {
        return inputIngredients;
    }

    public ItemStack getOutput() {
        return output;
    }
}
