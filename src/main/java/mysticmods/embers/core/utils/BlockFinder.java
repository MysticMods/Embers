package mysticmods.embers.core.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockFinder {



	public static List<BlockPos> findBlocksInRadius(Block block, BlockPos startingPos, Level level, int radius, int yUp, int yDown) {
		List<BlockPos> positions = new ArrayList<>();
		for (int x = startingPos.getX() - radius; x < startingPos.getX() + radius; x++) {
			for (int z = startingPos.getZ() - radius; z < startingPos.getZ() + radius; z++) {
				for (int y = startingPos.getY() - yDown; y < startingPos.getY() + yUp; y++) {
					if (level.getBlockState(new BlockPos(x, y, z)).getBlock() == block) {
						positions.add(new BlockPos(x, y, z));
					}
				}
			}
		}

		return positions;
	}

	public static List<BlockPos> findBlocksWithTagInRadius(TagKey<Block> pTag, BlockPos startingPos, Level level, int radius, int yUp, int yDown) {
		List<BlockPos> positions = new ArrayList<>();
		for (int x = startingPos.getX() - radius; x <= startingPos.getX() + radius; x++) {
			for (int z = startingPos.getZ() - radius; z <= startingPos.getZ() + radius; z++) {
				for (int y = startingPos.getY() - yDown; y < startingPos.getY() + yUp; y++) {
					if (level.getBlockState(new BlockPos(x, y, z)).is(pTag)) {
						positions.add(new BlockPos(x, y, z));
					}
				}
			}
		}

		return positions;
	}
}
