package com.mystic.embers.blocks;

import com.mystic.embers.api.BaseBlockEntity;
import com.mystic.embers.blockentity.EmberDiffuserEntity;
import com.mystic.embers.init.ModBlockEntity;
import com.mystic.embers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.libs.particleslib.init.ModParticles;
import org.jetbrains.annotations.Nullable;

public class EmberDiffuserBlock extends Block implements EntityBlock {

    public EmberDiffuserBlock(Properties pProperties) {
        super(pProperties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EmberDiffuserEntity(ModBlockEntity.EMBER_DIFFUSER.get(), pPos, pState);

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return BaseBlockEntity::clientTick;
        } else {
            return BaseBlockEntity::serverTick;
        }
    }
}
