package mysticmods.embers.api.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface ILevelEmber extends INBTSerializable<ListTag> {

	int getEmberForPos(BlockPos pos);

	void setEmberForPos(BlockPos pos, int ember);

	void setEmberInRadius(BlockPos center, int[] emberPerRadius);

	void addEmberListener(BlockPos pos, LazyOptional<IEmberIntensity> intensity);
}
