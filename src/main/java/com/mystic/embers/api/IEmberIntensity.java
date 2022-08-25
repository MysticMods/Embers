package com.mystic.embers.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEmberIntensity extends INBTSerializable<IntTag> {

	int getMinIntensity();

	int getMaxIntensity();

	int getIntensity();

	void setIntensity(int intensity);
}