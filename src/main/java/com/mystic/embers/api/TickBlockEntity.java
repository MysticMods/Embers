package com.mystic.embers.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface TickBlockEntity {

	<T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState);

	<T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState);
}
