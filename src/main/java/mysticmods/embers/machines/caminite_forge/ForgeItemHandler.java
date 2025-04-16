package mysticmods.embers.machines.caminite_forge;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class ForgeItemHandler extends ItemStackHandler {
    private final CaminiteForgeBlockEntity entity;

    public ForgeItemHandler(int size, CaminiteForgeBlockEntity blockEntity) {
        super(size);
        this.entity = blockEntity;
    }

    public ForgeItemHandler(NonNullList<ItemStack> stacks, int outputs, CaminiteForgeBlockEntity blockEntity) {
        super(stacks);
        this.entity = blockEntity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.entity.itemHandlerUpdate();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return slot == 0 || slot == 1;
    }
}
