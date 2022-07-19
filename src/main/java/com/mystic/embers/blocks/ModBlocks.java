package com.mystic.embers.blocks;

import com.mystic.embers.Embers;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class ModBlocks {

    private static final Registrate REGISTRATE = Embers.registrate();

    public static final RegistryEntry<Block> ARCAIC_BRICK = REGISTRATE.block("arcaic_brick", Material.STONE, Block::new)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .item().tab(() -> Embers.ITEM_GROUP).build()
            .register();

    //private final ItemEntry<BlockItem> testblockitem = (ItemEntry<BlockItem>) MY_BLOCK.<Item, BlockItem>getSibling(Registry.ITEM_REGISTRY);

    public static void classload() {
    }

}
