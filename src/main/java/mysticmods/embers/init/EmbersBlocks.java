package mysticmods.embers.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import mysticmods.embers.Embers;
import mysticmods.embers.api.data.EmbersApiTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class EmbersBlocks {

	private static final Registrate REGISTRATE = Embers.registrate();

	public static final NonNullUnaryOperator<BlockBehaviour.Properties> BASE_PROPERTIES = r -> r.dynamicShape().noOcclusion().strength(1.5f).sound(SoundType.STONE);

	//Machines

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
