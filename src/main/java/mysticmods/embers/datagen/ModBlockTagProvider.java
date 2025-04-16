package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.ModBlocks;
import mysticmods.embers.init.ModTags;
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
        tag(ModTags.IRON_ORES_TAG).add(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE);
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.BRAZIER.get(),
                ModBlocks.BUDDING_EMBER.get(),
                ModBlocks.CAMINITE_BRICK.get(),
                ModBlocks.CAMINITE_FORGE.get(),
                ModBlocks.CAMINITE_FORGE_COMPONENT.get(),
                ModBlocks.CAMINITE_MOLD.get(),
                ModBlocks.CAMINITE_SMELTER.get(),
                ModBlocks.COPPER_ANVIL.get(),
                ModBlocks.EMBER_CLUSTER.get(),
                ModBlocks.EMBER_CRYSTALLIZER.get(),
                ModBlocks.LARGE_EMBER_BUD.get(),
                ModBlocks.MEDIUM_EMBER_BUD.get(),
                ModBlocks.SMALL_EMBER_BUD.get()
        );
    }
}
