package mysticmods.embers.core.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public interface TickBlockEntity {

	static <T extends BlockEntity> void clientTick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
		if (pBlockEntity instanceof TickBlockEntity clientBlockEntity) {
			clientBlockEntity.clientTick(pLevel, pPos, pState);
		}
	}

	static <T extends BlockEntity> void serverTick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
		if (pBlockEntity instanceof TickBlockEntity serverBlockEntity) {
			serverBlockEntity.serverTick(pLevel, pPos, pState);
		}
	}

	static <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level) {
		if (level.isClientSide()) {
			return TickBlockEntity::clientTick;
		} else {
			return TickBlockEntity::serverTick;
		}
	}

	default void clientTick(Level level, BlockPos blockPos, BlockState blockState) {
	}

	default void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
	}
}
