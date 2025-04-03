package mysticmods.embers.core.machines.caminiteforge;

import mysticmods.embers.init.EmbersBlocks;
import team.lodestar.lodestone.systems.multiblock.MultiBlockItem;

public class CaminiteForgeItemBlock extends MultiBlockItem {

    public CaminiteForgeItemBlock(Properties properties) {
        super(EmbersBlocks.CAMINITE_FORGE.get(), properties, CaminiteForgeBlockEntity.STRUCTURE);
    }
}