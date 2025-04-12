package mysticmods.embers.utils;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class BEUtil {

    public static <T extends BlockEntity> void updateViaState(T tile) {
        tile.setChanged();
        Level level = tile.getLevel();
        if (level == null || level.isClientSide()) {
            return;
        }

        BlockState state = level.getBlockState(tile.getBlockPos());
        level.sendBlockUpdated(tile.getBlockPos(), state, state, 8);
    }

    public static <T extends BlockEntity>  void dropItemHandler(T entity, ItemStackHandler itemHandler) {
        if(!entity.getLevel().isClientSide()){
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                if(!itemHandler.getStackInSlot(i).isEmpty()){
                    Block.popResource(entity.getLevel(), entity.getBlockPos(), itemHandler.getStackInSlot(i));
                    itemHandler.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        }
    }

}
