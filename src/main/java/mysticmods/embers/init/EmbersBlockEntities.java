package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.machines.brazier.BrazierBlockEntity;
import mysticmods.embers.machines.caminite_forge.CaminiteForgeBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class EmbersBlockEntities {

    public static final Supplier<BlockEntityType<BrazierBlockEntity>> BRAZIER = Embers.BLOCK_ENTITY_TYPES.register(
            "brazier_block_entity",
            () -> BlockEntityType.Builder.of(
                    BrazierBlockEntity::new,
                    EmbersBlocks.BRAZIER.get()
            ).build(null)
    );

    public static final Supplier<BlockEntityType<CaminiteForgeBlockEntity>> CAMINITE_FORGE = Embers.BLOCK_ENTITY_TYPES.register(
            "caminite_forge_block_entity",
            () -> BlockEntityType.Builder.of(
                    CaminiteForgeBlockEntity::new,
                    EmbersBlocks.CAMINITE_FORGE.get()
            ).build(null)
    );

    public static void init() {
    }

}
