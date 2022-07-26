package com.mystic.embers.menu;

import com.mystic.embers.blockentity.CaminiteForgeEntity;
import com.mystic.embers.init.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class CaminiteForgeMenu extends AbstractContainerMenu {

    private final IItemHandler playerInventory;
    private CaminiteForgeEntity entity;
    public static net.minecraft.network.chat.Component component = new TextComponent("Caminite");

    protected CaminiteForgeMenu(@Nullable CaminiteForgeEntity entity, Inventory playerInventory, int pContainerId) {
        super(ModMenus.CAMINITE_FORGE.get(), pContainerId);
        this.playerInventory = new InvWrapper(playerInventory);
        if(entity != null){
            this.entity = entity;
            entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c ->{
                addSlot(new SlotItemHandler(c, 0, 79, 7));
                addSlot(new SlotItemHandler(c, 1, 79, 58));
            });
        }
        layoutPlayerInventorySlots(8, 84);
    }


    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                if (player.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), player.getLevel()).isPresent()) {
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

    public static CaminiteForgeMenu factory(MenuType<CaminiteForgeMenu> type, int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        BlockEntity entity = inventory.player.level.getBlockEntity(buf.readBlockPos());
        if (entity instanceof CaminiteForgeEntity castBlockEntity)
            return new CaminiteForgeMenu(castBlockEntity, inventory, pContainerId);
        return new CaminiteForgeMenu(null, inventory, pContainerId);
    }

    public static MenuConstructor getServerContainer(CaminiteForgeEntity entity, BlockPos pos){
        return (id, playerInv, player) -> new CaminiteForgeMenu(entity, playerInv, id);

    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(this.playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(this.playerInventory, 0, leftCol, topRow, 9, 18);
    }

    public CaminiteForgeEntity getEntity() {
        return entity;
    }
}
