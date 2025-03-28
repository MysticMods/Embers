package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.core.machines.brazier.BrazierBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class EmbersBlockEntities {

    public static final Supplier<BlockEntityType<BrazierBlockEntity>> BRAZIER = Embers.BLOCK_ENTITY_TYPES.register(
            "brazier_block_entity",
            () -> new BlockEntityType<>(
                    BrazierBlockEntity::new,
                    EmbersBlocks.BRAZIER.get()
            )
    );

    public static void init() {
    }

}
