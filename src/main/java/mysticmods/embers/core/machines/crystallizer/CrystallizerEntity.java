package mysticmods.embers.core.machines.crystallizer;

import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.core.base.EmberIntensityBlockEntity;
import mysticmods.embers.core.capability.intensity.EmberIntensity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CrystallizerEntity extends EmberIntensityBlockEntity {

	private final IEmberIntensity ember = new EmberIntensity(100, 100);

	public CrystallizerEntity(BlockEntityType<? extends CrystallizerEntity> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);
	}

	@Override
	@NotNull
	protected IEmberIntensity getEmberIntensity() {
		return ember;
	}
}
