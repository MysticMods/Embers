package mysticmods.embers.machines.caminite_mold;

import mysticmods.embers.init.ModBlockEntities;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class CaminiteMoldBlock extends LodestoneEntityBlock<CaminiteMoldBlockEntity> {


    public CaminiteMoldBlock(Properties properties) {
        super(properties);
        setBlockEntity(ModBlockEntities.CAMINITE_MOLD);
    }

}
