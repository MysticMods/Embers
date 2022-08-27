package mysticmods.embers.core.base;

import mysticmods.embers.api.capability.IEmberEmitter;
import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.core.config.EmbersConfig;
import mysticmods.embers.core.utils.BlockFinder;
import mysticmods.embers.init.EmbersCaps;
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
import java.util.Objects;

public abstract class EmberIntensityBlockEntity extends BlockEntity {
	private final LazyOptional<IEmberIntensity> emberIntensityOp = LazyOptional.of(this::getEmberIntensity);
	private LazyOptional<IEmberEmitter> emberEmitter;

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
	 *
	 * @return The ember intensity capability as configured. This should be a singleton.
	 */
	@Nonnull
	protected abstract IEmberIntensity getEmberIntensity();

	/**
	 * Called when the block is no longer under the effect of Ember. Can be used for a drop off function or other behaviour
	 */
	protected void invalidateEmberIntensity() {
		getEmberIntensity().setIntensity(0);
	}

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
		CompoundTag tag = new CompoundTag();
		saveAdditional(tag);
		return tag;
	}

	public void findEmitter(BlockPos blockPos) {
		if (level != null) {
			BlockFinder.getEmberEmitterWithinRange(blockPos, level, EmbersConfig.SERVER_CONFIG.EMITTER_SEARCH_RANGE.get()).ifPresentOrElse(pos -> {
				getEmitterFromPos(pos);
				emberEmitter.ifPresent(emitter -> getEmberIntensity().setIntensity(emitter.getIntensityFromBlockPos(this.getBlockPos())));
			}, this::invalidateEmberIntensity);
		}
	}

	private void getEmitterFromPos(BlockPos pos) {
		if (level != null) {
			emberEmitter = Objects.requireNonNull(level.getBlockEntity(pos)).getCapability(EmbersCaps.EMBER_EMITTER);
			emberEmitter.addListener(this::onEmberEmitterInvalidated);
		}
	}

	protected void onEmberEmitterInvalidated(LazyOptional<IEmberEmitter> invalidEmitter) {
		findEmitter(getBlockPos());
	}

	protected LazyOptional<IEmberEmitter> getEmberEmitter() {
		return emberEmitter;
	}

	private void saveEmitterToPos(CompoundTag tag) {
		getEmberEmitter().ifPresent(e -> e.getPos().ifPresent(pos -> {
			CompoundTag t = new CompoundTag();
			t.putInt("x", pos.getX());
			t.putInt("y", pos.getY());
			t.putInt("z", pos.getZ());
			tag.put("emitter_pos", t);
		}));
	}

	private void loadEmitterFromPos(CompoundTag tag) {
		var emitterPos = tag.getCompound("emitter_pos");
		if (emitterPos.contains("x") && emitterPos.contains("y") && emitterPos.contains("z")) {
			getEmitterFromPos(new BlockPos(emitterPos.getInt("x"), emitterPos.getInt("y"), emitterPos.getInt("z")));
		} else {
			findEmitter(getBlockPos());
		}
	}

	@Override
	protected void saveAdditional(@Nonnull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("ember_intensity", getEmberIntensity().serializeNBT());
		saveEmitterToPos(tag);
	}

	@Override
	public void load(@Nonnull CompoundTag tag) {
		super.load(tag);
		getEmberIntensity().setIntensity(tag.getInt("ember_intensity"));
		loadEmitterFromPos(tag);
	}
}
