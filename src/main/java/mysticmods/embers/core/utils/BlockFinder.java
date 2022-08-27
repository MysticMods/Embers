package mysticmods.embers.core.utils;

import mysticmods.embers.api.data.EmbersTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.Optional;

public class BlockFinder {

	@Nonnull
	public static Optional<BlockPos> getEmberEmitterWithinRange(@Nonnull BlockPos startingPos, @Nonnull Level level, int radius) {
		return getFirstBlockWithTagInRange(EmbersTags.Blocks.EMBER_EMITTER, startingPos, level, radius);
	}

	@Nonnull
	public static Optional<BlockPos> getFirstBlockWithTagInRange(@Nonnull TagKey<Block> tag, @Nonnull BlockPos startingPos, @Nonnull Level level, int radius) {
		return getFirstBlockWithTagInRange(tag, startingPos, level, radius, radius);
	}

	@Nonnull
	public static Optional<BlockPos> getFirstBlockWithTagInRange(@Nonnull TagKey<Block> tag, @Nonnull BlockPos startingPos, @Nonnull Level level, int radius, int height) {
		return BlockPos.findClosestMatch(startingPos, radius, height, (blockPos -> level.getBlockState(blockPos).is(tag)));
	}
}
