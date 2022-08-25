package com.mystic.embers.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface EmberEmitterCap extends INBTSerializable<CompoundTag> {

	int getIntensityAtDistance(int distance);

	int[] getAllIntensities();

	int getMaxDistance();

	int getMaxCombinedIntensity();
}
