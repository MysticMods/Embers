package com.mystic.embers.core.capability.ember;

import com.mystic.embers.api.IEmberIntensity;
import net.minecraft.nbt.IntTag;

public class EmberIntensity implements IEmberIntensity {

	private int intensity;
	private int maxIntensity;
	private int minIntensity;

	public EmberIntensity(int minIntensity, int maxIntensity) {
		this(0, minIntensity, maxIntensity);
	}

	public EmberIntensity(int intensity, int minIntensity, int maxIntensity) {
		this.intensity = intensity;
		this.maxIntensity = maxIntensity;
		this.minIntensity = minIntensity;
	}

	@Override
	public IntTag serializeNBT() {
		return IntTag.valueOf(intensity);
	}

	@Override
	public void deserializeNBT(IntTag nbt) {
		intensity = nbt.getAsInt();
	}


	@Override
	public int getMinIntensity() {
		return minIntensity;
	}

	@Override
	public int getMaxIntensity() {
		return maxIntensity;
	}

	@Override
	public int getIntensity() {
		return intensity;
	}

	@Override
	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}
}