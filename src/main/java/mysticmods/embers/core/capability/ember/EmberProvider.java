package mysticmods.embers.core.capability.ember;

import mysticmods.embers.api.capability.IEmber;
import mysticmods.embers.init.EmbersCaps;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EmberProvider implements ICapabilitySerializable<CompoundTag> {
	private final IEmber ember;
	private final LazyOptional<IEmber> op;

	public EmberProvider(IEmber ember) {
		this.ember = ember;
		this.op = LazyOptional.of(() -> this.ember);
	}

	public void invalidate() {
		this.op.invalidate();
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return EmbersCaps.EMBER.orEmpty(cap, op);
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag rootTag = new CompoundTag();
		return rootTag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {

	}
}
