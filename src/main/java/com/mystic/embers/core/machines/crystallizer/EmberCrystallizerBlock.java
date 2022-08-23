package com.mystic.embers.core.machines.crystallizer;

import com.mystic.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EmberCrystallizerBlock extends Block implements EntityBlock {


	public EmberCrystallizerBlock(Properties pProperties) {
		super(pProperties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new EmberCrystallizerEntity(EmbersBlockEntities.EMBER_DIFFUSER.get(), pPos, pState);

	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if (level.isClientSide()) {
			return EmberCrystallizerEntity::clientTick;
		} else {
			return EmberCrystallizerEntity::serverTick;
		}
	}
}
