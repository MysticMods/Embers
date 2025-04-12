package mysticmods.embers.recipes.alloy;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class AlloyRecipeInput implements RecipeInput {

    private final ItemStack inputOne;
    private final ItemStack inputTwo;

    public AlloyRecipeInput(ItemStack inputOne, ItemStack inputTwo) {
        this.inputOne = inputOne;
        this.inputTwo = inputTwo;
    }

    @Override
    public ItemStack getItem(int index) {
        if(index == 0) {
            return inputOne;
        } else if(index == 1) {
            return inputTwo;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 2;
    }
}
