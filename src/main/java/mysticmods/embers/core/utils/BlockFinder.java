package mysticmods.embers.core.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.Optional;

public class BlockFinder {

	@Nonnull
	public static Optional<BlockPos> getFirstBlockWithTagInRange(TagKey<Block> tag, BlockPos startingPos, Level level, int radius, boolean up, boolean down) {
		// Scan each circle around the block, increasing the radius each time, so nearest is found first
		for (int r = 0; r <= radius; ++r) {
			for (int x = r * -1; x <= r; x++) {
				for (int z = r * -1; z <= r; z++) {
					for (int y = down ? r * -1 : 0; y <= (up ? r : 0); y++) {
						var foundPos = new BlockPos(x, y, z);
						if (level.getBlockState(foundPos).is(tag)) {
							return Optional.of(foundPos);
						}
					}
				}
			}
		}
		return Optional.empty();
	}
}
