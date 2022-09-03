package mysticmods.embers.api.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface ILevelEmber extends INBTSerializable<ListTag> {

	int getEmberForPos(BlockPos pos);

	void setEmberForPos(BlockPos pos, int ember);

	void setEmberForRadius(BlockPos center, int[] emberPerRadius);

	void addEmberListener(BlockPos pos, LazyOptional<IEmberIntensity> intensity);
	void addEmitterListener(BoundingBox box, LazyOptional<IEmberEmitter> emitter);

	void setEmberForBoundingBox(BlockPos center, BoundingBox box, int[] emberPerRadius);
}
