package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.machines.anvil.copper.CopperAnvilBlock;
import mysticmods.embers.machines.brazier.BrazierBlock;
import mysticmods.embers.machines.caminite_forge.CaminiteForgeBlock;
import mysticmods.embers.machines.caminite_mold.CaminiteMoldBlock;
import mysticmods.embers.machines.crystallizer.EmberCrystallizerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.multiblock.MultiblockComponentBlock;

public class EmbersBlocks {


    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Embers.MODID);

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

    public static final DeferredBlock<Block> CAMINITE_FORGE_COMPONENT = BLOCKS.register(
            "caminite_forge_component", registryName -> new MultiblockComponentBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)
            )
    );

    public static final DeferredBlock<Block> COPPER_ANVIL = BLOCKS.register(
            "copper_anvil", registryName -> new CopperAnvilBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL).noOcclusion()
            )
    );

    public static final DeferredBlock<Block> CAMINITE_MOLD = BLOCKS.register(
            "caminite_mold", registryName -> new CaminiteMoldBlock(
                    BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops().strength(1.5F, 6.0F)
                            .isViewBlocking(EmbersBlocks::never)
            )
    );

    public static final DeferredBlock<Block> EMBER_CRYSTALLIZER = BLOCKS.register(
            "ember_crystallizer", registryName -> new EmberCrystallizerBlock(
                    BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops().strength(1.5F, 6.0F)
                            .isViewBlocking(EmbersBlocks::never)
            )
    );

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

}
