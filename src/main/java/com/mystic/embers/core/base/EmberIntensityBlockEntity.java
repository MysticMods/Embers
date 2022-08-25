package com.mystic.embers.core.base;

import com.mystic.embers.api.EmbersTags;
import com.mystic.embers.api.IEmberIntensity;
import com.mystic.embers.core.capability.ember.EmberIntensity;
import com.mystic.embers.core.machines.diffuser.EmberDiffuserEntity;
import com.mystic.embers.core.utils.BlockFinder;
import com.mystic.embers.init.EmbersCaps;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import noobanidus.libs.noobutil.util.BlockEntityUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class EmberIntensityBlockEntity extends BlockEntity {
	private final LazyOptional<IEmberIntensity> emberIntensityOp = LazyOptional.of(this::getEmberIntensity);
	protected BlockPos generatorPosition = null;

	public EmberIntensityBlockEntity(BlockEntityType<? extends EmberIntensityBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
		if (cap == EmbersCaps.EMBER_INTENSITY) {
			return emberIntensityOp.cast();
		}
		return super.getCapability(cap);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		emberIntensityOp.invalidate();
	}

	/**
	 * Gets the Ember Intensity capability for this entity
	 * @return The ember intensity capability as configured. This should be a singleton.
	 */
	@Nonnull
	public abstract IEmberIntensity getEmberIntensity();

	public void updateViaState() {
		setChanged();
		BlockEntityUtil.updateViaState(this);
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Nonnull
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag pTag = new CompoundTag();
		saveAdditional(pTag);
		return pTag;
	}

	public int getGeneratorEmberOutput() {
		if (this.generatorPosition != null && level != null) {
			BlockEntity blockEntity = level.getBlockEntity(this.generatorPosition);
			if (blockEntity instanceof EmberDiffuserEntity entity) {
				return entity.getEmberOutputForMachine(this.getBlockPos());
			}
		}
		return 0;
	}

	public void findGenerator(BlockPos blockPos) {
		List<BlockPos> generators = BlockFinder.findBlocksWithTagInRadius(EmbersTags.Blocks.EMBER_GENERATOR, blockPos, level, 10, 3, 3);
		BlockPos bestGenerator = null;
		int bestEmberSource = 0;
		for (BlockPos pos : generators) {
			if (level != null) {
				BlockEntity blockEntity = level.getBlockEntity(pos);
				if (blockEntity instanceof EmberDiffuserEntity entity) {
					int emberSource = entity.getEmberOutputForMachine(blockPos);
					if (emberSource > bestEmberSource) { //TODO: Add distance check for closest generator
						bestEmberSource = emberSource;
						bestGenerator = pos;
					}
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
