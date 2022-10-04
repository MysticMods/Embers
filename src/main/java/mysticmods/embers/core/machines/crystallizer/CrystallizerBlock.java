package mysticmods.embers.core.machines.crystallizer;

import mysticmods.embers.init.EmbersBlockEntities;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class CrystallizerBlock extends LodestoneEntityBlock<CrystallizerEntity> {

	public CrystallizerBlock(Properties pProperties) {
		super(pProperties);
		setBlockEntity(EmbersBlockEntities.CRYSTALLIZER);
	}
}
