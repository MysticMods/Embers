package mysticmods.embers.core.capability.intensity;

import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.init.EmbersCaps;
import net.minecraft.core.Direction;
import net.minecraft.nbt.IntTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EmberIntensityProvider implements ICapabilitySerializable<IntTag> {

	private final IEmberIntensity ember;
	private final LazyOptional<IEmberIntensity> op;

	public EmberIntensityProvider(int min, int max) {
		this.ember = new EmberIntensity(min, max);
		this.op = LazyOptional.of(() -> this.ember);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction facing) {
		return EmbersCaps.EMBER_INTENSITY.orEmpty(cap, op);
	}

	@Override
	public IntTag serializeNBT() {
		return this.ember.serializeNBT();
	}

	@Override
	public void deserializeNBT(IntTag tag) {
		this.ember.deserializeNBT(tag);
	}
}