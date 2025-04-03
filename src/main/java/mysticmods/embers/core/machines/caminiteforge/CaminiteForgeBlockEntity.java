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

    private final EmberIntensity intensity;

    private float progress = 0;
    private boolean isLit = false;
    private boolean hasHotMetals = false;
    private final int PROGRESS_PER_ITEM = 20 * 5;
    public int progressTimer = 0;

    public CaminiteForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.CAMINITE_FORGE.get(), STRUCTURE.get(), pos, blockState);
        this.intensity = new EmberIntensity(100, 100);
    }


    @Override
    public EmberIntensity getEmberIntensity() {
        return intensity;
    }
}
