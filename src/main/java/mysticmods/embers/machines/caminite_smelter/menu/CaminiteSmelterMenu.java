package mysticmods.embers.machines.caminite_smelter.menu;

import mysticmods.embers.init.EmbersMenuTypes;
import mysticmods.embers.machines.caminite_smelter.CaminiteSmelterBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class CaminiteSmelterMenu extends AbstractContainerMenu {
    private final CaminiteSmelterBlockEntity blockEntity;
    private final ContainerLevelAccess containerLevelAccess;

    // Client constructor
    public CaminiteSmelterMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        this(windowId, playerInventory, retrieveBlockEntityFromWorld(playerInventory, data));
    }

    // Server constructor
    public CaminiteSmelterMenu(int windowId, Inventory playerInventory, CaminiteSmelterBlockEntity blockEntity) {
        super(EmbersMenuTypes.CAMINITE_FORGE.get(), windowId);
        this.blockEntity = blockEntity;
        this.containerLevelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        // Add the input slot
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 79, 16) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return blockEntity.getItemHandler().isItemValid(0, stack);
            }
        });

        // Add the output slot
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 2, 138, 39) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false; // Output slot, can't place items
            }
        });

        // Add player inventory slots
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 93 + row * 18));
            }
        }

        // Add player hotbar slots
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 151));
        }
    }

    public static CaminiteSmelterBlockEntity retrieveBlockEntityFromWorld(Inventory playerInventory, FriendlyByteBuf data) {
        BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (blockEntity instanceof CaminiteSmelterBlockEntity) {
            return (CaminiteSmelterBlockEntity) blockEntity;
        }
        throw new IllegalStateException("Block entity is not correct! " + blockEntity);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.containerLevelAccess, player, blockEntity.getBlockState().getBlock());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < 2) {
                if (!this.moveItemStackTo(itemstack1, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public CaminiteSmelterBlockEntity getBlockEntity() {
        return blockEntity;
    }
}