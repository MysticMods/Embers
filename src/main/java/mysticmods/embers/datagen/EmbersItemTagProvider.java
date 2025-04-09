package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.EmbersTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EmbersItemTagProvider extends ItemTagsProvider {

    public EmbersItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Embers.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(EmbersTags.IRON_NUGGETS_TAG).add(Items.IRON_NUGGET);
        tag(EmbersTags.IRON_INGOTS_TAG).add(Items.IRON_INGOT);
        tag(EmbersTags.IRON_RAW_ORES_TAG).add(Items.RAW_IRON);

        //tag(EmbersTags.COPPER_NUGGETS_TAG).add(Items.COPPER_NUGGET);
        tag(EmbersTags.COPPER_INGOTS_TAG).add(Items.COPPER_INGOT);
        tag(EmbersTags.COPPER_RAW_ORES_TAG).add(Items.RAW_COPPER);


        tag(EmbersTags.GOLD_NUGGETS_TAG).add(Items.GOLD_NUGGET);
        tag(EmbersTags.GOLD_INGOTS_TAG).add(Items.GOLD_INGOT);
        tag(EmbersTags.GOLD_RAW_ORES_TAG).add(Items.RAW_GOLD);
    }
}
