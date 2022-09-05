package mysticmods.embers.core.base;

import mysticmods.embers.api.capability.IEmberEmitter;
import mysticmods.embers.init.EmbersCaps;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import noobanidus.libs.noobutil.util.BlockEntityUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public abstract class EmberEmitterBlockEntity extends LodestoneBlockEntity {
	private final LazyOptional<IEmberEmitter> emitterOp = LazyOptional.of(this::getEmitter);

	public EmberEmitterBlockEntity(BlockEntityType<? extends EmberEmitterBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	@NotNull
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
		if (cap == EmbersCaps.EMBER_EMITTER) {
			return emitterOp.cast();
		}
		return super.getCapability(cap);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		emitterOp.invalidate();
	}

	/**
	 * Gets the Ember Emitter capability for this entity
	 *
	 * @return The ember emitter capability as configured. This should be a singleton.
	 */
	@NotNull
	protected abstract IEmberEmitter getEmitter();

	public void updateViaState() {
		setChanged();
		BlockEntityUtil.updateViaState(this);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("emitter", getEmitter().serializeNBT());
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		getEmitter().deserializeNBT(tag.getCompound("emitter"));
	}

	@Override
	public void onPlace(@Nullable LivingEntity placer, ItemStack stack) {
		super.onPlace(placer, stack);
		if (level != null && !level.isClientSide) {
			level.getCapability(EmbersCaps.EMBER).ifPresent(ember -> ember.addEmitterListener(getEmitter().getBoundingBox(), emitterOp));
			setChanged();
		}
	}
}
