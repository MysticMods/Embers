package com.mystic.embers.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
}
