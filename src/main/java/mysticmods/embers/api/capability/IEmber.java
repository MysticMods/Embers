package mysticmods.embers.api.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEmber extends INBTSerializable<CompoundTag> {

	int getEmberForPos(BlockPos pos);

	void setEmberForPos(BlockPos pos, int ember);

	void setEmberInRadius(BlockPos center, int[] emberPerRadius);

	int getMaxEmberForPos(BlockPos pos);

	void setMaxEmberForPos(BlockPos pos, int maxEmber);

	void setMaxEmberInRadius(BlockPos center, int[] maxEmberPerRadius);
}
