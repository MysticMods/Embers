package mysticmods.embers.core.machines.anvil.copper;

import mysticmods.embers.init.EmbersBlockEntities;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class CopperAnvilBlock extends LodestoneEntityBlock<CopperAnvilEntity> {

	public CopperAnvilBlock(Properties properties) {
		super(properties);
		setBlockEntity(EmbersBlockEntities.COPPER_ANVIL);
	}
}
