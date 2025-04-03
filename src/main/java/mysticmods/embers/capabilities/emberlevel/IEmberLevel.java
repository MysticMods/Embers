package mysticmods.embers.capabilities.emberlevel;

import mysticmods.embers.capabilities.emberemitter.IEmberEmitter;
import mysticmods.embers.capabilities.emberintensity.IEmberIntensity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.NotNull;

public interface IEmberLevel {
    int getEmberForPos(@NotNull BlockPos pos);

    void setEmberForPos(@NotNull BlockPos pos, int ember);

    void setEmberForRadius(@NotNull BlockPos center, int[] emberPerRadius);

    void addEmberListener(@NotNull BlockPos pos, @NotNull IEmberIntensity intensity);

    void removeEmberListener(@NotNull BlockPos pos, @NotNull IEmberIntensity intensity);

    void addEmitterListener(@NotNull BoundingBox box, @NotNull IEmberEmitter emitter);

    void removeEmitterListener(@NotNull IEmberEmitter emitter);

    void setEmberForBoundingBox(@NotNull BlockPos center, @NotNull BoundingBox box, int[] emberPerRadius);

    void clearEmberInBoundingBox(@NotNull BoundingBox box);
}

