package mysticmods.embers.init;

import mysticmods.embers.core.machines.brazier.BrazierBlock;
import mysticmods.embers.core.machines.caminiteforge.CaminiteForgeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;

import static mysticmods.embers.Embers.BLOCKS;

public class EmbersBlocks {

    public static final DeferredBlock<Block> CAMINITE_BRICK = BLOCKS.register(
            "caminite_bricks", registryName -> new Block(
                    BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops().strength(1.5F, 6.0F)
            )
    );

    public static final DeferredBlock<Block> BRAZIER = BLOCKS.register(
            "brazier", registryName -> new BrazierBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)
                            .lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0)

            )
    );

    public static final DeferredBlock<Block> CAMINITE_FORGE = BLOCKS.register(
            "caminite_forge", registryName -> new CaminiteForgeBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)

            )
    );

    public static void init() {
    }

}
