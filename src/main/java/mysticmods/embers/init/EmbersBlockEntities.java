package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.machines.brazier.BrazierBlockEntity;
import mysticmods.embers.machines.caminite_forge.CaminiteForgeBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EmbersBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Embers.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BrazierBlockEntity>> BRAZIER = BLOCK_ENTITY_TYPES.register(
            "brazier_block_entity",
            () -> BlockEntityType.Builder.of(
                    BrazierBlockEntity::new,
                    EmbersBlocks.BRAZIER.get()
            ).build(null)
    );

    public static final  DeferredHolder<BlockEntityType<?>, BlockEntityType<CaminiteForgeBlockEntity>> CAMINITE_FORGE = BLOCK_ENTITY_TYPES.register(
            "caminite_forge_block_entity",
            () -> BlockEntityType.Builder.of(
                    CaminiteForgeBlockEntity::new,
                    EmbersBlocks.CAMINITE_FORGE.get()
            ).build(null)
    );

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
