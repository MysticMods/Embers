package mysticmods.embers.machines.caminite_mold;

import mysticmods.embers.init.EmbersBlockEntities;
import mysticmods.embers.machines.caminite_forge.CaminiteForgeBlockEntity;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class CaminiteMoldBlock extends LodestoneEntityBlock<CaminiteMoldBlockEntity> {


    public CaminiteMoldBlock(Properties properties) {
        super(properties);
        setBlockEntity(EmbersBlockEntities.CAMINITE_MOLD);
    }

}
