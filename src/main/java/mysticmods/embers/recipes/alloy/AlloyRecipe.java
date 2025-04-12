package mysticmods.embers.recipes.alloy;

import mysticmods.embers.init.ModRecipeTypes;
import mysticmods.embers.init.ModSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AlloyRecipe implements Recipe<AlloyRecipeInput> {

    private final Ingredient metalOne;
    private final Ingredient metalTwo;
    private final ItemStack output;

    public AlloyRecipe(Ingredient metalOne, Ingredient metalTwo, ItemStack output) {
        this.metalOne = metalOne;
        this.metalTwo = metalTwo;
        this.output = output;
    }

    @Override
    public boolean matches(AlloyRecipeInput input, @NotNull Level level) {
        if(this.metalOne.test(input.getItem(0)) && this.metalTwo.test(input.getItem(1))) {
            return true;
        } else return this.metalOne.test(input.getItem(1)) && this.metalTwo.test(input.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull AlloyRecipeInput input, HolderLookup.@NotNull Provider registries) {
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
        return ModSerializers.ALLOY_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.ALLOY.get();
    }

    public Ingredient getMetalOne() {
        return metalOne;
    }

    public Ingredient getMetalTwo() {
        return metalTwo;
    }

    public ItemStack getOutput() {
        return output;
    }
}
