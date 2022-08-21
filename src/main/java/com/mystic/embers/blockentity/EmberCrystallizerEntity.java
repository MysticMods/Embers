package com.mystic.embers.blockentity;

import com.mystic.embers.api.TickBlockEntity;
import com.mystic.embers.blockentity.base.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EmberCrystallizerEntity extends BaseBlockEntity implements TickBlockEntity {

	public EmberCrystallizerEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);
	}

	@Override
	public <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState) {

	}

	@Override
	public <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState) {

	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
		CompoundTag tag = pkt.getTag();
		if (tag != null) {
			load(tag);
		} else {

		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag pTag) {
		super.saveAdditional(pTag);
	}

	@Override
	public void load(@NotNull CompoundTag pTag) {
		super.load(pTag);

	}
}
