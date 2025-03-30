package mysticmods.embers.core.base;

import mysticmods.embers.core.capabilities.emberemitter.EmberEmitter;
import mysticmods.embers.core.capabilities.emberemitter.IEmberEmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class EmberEmitterBlockEntity extends BlockEntity {

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

    public void init() {
        if (level != null && !level.isClientSide) {
            //level.getCapability(EmbersCaps.EMBER).ifPresent(ember -> ember.addEmitterListener(getEmitter().getBoundingBox(), emitterOp));
            setChanged();
        }
    }

    //    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        emitterOp.invalidate();
//    }

//    public void updateViaState() {
//        setChanged();
//        BlockEntityUtil.updateViaState(this);
//    }

//    protected void clearEmber() {
//        if (level != null && !level.isClientSide) {
//            level.getCapability(EmbersCaps.EMBER).ifPresent(ember -> ember.clearEmberInBoundingBox(getEmitter().getBoundingBox()));
//        }
//    }

//    @Override
//    public void onBreak(@Nullable Player player) {
//        super.onBreak(player);
//        clearEmber();
//    }



}
