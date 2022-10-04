package mysticmods.embers.api.capability;

import net.minecraft.nbt.IntTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEmberIntensity extends INBTSerializable<IntTag> {

	/**
	 * Gets the amount of intensity for this block to operate
	 *
	 * @return The amount of intensity
	 */
	int getMinIntensity();

	/**
	 * Gets the maximum amount of intensity before this block is considered "overheated"
	 *
	 * @return The intensity at which this machine is overheated
	 */
	int getMaxIntensity();

	/**
	 * Gets the current intensity of the machine
	 *
	 * @return The current machine intensity
	 */
	int getIntensity();

	/**
	 * Sets the intensity of the cap
	 *
	 * @param intensity the intensity to set the block to
	 */
	void setIntensity(int intensity);
}