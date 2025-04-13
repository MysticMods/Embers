package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.EmbersBlocks;
import mysticmods.embers.init.EmbersTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Embers.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(EmbersTags.IRON_ORES_TAG).add(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE);
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                EmbersBlocks.BRAZIER.get(),
                EmbersBlocks.BUDDING_EMBER.get(),
                EmbersBlocks.CAMINITE_BRICK.get(),
                EmbersBlocks.CAMINITE_FORGE.get(),
                EmbersBlocks.CAMINITE_FORGE_COMPONENT.get(),
                EmbersBlocks.CAMINITE_MOLD.get(),
                EmbersBlocks.COPPER_ANVIL.get(),
                EmbersBlocks.EMBER_CLUSTER.get(),
                EmbersBlocks.EMBER_CRYSTALLIZER.get(),
                EmbersBlocks.LARGE_EMBER_BUD.get(),
                EmbersBlocks.MEDIUM_EMBER_BUD.get(),
                EmbersBlocks.SMALL_EMBER_BUD.get()
        );
    }
}
