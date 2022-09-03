package mysticmods.embers.core.capability.emitter;

import mysticmods.embers.api.capability.IEmberEmitter;
import mysticmods.embers.init.EmbersCaps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EmberEmitterProvider implements ICapabilitySerializable<CompoundTag> {

	private final IEmberEmitter emitter;
	private final LazyOptional<IEmberEmitter> op;

	public EmberEmitterProvider(int[] intensities, BlockPos pos, BoundingBox box) {
		this.emitter = new EmberEmitter(intensities, pos, box);
		this.op = LazyOptional.of(() -> emitter);
	}

	@Override
	@NotNull
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return EmbersCaps.EMBER_EMITTER.orEmpty(cap, op);
	}

	@Override
	public CompoundTag serializeNBT() {
		return emitter.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		emitter.deserializeNBT(nbt);
	}
}
