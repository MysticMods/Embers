package mysticmods.embers.core.machines.anvil.copper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;

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

	public ItemStack getLastFilledSlot(){
		for (int i = this.getSlots() - 1; i >= 0; i--) {
			if (!this.getStackInSlot(i).isEmpty()) {
				return this.extractItem(i, this.getStackInSlot(i).getCount(), false);
			}
		}
		return ItemStack.EMPTY;
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
