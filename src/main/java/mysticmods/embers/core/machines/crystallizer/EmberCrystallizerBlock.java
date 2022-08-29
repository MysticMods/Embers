package mysticmods.embers.core.machines.crystallizer;

import mysticmods.embers.core.utils.TickBlockEntity;
import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EmberCrystallizerBlock extends Block implements EntityBlock {


	public EmberCrystallizerBlock(Properties pProperties) {
		super(pProperties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
		return new EmberCrystallizerEntity(EmbersBlockEntities.EMBER_DIFFUSER.get(), pPos, pState);

	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
		if (level.isClientSide()) {
			return TickBlockEntity::clientTick;
		} else {
			return TickBlockEntity::serverTick;
		}
	}
}