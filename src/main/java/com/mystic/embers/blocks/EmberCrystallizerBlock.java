package com.mystic.embers.blocks;

import com.mystic.embers.blockentity.EmberCrystallizerEntity;
import com.mystic.embers.init.ModBlockEntity;
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
		return new EmberCrystallizerEntity(ModBlockEntity.EMBER_DIFFUSER.get(), pPos, pState);

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
