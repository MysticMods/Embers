package mysticmods.embers.api.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public interface IEmberEmitter extends INBTSerializable<CompoundTag> {

	/**
	 * Get the intensity at each distance from the emitter. 0 is the emitter's Block
	 * @return The list of intensities for the given distance, with the length of the array representing the max distance.
	 */
	int[] getIntensities();

	/**
	 * Checks if the ember emitter is currently producing ember.
	 * @return True if the ember emitter is currently producing ember.
	 */
	boolean isActive();

	/**
	 * Called to force the emitter to init. This should happen whenever the capability is initialised or when
	 * ILevelEmitter cap calls it
	 */
	void initEmitter();

	/**
	 * Gets the area the emitter effects. Note this will always be checked with the intensities being centered around
	 * the emitter itself.
	 * @return The bounding box the emitter effects
	 */
	@NotNull
	BoundingBox getBoundingBox();
}
