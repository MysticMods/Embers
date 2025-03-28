package mysticmods.embers.core.utils;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityUtil {

    public static <T extends BlockEntity> void updateViaState(T tile) {
        Level level = tile.getLevel();
        if (level == null || level.isClientSide()) {
            return;
        }

        BlockState state = level.getBlockState(tile.getBlockPos());
        level.sendBlockUpdated(tile.getBlockPos(), state, state, 8);
    }

}
