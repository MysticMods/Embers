package epicsquid.embers.setup;

import epicsquid.embers.Embers;
import epicsquid.embers.blocks.BrazierBlock;
import epicsquid.embers.blocks.EmberFilterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final Supplier<Item.Properties> EIG = () -> new Item.Properties().group(Embers.GROUP);
    public static final Supplier<Item.Properties> EMG = () -> new Item.Properties().group(Embers.GROUP);

    public static RegistryObject<EmberFilterBlock> EMBER_FILTER = Embers.REGISTRY.registerBlock("ember_filter", Embers.REGISTRY.block(EmberFilterBlock::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), EIG);
    public static RegistryObject<Block> BRAZIER = Embers.REGISTRY.registerBlock("brazier", Embers.REGISTRY.block(BrazierBlock::new, () -> Block.Properties.create(Material.IRON).hardnessAndResistance(1F).harvestTool(ToolType.PICKAXE)), EIG);

    public static RegistryObject<Block> CAMINITE_BRICK = Embers.REGISTRY.registerBlock("caminite_bricks", Embers.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), EIG);
    public static RegistryObject<Block> CHISELED_CAMINITE_BRICK = Embers.REGISTRY.registerBlock("chiseled_caminite_bricks", Embers.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), EIG);
    public static RegistryObject<Block> ARCAIC_BRICK = Embers.REGISTRY.registerBlock("arcaic_bricks", Embers.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), EIG);

    public static RegistryObject<Block> CINDERSTEEL_BLOCK = Embers.REGISTRY.registerBlock("cindersteel_block", Embers.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), EIG);
    public static RegistryObject<Block> DAWNSTONE_BLOCK = Embers.REGISTRY.registerBlock("dawnstone_block", Embers.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), EIG);
    public static RegistryObject<Block> EMBER_BLOCK = Embers.REGISTRY.registerBlock("ember_block", Embers.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), EIG);
    public static RegistryObject<Block> EMBER_ORE = Embers.REGISTRY.registerBlock("ember_ore", Embers.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), EIG);

    public static void load() {
    }
}
