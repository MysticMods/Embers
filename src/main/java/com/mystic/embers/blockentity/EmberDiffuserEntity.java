package com.mystic.embers.blockentity;

import com.mystic.embers.api.TickBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EmberDiffuserEntity  extends BlockEntity implements TickBlockEntity {
    public EmberDiffuserEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Override
    public <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState ) {
        
    }

    @Override
    public <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState ) {

    }
}
