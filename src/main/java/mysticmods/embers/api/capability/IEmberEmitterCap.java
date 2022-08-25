package mysticmods.embers.api.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface IEmberEmitterCap extends INBTSerializable<CompoundTag> {

	/**
	 * Gets the Ember Intensity supplied by this emitter at the given distance away in blocks
	 * @param distance The distance away from the emitter in blocks
	 * @return The ember intensity at that distance from the emitter.
	 */
	int getIntensityAtDistance(int distance);

	/**
	 * Gets the Ember Intensity for a given block pos relative to the emitter.
	 * @param pos The position to check the intensity for
	 * @return The intensity at the given position as supplied by this emitter.
	 */
	int getIntensityFromBlockPos(BlockPos pos);

	/**
	 * Get the intensity at each distance from the emitter. 0 is the emitter's Block
	 * @return The list of intensities for the given distance, with the length of the array representing the max distance.
	 */
	int[] getIntensities();

	/**
	 * Gets the maximum intensity the Ember emitter can combine to from overlapping ember fields.
	 * @return The maximum combined ember intensity this emitter can support.
	 */
	int getMaxCombinedIntensity();

	/**
	 * Forces the Ember Emitter to update from another emitter. This will allow the emitter calling this to set all new
	 * value from the other's cap being passed as otherEmitter.
	 * @param otherEmitter The emitter triggering the update
	 */
	void combineEmberEmitters(LazyOptional<IEmberEmitterCap> otherEmitter);

	/**
	 * Sets the intensities for this Ember Emitter. This should not exceed the range of the existing intensities, which
	 * should instead be handled by the ember emitter increasing this range. If it does increase range, it will increase
	 * it in all directions.
	 * @param intensities the new list of intensities by range
	 */
	void setIntensities(int[] intensities);

	/**
	 * Checks if the ember emitter is currently producing ember.
	 * @return True if the ember emitter is currently producing ember.
	 */
	boolean isActive();
}
