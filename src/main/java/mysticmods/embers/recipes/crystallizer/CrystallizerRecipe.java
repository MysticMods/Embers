package mysticmods.embers.recipes.crystallizer;

import mysticmods.embers.init.ModRecipeTypes;
import mysticmods.embers.init.ModSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CrystallizerRecipe implements Recipe<RecipeInput> {

    private final Ingredient input;
    private final ItemStack output;

    public CrystallizerRecipe(Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(@NotNull RecipeInput input, @NotNull Level level) {
        if (input.getItem(0).isEmpty()) {
            return false;
        }
        if(this.input.test(input.getItem(0))){
            return true;
        }
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput input, HolderLookup.@NotNull Provider registries) {
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
        return ModSerializers.CRYSTALLIZER_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.CRYSTALLIZER.get();
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
}
