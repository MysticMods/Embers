package mysticmods.embers.core.machines.forge;

import mysticmods.embers.init.EmbersBlocks;
import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.systems.multiblock.MultiBlockItem;

public class CaminiteForgeItemBlock extends MultiBlockItem {

	public CaminiteForgeItemBlock(Block pBlock, Properties properties) {
		super(EmbersBlocks.CAMINITE_FORGE.get(), properties, CaminiteForgeEntity.STRUCTURE);
	}
}
