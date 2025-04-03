package mysticmods.embers.core.machines.caminiteforge;

import mysticmods.embers.core.base.IEmberIntesityEntity;
import mysticmods.embers.core.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.init.EmbersBlockEntities;
import mysticmods.embers.init.EmbersBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.multiblock.MultiBlockCoreEntity;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

import java.util.function.Supplier;

public class CaminiteForgeBlockEntity extends MultiBlockCoreEntity implements IEmberIntesityEntity {

    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, EmbersBlocks.CAMINITE_FORGE_COMPONENT.get().defaultBlockState()));


    public CaminiteForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.CAMINITE_FORGE.get(), STRUCTURE.get(), pos, blockState);
    }


    @Override
    public EmberIntensity getEmberIntensity() {
        return null;
    }
}
