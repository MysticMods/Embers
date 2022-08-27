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
		return getFirstBlockWithTagInRange(tag, startingPos, level, radius, true, true);
	}

	@Nonnull
	public static Optional<BlockPos> getFirstBlockWithTagInRange(@Nonnull TagKey<Block> tag, @Nonnull BlockPos startingPos, @Nonnull Level level, int radius, boolean up, boolean down) {
		// Scan each circle around the block, increasing the radius each time, so nearest is found first
		for (int r = 0; r <= radius; ++r) {
			for (int x = r * -1; x <= r; x += r) {
				for (int z = r * -1; z <= r; z += r) {
					for (int y = down ? r * -1 : 0; y <= (up ? r : 0); y += r) {
						// This ensures we only check the most outside circle
						if (x == radius || x == radius * -1 || z == radius || z == radius * -1 || y == radius || y == radius * -1) {
							var foundPos = startingPos.offset(x, y, z);
							if (level.getBlockState(foundPos).is(tag)) {
								return Optional.of(foundPos);
							}
						}
					}
				}
			}
		}
		return Optional.empty();
	}
}
