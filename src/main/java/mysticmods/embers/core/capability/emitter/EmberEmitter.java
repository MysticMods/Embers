package mysticmods.embers.core.capability.emitter;

import mysticmods.embers.Embers;
import mysticmods.embers.api.capability.IEmberEmitter;
import mysticmods.embers.api.capability.ILevelEmber;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class EmberEmitter implements IEmberEmitter {

	private int[] intensities;
	private BlockPos pos;
	private BoundingBox box;

	public EmberEmitter(int[] intensities, BlockPos pos, BoundingBox box) {
		this.intensities = intensities;
		this.pos = pos;
		this.box = box;
	}

	@Override
	public int[] getIntensities() {
		return intensities;
	}

	@Override
	public void initEmitter(@NotNull ILevelEmber levelEmber) {
		levelEmber.setEmberForBoundingBox(pos, box, intensities);
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
