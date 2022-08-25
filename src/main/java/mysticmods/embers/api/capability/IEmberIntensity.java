package mysticmods.embers.api.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEmberIntensity extends INBTSerializable<IntTag> {

	int getMinIntensity();

	int getMaxIntensity();

	int getIntensity();

	void setIntensity(int intensity);

	default void onChange() {
		// Do nothing, just for blocks to do something when the intensity changes
	}
}