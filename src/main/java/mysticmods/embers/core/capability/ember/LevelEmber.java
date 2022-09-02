package mysticmods.embers.core.capability.ember;

import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.api.capability.ILevelEmber;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.Map;

public class LevelEmber implements ILevelEmber {

	private final Map<BlockPos, Integer> ember = new HashMap<>();
	private final Map<BlockPos, LazyOptional<IEmberIntensity>> listeners = new HashMap<>();

	@Override
	public int getEmberForPos(BlockPos pos) {
		return ember.get(pos);
	}

	@Override
	public void setEmberForPos(BlockPos pos, int emberIn) {
		int validEmber = Math.max(emberIn, 0);
		if (validEmber == 0) {
			ember.remove(pos);
		} else {
			ember.put(pos, validEmber);
		}
		// Update listeners
		if (listeners.containsKey(pos)) {
			listeners.get(pos).ifPresent(e -> e.setIntensity(validEmber));
		}
	}

	@Override
	public void setEmberInRadius(BlockPos center, int[] emberPerRadius) {
		int radius = emberPerRadius.length;
		for (int r = 0; r < radius; r++) {
			for (int x = r * -1; x <= r; x += r) {
				for (int z = r * -1; z <= r; z += r) {
					for (int y = r * -1; y <= r; y += r) {
						// This ensures we only check the most outside circle
						if (x == radius - 1 || x == radius * -1 || z == radius - 1 || z == radius * -1 || y == radius - 1 || y == radius * -1) {
							BlockPos pos = center.offset(x, y, z);
							setEmberForPos(pos, emberPerRadius[r]);
						}
					}
				}
			}
		}
	}

	@Override
	public void addEmberListener(BlockPos pos, LazyOptional<IEmberIntensity> intensity) {
		listeners.put(pos, intensity);
		// Make sure to remove from list when we no longer have the ember intensity
		intensity.addListener(e -> listeners.remove(pos));
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
