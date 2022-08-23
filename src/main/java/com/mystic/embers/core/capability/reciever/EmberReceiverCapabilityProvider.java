package com.mystic.embers.core.capability.reciever;

import com.mystic.embers.core.capability.EmbersCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EmberReceiverCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

	private final IEmberReceiverCapability emberReceiverCapability = new EmberReceiverCapability();
	private final LazyOptional<IEmberReceiverCapability> op = LazyOptional.of(() -> emberReceiverCapability);

	public void invalidate() {
		this.op.invalidate();
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction facing) {
		return EmbersCapabilities.EMBER_RECEIVER_CAPABILITY.orEmpty(cap, op);
	}

	@Override
	public CompoundTag serializeNBT() {
		return this.emberReceiverCapability.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.emberReceiverCapability.deserializeNBT(tag);
	}
}
