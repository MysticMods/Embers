package mysticmods.embers.core.base;

import mysticmods.embers.api.blocks.EmbersBlockEntity;
import mysticmods.embers.core.capabilities.emberemitter.EmberEmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class EmberEmitterBlockEntity extends EmbersBlockEntity {

    public EmberEmitterBlockEntity(BlockEntityType<? extends EmberEmitterBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * Gets the Ember Emitter capability for this entity
     *
     * @return The ember emitter capability as configured. This should be a singleton.
     */
    @NotNull
    public abstract EmberEmitter getEmitter();

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("emitter", getEmitter().serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        getEmitter().deserializeNBT(registries, tag.getCompound("emitter"));
    }

}
