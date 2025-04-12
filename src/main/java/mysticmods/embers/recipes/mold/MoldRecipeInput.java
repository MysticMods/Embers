package mysticmods.embers.recipes.mold;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.ArrayList;
import java.util.List;

public class MoldRecipeInput implements RecipeInput {

    private final List<ItemStack> inputs;

    public MoldRecipeInput(List<ItemStack> inputs) {
        this.inputs = new ArrayList<>(inputs);
    }

    @Override
    public ItemStack getItem(int index) {
        if (index >= 0 && index < inputs.size()) {
            return inputs.get(index);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return inputs.size();
    }
}