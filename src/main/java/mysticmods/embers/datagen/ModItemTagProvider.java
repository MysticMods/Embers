package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.ModItems;
import mysticmods.embers.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Embers.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.IRON_NUGGETS_TAG).add(Items.IRON_NUGGET);
        tag(ModTags.IRON_INGOTS_TAG).add(Items.IRON_INGOT);
        tag(ModTags.IRON_RAW_ORES_TAG).add(Items.RAW_IRON);

        tag(ModTags.COPPER_NUGGETS_TAG).add(ModItems.COPPER_NUGGET.get());
        tag(ModTags.COPPER_INGOTS_TAG).add(Items.COPPER_INGOT);
        tag(ModTags.COPPER_RAW_ORES_TAG).add(Items.RAW_COPPER);

        tag(ModTags.GOLD_NUGGETS_TAG).add(Items.GOLD_NUGGET);
        tag(ModTags.GOLD_INGOTS_TAG).add(Items.GOLD_INGOT);
        tag(ModTags.GOLD_RAW_ORES_TAG).add(Items.RAW_GOLD);
    }
}
