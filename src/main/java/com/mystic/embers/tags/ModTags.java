package com.mystic.embers.tags;

import com.mystic.embers.Embers;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModTags extends ItemTagsProvider {

    //public static final TagKey<Item> MANAPEARL = ItemTags.create(new ResourceLocation("forge", "mana_pearl"));
    //public static final TagKey<Item> PIXIE_DUST = ItemTags.create(new ResourceLocation("forge", "pixie_dust"));

    public ModTags(DataGenerator dataGen, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGen, blockTagsProvider, Embers.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // Add Botania items for crafting
       // tag(MANAPEARL).addOptional(new ResourceLocation(BotaniaAPI.MODID, "mana_pearl"));
    }
}
