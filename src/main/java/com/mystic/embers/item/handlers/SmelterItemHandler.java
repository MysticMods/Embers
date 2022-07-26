package com.mystic.embers.item.handlers;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class SmelterItemHandler extends ItemStackHandler {

    private final BlockEntity entity;
    private final int outputs;

    public SmelterItemHandler(BlockEntity blockEntity, int outputs) {
        this.entity = blockEntity;
        this.outputs = outputs;
    }

    public SmelterItemHandler(int size, int outputs, BlockEntity blockEntity) {
        super(size + outputs);
        this.entity = blockEntity;
        this.outputs = outputs;
    }

    public SmelterItemHandler(NonNullList<ItemStack> stacks, int outputs, BlockEntity blockEntity) {
        super(stacks);
        this.entity = blockEntity;
        this.outputs = outputs;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.entity.setChanged();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        var s = entity.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), entity.getLevel());
        return s.isPresent();
    }
    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if(slot >= this.getSlots() - this.outputs){
            return stack;
        }
        if (entity.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), entity.getLevel()).isEmpty()) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
