package mysticmods.embers.machines.caminite_forge;

import mysticmods.embers.init.EmbersRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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
//        Optional<RecipeHolder<? extends Recipe<CraftingInput>>> optional = recipes.getRecipeFor(
//                // The recipe type to get the recipe for. In our case, we use the crafting type.
//                RecipeTypes.CRAFTING,
//                // Our recipe input.
//                input,
//                // Our level context.
//                serverLevel
//        );

        var s = entity.getLevel().getRecipeManager().getRecipeFor(EmbersRecipeTypes.MALLEABLE_METAL.get(), CraftingInput.of(1, 1, List.of(getStackInSlot(0))), entity.getLevel());
        return s.isPresent();
    }
}
