package mysticmods.embers.core.capability.ember;

import mysticmods.embers.api.capability.ILevelEmber;
import mysticmods.embers.init.EmbersCaps;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LevelEmberProvider implements ICapabilitySerializable<ListTag> {
	private final ILevelEmber ember;
	private final LazyOptional<ILevelEmber> op;

	public LevelEmberProvider(ILevelEmber ember) {
		this.ember = ember;
		this.op = LazyOptional.of(() -> this.ember);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return EmbersCaps.EMBER.orEmpty(cap, op);
	}

	@Override
	public ListTag serializeNBT() {
		return ember.serializeNBT();
	}

	@Override
	public void deserializeNBT(ListTag nbt) {
		ember.deserializeNBT(nbt);
	}
}
