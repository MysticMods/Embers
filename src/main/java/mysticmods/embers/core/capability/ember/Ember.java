package mysticmods.embers.core.capability.ember;

import com.mojang.datafixers.util.Pair;
import mysticmods.embers.api.capability.IEmber;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class Ember implements IEmber {

	// Pos: Ember/MaxEmber
	private final Map<BlockPos, Pair<Integer, Integer>> ember = new HashMap<>();

	@Override
	public int getEmberForPos(BlockPos pos) {
		return ember.get(pos).getFirst();
	}

	@Override
	public void setEmberForPos(BlockPos pos, int emberIn) {
		ember.put(pos, Math.min(emberIn, ember.getOrDefault(pos, 0)));
	}

	@Override
	public void setEmberInRadius(BlockPos center, int[] emberPerRadius) {

	}

	@Override
	public int getMaxEmberForPos(BlockPos pos) {
		return ember.get(pos).getSecond();
	}

	@Override
	public void setMaxEmberForPos(BlockPos pos, int maxEmberIn) {

	}

	@Override
	public void setMaxEmberInRadius(BlockPos center, int[] maxEmberPerRadius) {

	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag rootTag = new CompoundTag();
		for (BlockPos key : ember.keySet()) {
			CompoundTag emberPos = new CompoundTag();
			emberPos.putLong("pos", key.asLong());
			emberPos.putInt("ember", ember.get(key).getFirst());
			emberPos.putInt("max", ember.get(key).getSecond());
		}
		return rootTag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {

	}
}
