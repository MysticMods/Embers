package mysticmods.embers.machines.anvil.copper;

import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class CopperAnvilBlockEntity extends LodestoneBlockEntity {
    public CopperAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(EmbersBlockEntities.COPPER_ANVIL.get(), pos, state);
    }
}
