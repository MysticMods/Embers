package mysticmods.embers.core.capabilities.emberemitter;

import mysticmods.embers.core.capabilities.emberlevel.IEmberLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.NotNull;

public interface IEmberEmitter{

    /**
     * Get the intensity at each distance from the emitter. 0 is the emitter's Block
     *
     * @return The list of intensities for the given distance, with the length of the array representing the max distance.
     */
    int[] getIntensities();

    /**
     * Called to force the emitter to init. This should happen whenever the capability is initialised or when
     * IEmberLevel cap calls it
     *
     * @param ember The ember level to update for
     */
    void initEmitter(@NotNull IEmberLevel ember);

    /**
     * Gets the area the emitter effects. Note this will always be checked with the intensities being centered around
     * the emitter itself.
     *
     * @return The bounding box the emitter effects
     */
    @NotNull
    BoundingBox getBoundingBox();

    @NotNull
    BlockPos getPos();

    /**
     * Determines if a given Ember Emitter is currently active, which controls the initEmitter function
     *
     * @return true if the current emitter is active.
     */
    default boolean isActive() {
        return true;
    }
}