package mysticmods.embers.machines.caminite_forge;

import mysticmods.embers.init.EmbersRecipeTypes;
import mysticmods.embers.recipes.MalleableMetalRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ForgeItemHandler extends ItemStackHandler {
    private final BlockEntity entity;

    public ForgeItemHandler(int size, BlockEntity blockEntity) {
        super(size);
        this.entity = blockEntity;
    }

    public ForgeItemHandler(NonNullList<ItemStack> stacks, int outputs, BlockEntity blockEntity) {
        super(stacks);
        this.entity = blockEntity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.entity.setChanged();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        RecipeManager recipes = entity.getLevel().getRecipeManager();
        Optional<RecipeHolder<MalleableMetalRecipe>> optional = recipes.getRecipeFor(
                EmbersRecipeTypes.MALLEABLE_METAL.get(),
                new SingleRecipeInput(stack),
                entity.getLevel()
        );

        System.out.println(stack.getItem());
        System.out.println(optional.isPresent());

        return optional.isPresent();
    }
}
