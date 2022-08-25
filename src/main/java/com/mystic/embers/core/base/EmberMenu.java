package com.mystic.embers.core.base;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class EmberMenu<T extends BlockEntity> extends AbstractContainerMenu {

	private final IItemHandler playerInventory;
	protected T entity;

	protected EmberMenu(@Nullable MenuType<?> pMenuType, @Nullable T entity, Inventory playerInventory, int pContainerId) {
		super(pMenuType, pContainerId);
		this.entity = entity;
		this.playerInventory = new InvWrapper(playerInventory);

	}


	@Override
	public boolean stillValid(@Nonnull Player pPlayer) {
		return true;
	}

	public abstract boolean canQuickMoveStack(Player player, ItemStack stack);

	@Override
	@Nonnull
	public ItemStack quickMoveStack(@Nonnull Player player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack stack = slot.getItem();
			itemstack = stack.copy();
			if (index == 0) {
				if (!this.moveItemStackTo(stack, 1, 37, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stack, itemstack);
			} else {
				if (canQuickMoveStack(player, stack)) { //TODO stack?
					if (!this.moveItemStackTo(stack, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 28) {
					if (!this.moveItemStackTo(stack, 28, 37, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (stack.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, stack);
		}

		return itemstack;
	}

	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
		for (int i = 0; i < amount; i++) {
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}

	private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
		for (int j = 0; j < verAmount; j++) {
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
		return index;
	}

	protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
		// Player inventory
		addSlotBox(this.playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

		// Hotbar
		topRow += 58;
		addSlotRange(this.playerInventory, 0, leftCol, topRow, 9, 18);
	}

	public T getEntity() {
		return entity;
	}
}
