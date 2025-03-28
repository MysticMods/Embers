package mysticmods.embers.core.machines.brazier;

import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BrazierBlockEntity extends BlockEntity {

    public BrazierBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.BRAZIER.get(), pos, blockState);
    }
}
