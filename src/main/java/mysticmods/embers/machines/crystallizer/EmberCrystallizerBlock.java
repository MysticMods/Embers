package mysticmods.embers.machines.crystallizer;

import mysticmods.embers.init.EmbersBlockEntities;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class EmberCrystallizerBlock  extends LodestoneEntityBlock<EmberCrystallizerBlockEntity> {


    public EmberCrystallizerBlock(Properties properties) {
        super(properties);
        setBlockEntity(EmbersBlockEntities.EMBER_CRYSTALLIZER);
    }

}
