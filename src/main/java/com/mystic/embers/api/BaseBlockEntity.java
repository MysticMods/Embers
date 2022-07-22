package com.mystic.embers.api;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.libs.noobutil.util.BlockEntityUtil;

import javax.annotation.Nullable;

public class BaseBlockEntity  extends BlockEntity {

    public BaseBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    public static <T extends BlockEntity> void clientTick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
        if (pBlockEntity instanceof TickBlockEntity clientBlockEntity) {
            clientBlockEntity.clientTick(pLevel, pPos, pState);
        }
    }

    public static <T extends BlockEntity> void serverTick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
        if (pBlockEntity instanceof TickBlockEntity serverBlockEntity) {
            serverBlockEntity.serverTick(pLevel, pPos, pState);
        }
    }

    public void updateViaState() {
        setChanged();
        BlockEntityUtil.updateViaState(this);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag pTag = new CompoundTag();
        saveAdditional(pTag);
        return pTag;
    }
}
