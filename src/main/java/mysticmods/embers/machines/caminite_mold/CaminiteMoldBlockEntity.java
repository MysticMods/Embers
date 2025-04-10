package mysticmods.embers.machines.caminite_mold;

import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class CaminiteMoldBlockEntity extends LodestoneBlockEntity {

    private final ItemStackHandler itemHandler;

    public CaminiteMoldBlockEntity(BlockPos pos, BlockState state) {
        super(EmbersBlockEntities.CAMINITE_MOLD.get(), pos, state);

        itemHandler = new ItemStackHandler(9) {

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                CaminiteMoldBlockEntity.this.onContentsChanged();
                super.onContentsChanged(slot);
            }
        };
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
        if (pStack.isEmpty()) {
            return super.onUseWithItem(pPlayer, pStack, pHand);
        }
        //Add the item to the first avilable slot
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            ItemStack itemStack = this.itemHandler.getStackInSlot(i);
            if (itemStack.isEmpty()) {
                this.itemHandler.setStackInSlot(i, pStack.split(1));
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(pPlayer, pStack, pHand);
    }

    @Override
    public InteractionResult onUseWithoutItem(Player pPlayer) {
        if(!pPlayer.isCrouching()){
            return super.onUseWithoutItem(pPlayer);
        }

        for (int i = this.itemHandler.getSlots() -1; i >= 0; i--) {
            ItemStack returnItem = this.itemHandler.getStackInSlot(i);
            if(!returnItem.isEmpty()) {
                if (!pPlayer.getInventory().add(returnItem)) {
                    pPlayer.drop(returnItem, false);
                }
                this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }
        }

        return super.onUseWithoutItem(pPlayer);
    }

    public void onContentsChanged(){

    }

    public @Nullable IItemHandler getItemHandler() {
        return this.itemHandler;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("ItemHandler", itemHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("ItemHandler")) {
            itemHandler.deserializeNBT(registries, tag.getCompound("ItemHandler"));
        }
    }
}
