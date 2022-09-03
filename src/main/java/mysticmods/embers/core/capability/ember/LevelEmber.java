package mysticmods.embers.core.capability.ember;

import mysticmods.embers.api.capability.IEmberEmitter;
import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.api.capability.ILevelEmber;
import mysticmods.embers.core.utils.BlockFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LevelEmber implements ILevelEmber {

	private final Map<BlockPos, Integer> ember = new HashMap<>();
	private final Map<BlockPos, LazyOptional<IEmberIntensity>> emberListeners = new HashMap<>();
	private final Map<BoundingBox, LazyOptional<IEmberEmitter>> emitterListeners = new HashMap<>();

	@Override
	public int getEmberForPos(@NotNull BlockPos pos) {
		return ember.get(pos);
	}

	@Override
	public void setEmberForPos(@NotNull BlockPos pos, int emberIn) {
		int validEmber = Math.max(emberIn, 0);
		if (validEmber == 0) {
			ember.remove(pos);
		} else {
			ember.put(pos, validEmber);
		}
		// Update listeners
		if (emberListeners.containsKey(pos)) {
			emberListeners.get(pos).ifPresent(e -> e.setIntensity(validEmber));
		}
	}

	@Override
	public void setEmberForBoundingBox(@NotNull BlockPos emitter, @NotNull BoundingBox box, int[] emberPerRadius) {
		BlockPos.betweenClosedStream(box).forEach(pos -> {
			int radius = BlockFinder.distance(pos, emitter);
			if (radius < emberPerRadius.length) {
				ember.compute(pos, (p, e) -> e != null ? Math.max(e, emberPerRadius[radius]) : emberPerRadius[radius]);
			}
		});
	}

	@Override
	public void setEmberForRadius(@NotNull BlockPos center, int[] emberPerRadius) {
		int r = emberPerRadius.length - 1;
		setEmberForBoundingBox(center, BoundingBox.fromCorners(center.offset(-r, -r, -r), center.offset(r, r, r)), emberPerRadius);
	}

	@Override
	public void addEmberListener(@NotNull BlockPos pos, @NotNull LazyOptional<IEmberIntensity> intensity) {
		emberListeners.put(pos, intensity);
		// Make sure to remove from list when we no longer have the ember intensity
		intensity.addListener(e -> emberListeners.remove(pos));
	}

	@Override
	public void addEmitterListener(@NotNull BoundingBox box, @NotNull LazyOptional<IEmberEmitter> emitter) {
		emitterListeners.put(box, emitter);
		emitter.addListener(e -> {
			emitterListeners.remove(box);
			clearEmberInBoundingBox(box);
			emitterListeners.keySet().stream().filter(bb -> bb.intersects(box)).forEach(bb -> emitterListeners.get(bb).ifPresent(emit -> emit.initEmitter(this)));
		});
	}

	private void clearEmberInBoundingBox(BoundingBox box) {
		BlockPos.betweenClosedStream(box).forEach(pos -> ember.put(pos, 0));
	}

	@Override
	public ListTag serializeNBT() {
		ListTag tag = new ListTag();
		tag.addAll(ember.keySet().stream().map(p -> {
			CompoundTag emberPos = new CompoundTag();
			emberPos.putLong("pos", p.asLong());
			emberPos.putInt("ember", ember.get(p));
			return emberPos;
		}).toList());
		return tag;
	}

	@Override
	public void deserializeNBT(ListTag nbt) {
		nbt.forEach(t -> {
			if (t instanceof CompoundTag tag) {
				ember.put(BlockPos.of(tag.getLong("pos")), tag.getInt("ember"));
			}
		});
	}
}
