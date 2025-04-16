package mysticmods.embers.machines.caminite_forge;

import mysticmods.embers.init.ModBlocks;
import team.lodestar.lodestone.systems.multiblock.MultiBlockItem;

public class CaminiteForgeItemBlock extends MultiBlockItem {

    public CaminiteForgeItemBlock(Properties properties) {
        super(ModBlocks.CAMINITE_FORGE.get(), properties, CaminiteForgeBlockEntity.STRUCTURE);
    }
}