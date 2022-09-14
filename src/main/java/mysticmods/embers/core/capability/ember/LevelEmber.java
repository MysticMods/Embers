package mysticmods.embers.core.capability.ember;

import mysticmods.embers.Embers;
import mysticmods.embers.api.capability.IEmberEmitter;
import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.api.capability.ILevelEmber;
import mysticmods.embers.core.utils.BlockFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class LevelEmber implements ILevelEmber {

	private final Map<BlockPos, Integer> ember = new HashMap<>();
	private final Map<BlockPos, LazyOptional<IEmberIntensity>> emberListeners = new HashMap<>();
	private final Map<BoundingBox, LazyOptional<IEmberEmitter>> emitterListeners = new HashMap<>();

	@Override
	public int getEmberForPos(@NotNull BlockPos pos) {
		if(ember.get(pos) == null) return 0;
		return ember.get(pos);
	}

	@Override
	public void setEmberForPos(@NotNull BlockPos pos, int emberIn) {
		BlockPos immutablePos = pos.immutable(); // Always make sure its immutable first
		int validEmber = Math.max(emberIn, 0);
		if (validEmber == 0) {
			ember.remove(immutablePos);
		} else {
			ember.compute(immutablePos, (p, e) -> e != null ? Math.max(e, validEmber) : validEmber);
		}
		// Update listeners
		if (emberListeners.containsKey(immutablePos)) {
			emberListeners.get(immutablePos).ifPresent(e -> e.setIntensity(validEmber));
		}
	}

	@Override
	public void setEmberForBoundingBox(@NotNull BlockPos emitter, @NotNull BoundingBox box, int[] emberPerRadius) {
		BlockPos.betweenClosedStream(box).forEach(pos -> {
			int radius = BlockFinder.distance(pos, emitter);
			if (radius < emberPerRadius.length) {
				setEmberForPos(pos, emberPerRadius[radius]);
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
		emitter.addListener(e -> emitterListeners.remove(box));
	}

	@Override
	public void clearEmberInBoundingBox(@NotNull BoundingBox box) {
		BlockPos.betweenClosedStream(box).forEach(pos -> setEmberForPos(pos, 0));
		// Update all remaining emitters
		emitterListeners.keySet().stream().filter(bb -> bb.intersects(box)).forEach(bb -> emitterListeners.get(bb).ifPresent(emit -> emit.initEmitter(this)));
	}

	@Override
	public ListTag serializeNBT() {
		ListTag tag = new ListTag();
		tag.addAll(ember.keySet().stream().map(p -> {
			CompoundTag emberPos = new CompoundTag();
			emberPos.put("pos", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, p).getOrThrow(false, s -> Embers.LOGGER.log(Level.SEVERE, s)));
			emberPos.putInt("ember", ember.get(p));
			return emberPos;
		}).toList());
		return tag;
	}

	@Override
	public void deserializeNBT(ListTag nbt) {
		nbt.forEach(t -> {
			if (t instanceof CompoundTag tag) {
				ember.put(BlockPos.CODEC.parse(NbtOps.INSTANCE, tag.get("ember")).getOrThrow(false, s -> Embers.LOGGER.log(Level.SEVERE, s)), tag.getInt("ember"));
			}
		});
	}
}
