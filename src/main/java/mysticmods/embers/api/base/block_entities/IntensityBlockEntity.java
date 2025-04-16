package mysticmods.embers.api.base.block_entities;

import mysticmods.embers.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.capabilities.emberlevel.EmberLevel;
import mysticmods.embers.utils.SDUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import static mysticmods.embers.utils.BEUtil.updateViaState;

public class IntensityBlockEntity extends LodestoneBlockEntity {

    protected final EmberIntensity intensity;
    protected EmberLevel emberLevel;

    public IntensityBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int minIntensity, int maxIntensity) {
        super(type, pos, state);

        this.intensity = new EmberIntensity(minIntensity, maxIntensity, this::updateToClient);
    }

    @Override
    public void onLoad() {
        this.emberLevel = SDUtil.getLevelEmbersData(level);
        if (this.emberLevel != null) {
            this.emberLevel.addEmberListener(getBlockPos(), this.intensity);
        }

        updateViaState(this);
    }

    public void updateToClient() {
        if (!level.isClientSide()) {
            updateViaState(this);
        }
    }

    public EmberIntensity getIntensity() {
        return intensity;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        super.onBreak(player);
        if (emberLevel != null) {
            emberLevel.removeEmberListener(getBlockPos(), this.intensity);
        }
    }
}
