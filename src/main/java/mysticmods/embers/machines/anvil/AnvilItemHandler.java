package mysticmods.embers.machines.anvil;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AnvilItemHandler extends ItemStackHandler {

    private final BlockEntity entity;

    public AnvilItemHandler(int size, BlockEntity blockEntity) {
        super(size);
        this.entity = blockEntity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.entity.setChanged();
    }

    public ItemStack addItemStack(ItemStack stack) {
        for (int i = 0; i < this.getSlots(); i++) {
            if (this.isItemValid(i, stack)) {
                return this.insertItem(i, stack, false);
            }
        }
        return stack;
    }

    public int getFilledSlotAmount() {
        int filledStacks = 0;
        for (int i = 0; i < this.getSlots(); i++) {
            if (!this.getStackInSlot(i).isEmpty()) {
                filledStacks++;
            }
        }
        return filledStacks;
    }

}