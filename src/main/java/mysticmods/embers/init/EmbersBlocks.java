package mysticmods.embers.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import mysticmods.embers.Embers;
import mysticmods.embers.api.data.EmbersApiTags;
import mysticmods.embers.core.machines.crystallizer.CrystallizerBlock;
import mysticmods.embers.core.machines.brazier.BrazierBlock;
import mysticmods.embers.core.machines.forge.CaminiteForgeBlock;
import mysticmods.embers.core.machines.forge.CaminiteForgeItemBlock;
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
import team.lodestar.lodestone.systems.multiblock.MultiblockComponentBlock;

import java.util.function.Supplier;

public class EmbersBlocks {

	private static final Registrate REGISTRATE = Embers.registrate();

	public static final NonNullUnaryOperator<BlockBehaviour.Properties> BASE_PROPERTIES = r -> r.dynamicShape().noOcclusion().strength(1.5f).sound(SoundType.STONE);

	//Machines
	public static final BlockEntry<BrazierBlock> BRAZIER = REGISTRATE.block("brazier", Material.STONE, BrazierBlock::new)
			.properties(BASE_PROPERTIES)
			.tag(EmbersApiTags.Blocks.EMBER_EMITTER, BlockTags.MINEABLE_WITH_PICKAXE)
			.item()
			.build()
			.blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().withExistingParent("brazier_child", new ResourceLocation(Embers.MOD_ID, "block/brazier"))))
			.recipe((ctx, p) -> ShapedRecipeBuilder.shaped(ctx.getEntry().asItem(), 1)
					.pattern("CCC")
					.pattern("IWI")
					.pattern("IWI")
					.define('C', net.minecraftforge.common.Tags.Items.INGOTS_COPPER)
					.define('I', net.minecraftforge.common.Tags.Items.INGOTS_IRON)
					.define('W', Ingredient.of(Items.ACACIA_LOG, Items.BIRCH_LOG, Items.JUNGLE_LOG, Items.DARK_OAK_LOG, Items.OAK_LOG, Items.SPRUCE_LOG))
					.unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
					.save(p))
			.register();

	public static final BlockEntry<CaminiteForgeBlock> CAMINITE_FORGE = REGISTRATE.block("caminite_forge", Material.STONE, CaminiteForgeBlock::new)
			.properties(BASE_PROPERTIES)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.item(CaminiteForgeItemBlock::new)
			.build()
			.blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().withExistingParent("caminite_forge_child", new ResourceLocation(Embers.MOD_ID, "block/caminite_forge"))))
			.register();

	public static final BlockEntry<MultiblockComponentBlock> CAMINITE_FORGE_COMPONENT = REGISTRATE.block("caminite_forge_component", Material.STONE, MultiblockComponentBlock::new)
			.properties(BASE_PROPERTIES)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().withExistingParent("caminite_forge_component_child", new ResourceLocation(Embers.MOD_ID, "block/caminite_forge_component"))))
			.register();

	public static final BlockEntry<CrystallizerBlock> CRYSTALLIZER = REGISTRATE.block("ember_crystallizer", Material.STONE, CrystallizerBlock::new)
			.properties(BASE_PROPERTIES)
			.tag(EmbersApiTags.Blocks.EMBER_USING, BlockTags.MINEABLE_WITH_PICKAXE)
			.item()
			.build()
			//.blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().withExistingParent("ember_crystallizer_child", new ResourceLocation(Embers.MODID, "block/ember_crystallizer"))))
			.recipe((ctx, p) -> ShapedRecipeBuilder.shaped(ctx.getEntry().asItem(), 1)
					.pattern("CGC")
					.pattern("CDC")
					.pattern("CCC")
					.define('C', Ingredient.of(EmbersItems.CAMINITE_BRICK.get()))
					.define('G', Ingredient.of(Items.GLASS))
					.define('D', Ingredient.of(Items.DIAMOND))
					.unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
					.save(p))
			.register();

	public static final BlockEntry<DropExperienceBlock> VERMILLIONITE_ORE = REGISTRATE.block("vermillionite_ore", Material.STONE, DropExperienceBlock::new)
			.properties(props -> props.requiresCorrectToolForDrops().strength(3.0f, 3.0f))
			.tag(EmbersApiTags.Blocks.ORES_VERMILLIONITE, BlockTags.MINEABLE_WITH_PICKAXE)
			.item()
			.build()
			.loot((lt, block) -> lt.dropOther(block, EmbersItems.VERMILLIONITE_CHUNK.get()))
			.register();

	//Building Blocks
	public static final BlockEntry<Block> CAMINITE_BRICK = normalCube("caminite_bricks", Material.STONE)
			.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.register();

	public static BlockBuilder<Block, Registrate> normalCube(String name, Material material) {
		return REGISTRATE.block(name, material, Block::new).item().build();
	}

	public static BlockBuilder<StairBlock, Registrate> normalStairs(String name, Material material, Supplier<? extends Block> block) {
		return REGISTRATE.block(name + "_stairs", material, stairsBlock(block))
				.tag(BlockTags.STAIRS)
				.item()
				.tag(ItemTags.STAIRS)
				.model((ctx, prov) -> prov.stairs(name + "_stairs", new ResourceLocation(Embers.MOD_ID, "block/" + name), new ResourceLocation(Embers.MOD_ID, "block/" + name), new ResourceLocation(Embers.MOD_ID, "block/" + name)))
				.build()
				.blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), new ResourceLocation(Embers.MOD_ID, "block/" + name)));
	}

	public static BlockBuilder<SlabBlock, Registrate> normalSlab(String name, Material material) {
		return REGISTRATE.block(name + "_slab", material, SlabBlock::new)
				.tag(BlockTags.SLABS)
				.item()
				.tag(ItemTags.SLABS)
				.model((ctx, prov) -> prov.slab(name + "_slab", new ResourceLocation(Embers.MOD_ID, "block/" + name), new ResourceLocation(Embers.MOD_ID, "block/" + name), new ResourceLocation(Embers.MOD_ID, "block/" + name)))
				.build()
				.blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), new ResourceLocation(Embers.MOD_ID, "block/" + name), new ResourceLocation(Embers.MOD_ID, "block/" + name)));
	}

	public static BlockBuilder<WallBlock, Registrate> normalWall(String name, Material material) {
		return REGISTRATE.block(name + "_wall", material, WallBlock::new)
				.tag(BlockTags.WALLS)
				.item()
				.tag(ItemTags.WALLS)
				.model((ctx, prov) -> prov.wallInventory(name + "_wall", new ResourceLocation(Embers.MOD_ID, "block/" + name)))
				.build()
				.blockstate((ctx, prov) -> prov.wallBlock(ctx.getEntry(), name, new ResourceLocation(Embers.MOD_ID, "block/" + name)));
	}

	public static NonNullFunction<Block.Properties, StairBlock> stairsBlock(Supplier<? extends Block> block) {
		return (b) -> new StairBlock(() -> block.get().defaultBlockState(), b);
	}

	public static void init() {
	}

}
