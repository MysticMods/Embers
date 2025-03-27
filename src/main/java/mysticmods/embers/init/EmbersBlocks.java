package mysticmods.embers.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

import static mysticmods.embers.EmbersMod.BLOCKS;

public class EmbersBlocks {

    public static final DeferredBlock<Block> CAMINITE_BRICK = BLOCKS.register(
            "caminite_bricks", registryName -> new Block(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .requiresCorrectToolForDrops().strength(1.5F, 6.0F)

            )
    );

    public static void init() {
    }

}
