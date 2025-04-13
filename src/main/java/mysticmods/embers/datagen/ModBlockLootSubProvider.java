package mysticmods.embers.datagen;

import mysticmods.embers.init.EmbersBlocks;
import mysticmods.embers.init.EmbersItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootSubProvider extends BlockLootSubProvider {

    public ModBlockLootSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return EmbersBlocks.BLOCKS.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
    }

    @Override
    protected void generate() {
        dropSelf(EmbersBlocks.BRAZIER.get());
        dropSelf(EmbersBlocks.CAMINITE_BRICK.get());
        dropSelf(EmbersBlocks.CAMINITE_FORGE.get());
        dropSelf(EmbersBlocks.CAMINITE_MOLD.get());
        dropSelf(EmbersBlocks.COPPER_ANVIL.get());
        dropSelf(EmbersBlocks.EMBER_CRYSTALLIZER.get());


        dropOther(EmbersBlocks.EMBER_CLUSTER.get(), EmbersItems.EMBER_CRYSTAL);
        dropOther(EmbersBlocks.CAMINITE_FORGE_COMPONENT.get(), EmbersItems.CAMINITE_FORGE_BLOCK_ITEM);

        //Silktouch
        add(EmbersBlocks.BUDDING_EMBER.get(), createSilkTouchOnlyTable(EmbersBlocks.BUDDING_EMBER.get()));

        //No drop
        add(EmbersBlocks.SMALL_EMBER_BUD.get(), noDrop());
        add(EmbersBlocks.MEDIUM_EMBER_BUD.get(), noDrop());
        add(EmbersBlocks.LARGE_EMBER_BUD.get(), noDrop());
    }
}
