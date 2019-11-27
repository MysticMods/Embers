package epicsquid.embers.blocks;

import epicsquid.embers.Embers;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final Supplier<Item.Properties> SIG = () -> new Item.Properties().group(Embers.GROUP);

    public static RegistryObject<EmberFilterBlock> EMBER_FILTER = Embers.REGISTRY.registerBlock("ember_filter", Embers.REGISTRY.block(EmberFilterBlock::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F)), SIG);

    public static void load() {
    }
}
