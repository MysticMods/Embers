package com.mystic.embers.core.base;

import com.mystic.embers.api.TickBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BaseBlockEntity extends BlockEntity {

	public BaseBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);
	}

	public List<BlockPos> findBlocksInRadius(Block block, BlockPos startingPos, Level level, int radius, int yUp, int yDown) {
		List<BlockPos> positions = new ArrayList<>();
		for (int x = startingPos.getX() - radius; x < startingPos.getX() + radius; x++) {
			for (int z = startingPos.getZ() - radius; z < startingPos.getZ() + radius; z++) {
				for (int y = startingPos.getY() - yDown; y < startingPos.getY() + yUp; y++) {
					if (level.getBlockState(new BlockPos(x, y, z)).getBlock() == block) {
						positions.add(new BlockPos(x, y, z));
					}
				}
			}
		}

		return positions;
	}

	public List<BlockPos> findBlocksWithTagInRadius(TagKey<Block> pTag, BlockPos startingPos, Level level, int radius, int yUp, int yDown) {
		List<BlockPos> positions = new ArrayList<>();
		for (int x = startingPos.getX() - radius; x <= startingPos.getX() + radius; x++) {
			for (int z = startingPos.getZ() - radius; z <= startingPos.getZ() + radius; z++) {
				for (int y = startingPos.getY() - yDown; y < startingPos.getY() + yUp; y++) {
					if (level.getBlockState(new BlockPos(x, y, z)).is(pTag)) {
						positions.add(new BlockPos(x, y, z));
					}
				}
			}
		}

		return positions;
	}

	public static <T extends BlockEntity> void clientTick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
		if (pBlockEntity instanceof TickBlockEntity clientBlockEntity) {
			clientBlockEntity.clientTick(pLevel, pPos, pState);
		}
	}

	public static <T extends BlockEntity> void serverTick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
		if (pBlockEntity instanceof TickBlockEntity serverBlockEntity) {
			serverBlockEntity.serverTick(pLevel, pPos, pState);
		}
	}

	public void updateViaState() {
		setChanged();
		Level world = this.getLevel();
		if (world == null || world.isClientSide()) {
			return;
		}

		BlockState state = world.getBlockState(this.getBlockPos());
		world.sendBlockUpdated(this.getBlockPos(), state, state, 8);
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag pTag = new CompoundTag();
		saveAdditional(pTag);
		return pTag;
	}
}
