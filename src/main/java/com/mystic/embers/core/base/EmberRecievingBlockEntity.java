package com.mystic.embers.core.base;

import com.mystic.embers.api.EmbersTags;
import com.mystic.embers.core.machines.diffuser.EmberDiffuserEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class EmberRecievingBlockEntity extends BaseBlockEntity {
	protected BlockPos generatorPosition = null;

	public EmberRecievingBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);
	}

	public int getGeneratorEmberOutput() {
		if (this.generatorPosition != null) {
			BlockEntity blockEntity = level.getBlockEntity(this.generatorPosition);
			if (blockEntity instanceof EmberDiffuserEntity entity) {
				return entity.getEmberOutputForMachine(this.getBlockPos());
			}
		}
		return 0;
	}

	public void findGenerator(BlockPos blockPos) {
		List<BlockPos> generators = findBlocksWithTagInRadius(EmbersTags.Blocks.EMBER_GENERATOR, blockPos, level, 10, 3, 3);
		BlockPos bestGenerator = null;
		int bestEmberSource = 0;
		for (BlockPos pos : generators) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof EmberDiffuserEntity entity) {
				int emberSource = entity.getEmberOutputForMachine(blockPos);
				if (emberSource > bestEmberSource) { //TODO: Add distance check for closest generator
					bestEmberSource = emberSource;
					bestGenerator = pos;
				}
			}
		}
		if (bestGenerator != null) {
			this.generatorPosition = bestGenerator;
			updateViaState();
		}
	}

	public void saveGenerator(CompoundTag pTag) {
		if (this.generatorPosition != null) {
			CompoundTag t = new CompoundTag();
			t.putInt("x", this.generatorPosition.getX());
			t.putInt("y", this.generatorPosition.getY());
			t.putInt("z", this.generatorPosition.getZ());
			pTag.put("generator_position", t);
		}
	}

	public void loadGenerator(CompoundTag pTag) {
		CompoundTag generator_position = (CompoundTag) pTag.get("generator_position");
		if (generator_position != null) {
			this.generatorPosition = new BlockPos(generator_position.getInt("x"), generator_position.getInt("y"), generator_position.getInt("z"));
		}
	}
}
