package mysticmods.embers.machines.crystallizer;

import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class EmberCrystallizerBlockEntity extends LodestoneBlockEntity {

    public EmberCrystallizerBlockEntity(BlockPos pos, BlockState state) {
        super(EmbersBlockEntities.EMBER_CRYSTALLIZER.get(), pos, state);
    }
}
