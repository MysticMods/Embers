package mysticmods.embers.api.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public interface ILevelEmber extends INBTSerializable<ListTag> {

	int getEmberForPos(@NotNull BlockPos pos);

	void setEmberForPos(@NotNull BlockPos pos, int ember);

	void setEmberForRadius(@NotNull BlockPos center, int[] emberPerRadius);

	void addEmberListener(@NotNull BlockPos pos, @NotNull LazyOptional<IEmberIntensity> intensity);
	void addEmitterListener(@NotNull BoundingBox box, @NotNull LazyOptional<IEmberEmitter> emitter);

	void setEmberForBoundingBox(@NotNull BlockPos center, @NotNull BoundingBox box, int[] emberPerRadius);
}
