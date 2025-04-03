package mysticmods.embers.machines.caminite_forge;

import mysticmods.embers.init.EmbersRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
        var s = entity.getLevel().getRecipeManager().getRecipeFor(EmbersRecipeTypes.MALLEABLE_METAL.get(), CraftingInput.of(1, 1, List.of(getStackInSlot(0))), entity.getLevel());
        return s.isPresent();
    }
}
