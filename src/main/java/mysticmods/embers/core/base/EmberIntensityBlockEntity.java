package mysticmods.embers.core.base;

import mysticmods.embers.api.capability.IEmberIntensity;
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

public abstract class EmberIntensityBlockEntity extends LodestoneBlockEntity {
	private final LazyOptional<IEmberIntensity> emberIntensityOp = LazyOptional.of(this::getEmberIntensity);

	public EmberIntensityBlockEntity(BlockEntityType<? extends EmberIntensityBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	@NotNull
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
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
	@NotNull
	protected abstract IEmberIntensity getEmberIntensity();

	public void updateViaState() {
		setChanged();
		BlockEntityUtil.updateViaState(this);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("ember", getEmberIntensity().serializeNBT());
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		getEmberIntensity().setIntensity(tag.getInt("ember"));
	}

	@Override
	public void onPlace(@Nullable LivingEntity placer, ItemStack stack) {
		super.onPlace(placer, stack);
		if (level != null && !level.isClientSide) {
			level.getCapability(EmbersCaps.EMBER).ifPresent(ember -> getEmberIntensity().setIntensity(ember.getEmberForPos(getBlockPos())));
			updateViaState();
		}
	}

	@Override
	public void init() {
		super.init();
		if (level != null && !level.isClientSide) {
			level.getCapability(EmbersCaps.EMBER).ifPresent(ember -> ember.addEmberListener(getBlockPos(), emberIntensityOp));
			updateViaState();
		}
	}

	/**
	 * Checks to make sure the current intensity is above the minimum
	 * @return true if the intensity is above the minimum threshold
	 */
	protected boolean hasEmberForOperation() {
		return getEmberIntensity().getIntensity() > getEmberIntensity().getMinIntensity();
	}
}
