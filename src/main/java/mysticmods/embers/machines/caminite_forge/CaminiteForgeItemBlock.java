package mysticmods.embers.machines.caminite_forge;

import mysticmods.embers.init.EmbersBlocks;
import team.lodestar.lodestone.systems.multiblock.MultiBlockItem;

public class CaminiteForgeItemBlock extends MultiBlockItem {

    public CaminiteForgeItemBlock(Properties properties) {
        super(EmbersBlocks.CAMINITE_FORGE.get(), properties, CaminiteForgeBlockEntity.STRUCTURE);
    }
}