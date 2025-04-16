package mysticmods.embers.machines.anvil.copper;

import mysticmods.embers.init.ModBlockEntities;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class CopperAnvilBlock extends LodestoneEntityBlock<CopperAnvilBlockEntity> {

    public CopperAnvilBlock(Properties properties) {
        super(properties);
        setBlockEntity(ModBlockEntities.COPPER_ANVIL);
    }
}
