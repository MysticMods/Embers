package mysticmods.embers.machines.crystallizer;

import mysticmods.embers.init.ModBlockEntities;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class EmberCrystallizerBlock  extends LodestoneEntityBlock<EmberCrystallizerBlockEntity> {


    public EmberCrystallizerBlock(Properties properties) {
        super(properties);
        setBlockEntity(ModBlockEntities.EMBER_CRYSTALLIZER);
    }

}
