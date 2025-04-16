package mysticmods.embers.datagen;

import mysticmods.embers.init.ModBlocks;
import mysticmods.embers.init.ModItems;
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
        return ModBlocks.BLOCKS.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.BRAZIER.get());
        dropSelf(ModBlocks.CAMINITE_BRICK.get());
        dropSelf(ModBlocks.CAMINITE_FORGE.get());
        dropSelf(ModBlocks.CAMINITE_MOLD.get());
        dropSelf(ModBlocks.CAMINITE_SMELTER.get());
        dropSelf(ModBlocks.COPPER_ANVIL.get());
        dropSelf(ModBlocks.EMBER_CRYSTALLIZER.get());


        dropOther(ModBlocks.EMBER_CLUSTER.get(), ModItems.EMBER_CRYSTAL);
        dropOther(ModBlocks.CAMINITE_FORGE_COMPONENT.get(), ModItems.CAMINITE_FORGE_BLOCK_ITEM);

        //Silktouch
        add(ModBlocks.BUDDING_EMBER.get(), createSilkTouchOnlyTable(ModBlocks.BUDDING_EMBER.get()));

        //No drop
        add(ModBlocks.SMALL_EMBER_BUD.get(), noDrop());
        add(ModBlocks.MEDIUM_EMBER_BUD.get(), noDrop());
        add(ModBlocks.LARGE_EMBER_BUD.get(), noDrop());
    }
}
