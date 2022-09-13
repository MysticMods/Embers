package mysticmods.embers.core.capability.emitter;

import mysticmods.embers.Embers;
import mysticmods.embers.api.capability.IEmberEmitter;
import mysticmods.embers.api.capability.ILevelEmber;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;
import java.util.logging.Level;

public class EmberEmitter implements IEmberEmitter {

	private int[] intensities;
	private BlockPos pos;
	private BoundingBox box;

	private final Supplier<Boolean> checkIsActive;

	public EmberEmitter(int[] intensities, BlockPos pos, BoundingBox box) {
		this(intensities, pos, box, () -> true);
	}

	public EmberEmitter(int[] intensities, BlockPos pos, BoundingBox box, Supplier<Boolean> checkIsActive) {
		this.intensities = intensities;
		this.pos = pos;
		this.box = box;
		this.checkIsActive = checkIsActive;
	}

	@Override
	public int[] getIntensities() {
		return intensities;
	}

	@Override
	public void initEmitter(@NotNull ILevelEmber levelEmber) {
		if (isActive()) {
			levelEmber.setEmberForBoundingBox(pos, box, intensities);
		}
	}

	@Override
	@NotNull
	public BoundingBox getBoundingBox() {
		return box;
	}

	@Override
	@NotNull
	public BlockPos getPos() {
		return pos;
	}

	@Override
	public boolean isActive() {
		return checkIsActive.get();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putIntArray("intensities", intensities);
		BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, pos).result().ifPresent(t -> tag.put("pos", t));
		BoundingBox.CODEC.encodeStart(NbtOps.INSTANCE, box).result().ifPresent(t -> tag.put("box", t));
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		intensities = nbt.getIntArray("intensities");
		box = BoundingBox.CODEC.parse(NbtOps.INSTANCE, nbt.get("box")).getOrThrow(false, s -> Embers.LOGGER.log(Level.SEVERE, "Emitter missing bounding box: " + s));
		pos = BlockPos.CODEC.parse(NbtOps.INSTANCE, nbt.get("pos")).getOrThrow(false, s -> Embers.LOGGER.log(Level.SEVERE, "Emitter missing pos: " + s));
	}
}
