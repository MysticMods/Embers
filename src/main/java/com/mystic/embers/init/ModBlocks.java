package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.mystic.embers.blocks.CaminiteForgeBlock;
import com.mystic.embers.blocks.CaminiteForgeUnfiredBlock;
import com.mystic.embers.blocks.EmberDiffuserBlock;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

public class ModBlocks {

    private static final Registrate REGISTRATE = Embers.registrate();

    public static final NonNullUnaryOperator<BlockBehaviour.Properties> BASE_PROPERTIES = r -> r.dynamicShape().noOcclusion().strength(1.5f).sound(SoundType.STONE);

    //Machines
    public static final BlockEntry<EmberDiffuserBlock> EMBER_DIFFUSER = REGISTRATE.block("ember_diffuser", Material.STONE, EmberDiffuserBlock::new)
            .properties(BASE_PROPERTIES)
            .item().tab(() -> Embers.ITEM_GROUP).build()
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().withExistingParent("ember_diffuser_child", new ResourceLocation(Embers.MODID, "block/ember_diffuser"))))
            .recipe((ctx, p) -> ShapedRecipeBuilder.shaped(ctx.getEntry().asItem(), 1)
                    .pattern("CCC")
                    .pattern("IWI")
                    .pattern("IWI")
                    .define('C', Tags.Items.INGOTS_COPPER)
                    .define('I', Tags.Items.INGOTS_IRON)
                    .define('W', Ingredient.of(Items.ACACIA_LOG, Items.BIRCH_LOG, Items.JUNGLE_LOG, Items.DARK_OAK_LOG, Items.OAK_LOG, Items.SPRUCE_LOG))
                    .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                    .save(p))
            .register();

    public static final BlockEntry<CaminiteForgeUnfiredBlock> UNFIRED_CAMINITE_FORGE = REGISTRATE.block("unfired_caminite_forge", Material.STONE, CaminiteForgeUnfiredBlock::new)
            .properties(BASE_PROPERTIES)
            .item().tab(() -> Embers.ITEM_GROUP).build()
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.getEntry(), prov.models().withExistingParent("unfired_caminite_forge_model", new ResourceLocation(Embers.MODID, "block/unfired_caminite_forge"))))
            .recipe((ctx, p) -> ShapedRecipeBuilder.shaped(ctx.getEntry().asItem(), 1)
                    .pattern("CCC")
                    .pattern("C C")
                    .pattern("CCC")
                    .define('C', ModItems.CAMINITE_BRICK.get())
                    .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CAMINITE_BRICK.get()))
                    .save(p))
            .register();

    public static final BlockEntry<CaminiteForgeBlock> CAMINITE_FORGE = REGISTRATE.block("caminite_forge", Material.STONE, CaminiteForgeBlock::new)
            .properties(BASE_PROPERTIES)
            .item().tab(() -> Embers.ITEM_GROUP).build()
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.getEntry(), prov.models().withExistingParent("caminite_forge_model", new ResourceLocation(Embers.MODID, "block/caminite_forge"))))
            .register();

    //Building Blocks
    public static final BlockEntry<Block> ARCAIC_BRICK = normalCube("arcaic_bricks", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();
    public static final BlockEntry<Block> CAMINITE_BRICK = normalCube("caminite_bricks", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();
    public static final BlockEntry<Block> CHISELED_CAMINITE_BRICK = normalCube("chiseled_caminite_bricks", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();
    public static final BlockEntry<Block> CINDERSTEEL_BLOCK = normalCube("cindersteel_block", Material.METAL)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();
    public static final BlockEntry<Block> DAWNSTONE_BLOCK = normalCube("dawnstone_block", Material.METAL)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();
    public static final BlockEntry<Block> EMBER_BLOCK = normalCube("ember_block", Material.METAL)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();
    public static final BlockEntry<Block> EMBER_ORE = normalCube("ember_ore", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();

    public static BlockEntry<StairBlock> ARCAIC_STAIRS = normalStairs("arcaic_bricks", Material.STONE, ARCAIC_BRICK)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();
    public static BlockEntry<StairBlock> CAMINITE_STAIRS = normalStairs("caminite_bricks", Material.STONE, ARCAIC_BRICK)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();

    public static BlockEntry<SlabBlock> ARCAIC_SLABS = normalSlab("arcaic_bricks", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();
    public static BlockEntry<SlabBlock> CAMINITE_SLABS = normalSlab("caminite_bricks", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();

    public static BlockEntry<WallBlock> ARCAIC_WALL = normalWall("arcaic_bricks", Material.STONE)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .register();
    public static BlockEntry<WallBlock> CAMINITE_WALL = normalWall("caminite_bricks", Material.STONE)
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

    public static void classload() { }

}
