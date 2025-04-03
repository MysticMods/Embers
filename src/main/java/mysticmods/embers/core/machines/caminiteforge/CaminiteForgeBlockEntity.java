package mysticmods.embers.core.machines.caminiteforge;

import mysticmods.embers.api.blocks.EmbersBlockEntity;
import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CaminiteForgeBlockEntity extends EmbersBlockEntity {

    public CaminiteForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.CAMINITE_FORGE.get(), pos, blockState);
    }
}
