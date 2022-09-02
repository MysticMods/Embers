package mysticmods.embers.core.utils;

import mysticmods.embers.api.data.EmbersApiTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.Optional;

public class BlockFinder {

	@Nonnull
	public static Optional<BlockPos> getEmberEmitterWithinRange(@Nonnull BlockPos startingPos, @Nonnull Level level, int radius) {
		return getFirstBlockWithTagInRange(EmbersApiTags.Blocks.EMBER_EMITTER, startingPos, level, radius);
	}

	@Nonnull
	public static Optional<BlockPos> getFirstBlockWithTagInRange(@Nonnull TagKey<Block> tag, @Nonnull BlockPos startingPos, @Nonnull Level level, int radius) {
		return getFirstBlockWithTagInRange(tag, startingPos, level, radius, radius);
	}

	@Nonnull
	public static Optional<BlockPos> getFirstBlockWithTagInRange(@Nonnull TagKey<Block> tag, @Nonnull BlockPos startingPos, @Nonnull Level level, int radius, int height) {
		return BlockPos.findClosestMatch(startingPos, radius, height, (blockPos -> level.getBlockState(blockPos).is(tag)));
	}

	public static int distance(Vec3i pos1, Vec3i pos2) {
		int x = Math.abs(pos1.getX() - pos2.getX());
		int y = Math.abs(pos1.getY() - pos2.getY());
		int z = Math.abs(pos1.getZ() - pos2.getZ());
		return Math.max(Math.max(x, y), z);
	}
}
