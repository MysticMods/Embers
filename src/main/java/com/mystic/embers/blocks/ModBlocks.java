package com.mystic.embers.blocks;

import com.mystic.embers.Embers;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class ModBlocks {

    private static final Registrate REGISTRATE = Embers.registrate();

    public static final BlockEntry<Block> ARCAIC_BRICK = normalCube("arcaic_bricks", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();

    public static BlockEntry<StairBlock> ARCAIC_STAIRS = normalStairs("arcaic_bricks", Material.STONE, ARCAIC_BRICK)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();

    public static BlockEntry<SlabBlock> ARCAIC_SLABS = normalSlab("arcaic_bricks", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();

    public static BlockEntry<WallBlock> ARCAIC_WALL = normalWall("arcaic_bricks", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();

    public static BlockBuilder<Block, Registrate> normalCube(String name, Material material){
        return REGISTRATE.block(name, material, Block::new) .item().tab(() -> Embers.ITEM_GROUP).build();
    }

    public static BlockBuilder<StairBlock, Registrate> normalStairs(String name, Material material, Supplier<? extends Block> block){
        return REGISTRATE.block(name + "_stairs", material, stairsBlock(block))
                .tag(BlockTags.STAIRS)
                .item()
                .tab(() -> Embers.ITEM_GROUP)
                .tag(ItemTags.STAIRS)
                .model((ctx, prov) -> prov.stairs(name + "_stairs", new ResourceLocation(Embers.MODID, "block/" + name), new ResourceLocation(Embers.MODID, "block/" + name), new ResourceLocation(Embers.MODID, "block/" + name)))
                .build()
                .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), new ResourceLocation(Embers.MODID, "block/" + name)));
    }

    public static BlockBuilder<SlabBlock, Registrate> normalSlab(String name, Material material){
        return REGISTRATE.block(name + "_slab", material, SlabBlock::new)
                .tag(BlockTags.SLABS)
                .item()
                .tab(() -> Embers.ITEM_GROUP)
                .tag(ItemTags.SLABS)
                .model((ctx, prov) -> prov.slab(name + "_slab", new ResourceLocation(Embers.MODID, "block/" + name), new ResourceLocation(Embers.MODID, "block/" + name), new ResourceLocation(Embers.MODID, "block/" + name)))
                .build()
                .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), new ResourceLocation(Embers.MODID, "block/" + name), new ResourceLocation(Embers.MODID, "block/" + name)));
    }

    public static BlockBuilder<WallBlock, Registrate> normalWall(String name, Material material){
        return REGISTRATE.block(name + "_wall", material, WallBlock::new)
                .tag(BlockTags.WALLS)
                .item()
                .tab(() -> Embers.ITEM_GROUP)
                .tag(ItemTags.WALLS)
                .model((ctx, prov) -> prov.wallInventory(name + "_wall", new ResourceLocation(Embers.MODID, "block/" + name)))
                .build()
                .blockstate((ctx, prov) -> prov.wallBlock(ctx.getEntry(), name, new ResourceLocation(Embers.MODID, "block/" + name)));
    }

    public static NonNullFunction<Block.Properties, StairBlock> stairsBlock(Supplier<? extends Block> block) {
        return (b) -> new StairBlock(() -> block.get().defaultBlockState(), b);
    }

    public static void classload() {
    }

}
